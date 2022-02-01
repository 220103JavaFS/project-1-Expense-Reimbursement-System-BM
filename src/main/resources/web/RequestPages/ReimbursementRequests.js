let all = document.getElementById('all').value;
let submitted = document.getElementById('submitted').value;
let approved = document.getElementById('approved').value;
let denied = document.getElementById('denied').value;

let allRadio = document.getElementById("all");
let submittedRadio = document.getElementById('submitted');
let approvedRadio = document.getElementById('approved');
let deniedRadio = document.getElementById('denied');

let requestTable = document.getElementById("RequestTable");

//Resquest Buttons
//let editButton = document.getElementById('edit_btn').value;
let newButton = document.getElementById('new_btn').value;
let deleteButton = document.getElementById('delete_btn').value;

const currentUserUsername = get_cookie("currentUserUsername"); //get name of the logged in user from cookie
const url = "http://localhost:8081";

const reimbursementStatusList = ["null", "created", "submitted", "denied", "approved"]
var reimbursementData; //this will be filled in on loading of the page
var currentType; //this will be filled in on page load

//Add event listeners for each radio button that will filter the table
allRadio.addEventListener("click", radioChangeFunc)
submittedRadio.addEventListener("click", radioChangeFunc);
approvedRadio.addEventListener("click", radioChangeFunc);
deniedRadio.addEventListener("click", radioChangeFunc);

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
   console.log(currentType.id);
   loadTable(reimbursementData, currentType.id);
}



// This table is where all the user's requests will display
var table = document.getElementById('RequestTable'),
  rIndex;
//requestId is the Reimbursement ID of the request selected
var requestId = '';
for (var i = 0; i < table.rows.length; i++) {
  table.rows[i].onclick = function () {
    rIndex = this.rowIndex;
    requestId = this.cells[0].innerHTML;
  };
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

  //first, delete all current data from our table
  let allRows = document.getElementsByClassName("jsRow");
  for (let k = allRows.length - 1; k >= 0; k--) {
    allRows[k].remove();
  }

  for (let request of reimbursementList) {

    if (filter == "all" || (reimbursementStatusList[request.reimbursementStatusId] == filter)) {
      //we only want to display table rows that match our filter
      let row = document.createElement("tr");
      row.classList.add("jsRow");

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
        else td.innerText = request[data];

        row.appendChild(td);
      }
      requestTable.appendChild(row);
    }
  }
}