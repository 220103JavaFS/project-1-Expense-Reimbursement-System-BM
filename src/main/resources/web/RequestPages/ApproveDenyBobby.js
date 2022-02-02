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

//Add event listeners
approvedRadio.addEventListener("click", radioChangeFunc);
deniedRadio.addEventListener("click", radioChangeFunc);
submitButton.addEventListener("click", submitFunc);

//on page load, reach out to the server to get user reimbursement data and then save it in JS object,
//also display it in the table on screen
window.onload = async () => {

  //get user's 
  let response = await fetch(url + "/ReimbursementRequest/Approval"); //just a standard get request
  reimbursementData = await response.json(); //store in a global variable so other functions can access

  loadTable(reimbursementData);
}

function radioChangeFunc() {
   currentRadioType = document.querySelector('input[name="request_type"]:checked');
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
    row.addEventListener("click", testFunc);

    for (let data in request) {
      let td = document.createElement("td");
      
      td.innerText = request[data];
      row.appendChild(td); 
    }
    requestTable.appendChild(row);
  }
}

function submitFunc() {
    //Step one, look at the current value of the radio button
    //step two, look at the currently selected request
    //step three submit patch request to Javalin with await fetch()

    //step four either remove the request form the table if step three worked
    //   or show an error message

}

function testFunc(event) {
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
    }
    else element.style.color = "black";
}