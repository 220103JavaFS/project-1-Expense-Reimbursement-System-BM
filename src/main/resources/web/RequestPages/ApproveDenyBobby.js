let approved = document.getElementById('approve').value;
let denied = document.getElementById('deny').value;

let approvedRadio = document.getElementById('approve');
let deniedRadio = document.getElementById('deny');

let requestTable = document.getElementById("RequestTable");
let submitButton = document.getElementById("submitButton");



const currentUserUsername = get_cookie("currentUserUsername"); //get name of the logged in user from cookie
const url = "http://localhost:8081";

const reimbursementStatusList = ["null", "created", "submitted", "denied", "approved"]
var reimbursementData; //this will be filled in on loading of the page
var currentRadioType; //this will be filled in on page load
var selectedReimbursement = {
  reimbursementID: 0,
  reimbursementAmount: 0,
  reimbursementSubmitted: null,
  reimbursementResolved: null,
  reimbursementDescription: "",
  reimbursementReceipt: [],
  reimbursementAuthor: 0,
  reimbursementResolver: 0,
  reimbursementStatusId: 0,
  reimbursementTypeId: 0,
}; //represents the request currently selected by the user. Set everything to default values off the bat


//Add event listeners
approvedRadio.addEventListener("click", radioChangeFunc);
deniedRadio.addEventListener("click", radioChangeFunc);
submitButton.addEventListener("click", submitFunc);

//on page load, reach out to the server to get user reimbursement data and then save it in JS object,
//also display it in the table on screen
window.onload = async () => {

  //get all of the requests in the database with "submit" status
  let response = await fetch(url + "/ReimbursementRequest/Approval"); //just a standard get request
  reimbursementData = await response.json(); //store in a global variable so other functions can access

  loadTable(reimbursementData);
}

function radioChangeFunc() {
   currentRadioType = document.querySelector('input[name="request_action"]:checked').value;
}

function get_cookie(Name) {
  //console.log("Attempting to get cookie: " + Name);
  var search = Name + "="
  var returnvalue = "";
  if (document.cookie.length > 0) {
    offset = document.cookie.indexOf(search)
    // if cookie exists
    if (offset != -1) {
      offset += search.length
      // set index of beginning of value
      end = document.cookie.indexOf(";", offset);
      // set index of end of cookie value
      if (end == -1) end = document.cookie.length;
      returnvalue=unescape(document.cookie.substring(offset, end))
      }
   }
  return returnvalue;
}

function convertStatus(statusId) {
  if (statusId === 1) return "Created";
  else if (statusId === 2) return "Submitted";
  else if (statusId === 3) return "Approved";
  else return "Denied";
}

function convertType(typeId) {
  if (typeId === 1) return "Lodging";
  else if (typeId === 2) return "Food";
  else if (typeId === 3) return "Travel";
  else return "Other";
}

function loadTable(reimbursementList) {
  //if we have a filter applied, only load the appropriate elements

  //first, delete all current data from our table (but not the table header)
  let allRows = document.getElementsByClassName("jsRow");
  for (let k = allRows.length - 1; k >= 0; k--) {
    allRows[k].remove();
  }

  //now loop through the current user's reimbursement list which was saved in a global variable upon page load
  //let rowCount = 0;
  for (let request of reimbursementList) {

    //we only want to display table rows that match our filter
    let row = document.createElement("tr");
    row.classList.add("jsRow");
    row.addEventListener("click", selectFunc);

    for (let data in request) {
      let td = document.createElement("td");
      
      td.innerText = request[data];
      row.appendChild(td); 
    }
    requestTable.appendChild(row);
  }
}

async function submitFunc() {
    //Step one, make sure that a request is selected
    if (selectedReimbursement == 0) {
        alert("Please select a request before submitting a decision.")
        return;
    }


    console.log(currentRadioType);
    //Step two, make sure that one of the radio buttons has actually been selected
    if (currentRadioType == "Approve") {
        selectedReimbursement["reimbursementStatusId"] = 3; //set status to approve
    }
    else if (currentRadioType == "Deny") {
      selectedReimbursement["reimbursementStatusId"] = 4; //set status to denied
    }
    else {
        alert("Please select an action via the radio buttons.");
        return;
    }

    //step three submit patch request to Javalin with await fetch()
    let response = await fetch(url + "/ReimbursementRequest/Edit", {
      method: 'PATCH',
      body: JSON.stringify(selectedReimbursement),
      credentials: 'include', //TODO: Is this needed for cookies?
    });

    if (response.status === 200) {
      alert("Request decision was carried out succesfully.")
      location.href = 'http://localhost:8081/RequestPages/ApproveRequestsBobby.html';//reload the current page which will in turn update the table
    }
    else if (response.status === 400) {
      let jsonAnswer = await response.json()
      let errorCode = jsonAnswer.errorCode;

      let errorString = "";
      if (errorCode & 0b1000) errorString += "You can't approve/deny your own requests.\n";
      alert(errorString);
    }

    //step four either remove the request form the table if step three worked
    //   or show an error message

}

function selectFunc(event) {
    let element = event.currentTarget;

    //now style the current row appropriately
    if (element.style.color === "black") {
        //first, iterate through all rows currently in the table and set their color back to the default black
        let allRows = document.getElementsByClassName("jsRow");
        for (let k = allRows.length - 1; k >= 0; k--) {
          allRows[k].style.color = "black";
        }

        //then style the actual row
        element.style.color = "blue";

        //set the currently selected reimbursement to the one that was just clicked on
        //selectedReimbursement = element.childNodes[0].innerHTML;
       let counter = -1;
       for (let field in selectedReimbursement) {
          selectedReimbursement[field] = element.childNodes[++counter].innerHTML
       }
       //console.log("selection should now be updated");
       //console.log(selectedReimbursement);
    }
    else {
        element.style.color = "black";

        //reset the selection
        selectedReimbursement = {
          reimbursementID: 0,
          reimbursementAmount: 0,
          reimbursementSubmitted: null,
          reimbursementResolved: null,
          reimbursementDescription: "",
          reimbursementReceipt: [],
          reimbursementAuthor: 0,
          reimbursementResolver: 0,
          reimbursementStatusId: 0,
          reimbursementTypeId: 0,
        }; 
    }
}