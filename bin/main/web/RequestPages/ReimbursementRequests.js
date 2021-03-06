let all = document.getElementById('all').value;
let submitted = document.getElementById('submitted').value;
let approved = document.getElementById('approved').value;
let denied = document.getElementById('denied').value;

let allRadio = document.getElementById("all");
let submittedRadio = document.getElementById('submitted');
let approvedRadio = document.getElementById('approved');
let deniedRadio = document.getElementById('denied');
let createdRadio = document.getElementById('created');

let requestTable = document.getElementById("RequestTable");

//Resquest Buttons
let editButton = document.getElementById('edit_btn');
let deleteButton = document.getElementById('delete_btn');

const currentUserUsername = get_cookie("currentUserUsername"); //get name of the logged in user from cookie
const url = "http://localhost:8081";

const reimbursementStatusList = ["null", "created", "submitted", "approved", "denied"]
const reimbursementStatuses = {
  Created: 1,
  Submitted: 2,
  Approved: 3,
  Denied:4
}
const reimbursementTypes = {
  Lodging: 1,
  Food: 2,
  Travel: 3,
  Other: 4
}

var reimbursementData; //this will be filled in on loading of the page
var currentType; //this will be filled in on page load
var selectedReimbursement = {
  reimbursementID: 0,
  reimbursementAmount: 0,
  reimbursementSubmitted: null,
  reimbursementResolved: null,
  reimbursementDescription: "",
  reimbursementReceipt: [],
  reimbursementAuthor: get_cookie("currentUserId"),
  reimbursementResolver: 0,
  reimbursementStatusId: 0,
  reimbursementTypeId: 0,
}; //keeps track of the currently selected reimbursement

//Add event listeners for each radio button that will filter the table
allRadio.addEventListener("click", radioChangeFunc);
submittedRadio.addEventListener("click", radioChangeFunc);
approvedRadio.addEventListener("click", radioChangeFunc);
deniedRadio.addEventListener("click", radioChangeFunc);
createdRadio.addEventListener("click", radioChangeFunc);

editButton.addEventListener("click", editFunc);
deleteButton.addEventListener("click", deleteFunc);

//on page load, reach out to the server to get user reimbursement data and then save it in JS object,
//also display it in the table on screen
window.onload = async () => {

  // Will give you current radio button selected //TODO: set this check query equal to a variable
  currentType = document.querySelector('input[name="request_type"]:checked');

  //get user's 
  let response = await fetch(url + "/ReimbursementRequest"); //just a standard get request
  reimbursementData = await response.json(); //store in a global variable so other functions can access

  loadTable(reimbursementData, currentType.id);
}

function radioChangeFunc() {
   currentType = document.querySelector('input[name="request_type"]:checked');

   //At this point, currentType.id will be equal to the radio button currently selected
   loadTable(reimbursementData, currentType.id);
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

function loadTable(reimbursementList, filter) {
  //if we have a filter applied, only load the appropriate elements

  //first, delete all current data from our table (but not the table header)
  let allRows = document.getElementsByClassName("jsRow");
  for (let k = allRows.length - 1; k >= 0; k--) {
    allRows[k].remove();
  }

  //now loop through the current user's reimbursement list which was saved in a global variable upon page load
  for (let request of reimbursementList) {

    if (filter == "all" || (reimbursementStatusList[request.reimbursementStatusId] == filter)) {
      //we only want to display table rows that match our filter
      let row = document.createElement("tr");
      row.classList.add("jsRow");
      row.addEventListener("click", selectFunc); //each row needs its own eventListener

      for (let data in request) {
        let td = document.createElement("td");
        
        //currently our reimbursement status and type are integers, but it would be easier to view these in string form
        //perform the conversion now. Also, if we're looking at a timestamp then we need to convert it. Also, don't bother
        //adding the current user ID to the table (because the current user will clearly know who they are).

        if (data == "reimbursementStatusId") td.innerText = convertStatus(request[data]);
        else if (data == "reimbursementTypeId") td.innerText = convertType(request[data]);
        else if (data == "reimbursementSubmitted" || data == "reimbursementResolved") {
          if (request[data] != null){
            let date = new Date(request[data]);
            td.innerText = date.toLocaleString();
          }
          else td.innerText = "";
        }
        else if (data == "reimbursementAuthor") continue; //no point in loading author data as only the author has access to the page
        else td.innerText = request[data];

        row.appendChild(td);
      }
      requestTable.appendChild(row);
    }
  }
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
        if (field == "reimbursementAuthor") continue; //we don't keep track of this in the table so skip it
        else if (field == "reimbursementStatusId") selectedReimbursement[field] = reimbursementStatuses[element.childNodes[++counter].innerHTML];
        else if (field == "reimbursementTypeId") selectedReimbursement[field] = reimbursementTypes[element.childNodes[++counter].innerHTML];
        else selectedReimbursement[field] = element.childNodes[++counter].innerHTML
     }

     console.log(selectedReimbursement);
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
        reimbursementAuthor: get_cookie("currentUserId"),
        reimbursementResolver: 0,
        reimbursementStatusId: 0,
        reimbursementTypeId: 0,
      }; 
  }
}

function editFunc() {
  if (selectedReimbursement.reimbursementStatusId != 1 && selectedReimbursement.reimbursementStatusId != 4) {
    alert("Only reimbursement requests with 'Created' or 'Denied' status are eligible for editing.");
    return;
  }

  //set cookie before going to the edit page
  document.cookie = "reimbursementRequest=" + selectedReimbursement.reimbursementID;
  location.href = 'http://localhost:8081/RequestPages/EditRequest.html';
}

async function deleteFunc() {
  if (selectedReimbursement.reimbursementStatusId == 1) {
    let result = await fetch(url + "/ReimbursementRequest", {
      method: 'DELETE',
      body: JSON.stringify(selectedReimbursement),
      credentials: 'include', //TODO: Is this needed for cookies?
    });

    if (result.status === 200) {
      alert("Request was successfully deleted from the database.");
      location.href = 'http://localhost:8081/RequestPages/ReimbursementRequestsBobby.html';
    }
    else if (result.status === 400) {
      alert("There was an issue when trying to connect to the database.");
    }
  }
  else {
    alert("You can only delete requests that haven't already been submit for approval.");
  }
}