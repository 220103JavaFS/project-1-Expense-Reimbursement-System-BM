//let created = document.getElementById('created').value;
let submitted = document.getElementById('submitted').value;
let approved = document.getElementById('approved').value;
let denied = document.getElementById('denied').value;

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

//Add event listeners for each radio button that will filter the table
submittedRadio.addEventListener("click", radioChangeFunc);
approvedRadio.addEventListener("click", radioChangeFunc);
deniedRadio.addEventListener("click", radioChangeFunc);

//on page load, reach out to the server to get user reimbursement data and then save it in JS object,
//also display it in the table on screen
window.onload = async () => {

  //get user's 
  let response = await fetch(url + "/ReimbursementRequest"); //just a standard get request
  var reimbursementData = await response.json(); //store in a global variable so other functions can access

  for (let request of reimbursementData) {
    let row = document.createElement("tr");
    for (let data in request) {
      let td = document.createElement("td");
      td.innerText = request[data];
      row.appendChild(td);
    }
    requestTable.appendChild(row);
  }
}

function radioChangeFunc() {
   currentType = document.querySelector('input[name="request_type"]:checked');


   //At this point, currentType.id will be equal to the radio button currently selected
   console.log(currentType.id);
}

// Will give you current radio button selected //TODO: set this check query equal to a variable
var currentType = document.querySelector('input[name="request_type"]:checked');

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