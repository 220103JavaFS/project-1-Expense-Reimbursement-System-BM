//DOM Selection

//Radio Buttons
let created = document.getElementById('created').value;
let submitted = document.getElementById('submitted').value;
let approved = document.getElementById('approved').value;
let denied = document.getElementById('denied').value;

//Resquest Buttons
let editButton = document.getElementById('edit_btn').value;
let newButton = document.getElementById('new_btn').value;
let deleteButton = document.getElementById('delete_btn').value;

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

// const url = 'http://localhost:8081';

// editButton.addEventListener('click', editFunc);
// newButton.addEventListener('click', newtFunc);
// deleteButton.addEventListener('click', deleteFunc);

// async function requestFunc() {
//   let reimbursementRequest = {
//     amount: amount,
//     description: description,
//     receipt: receipt,
//     type: type,
//     status: status,
//   };

//   if (
//     reimbursementRequest.amount == '' ||
//     reimbursementRequest.type == '' ||
//     reimbursementRequest.status == ''
//   ) {
//     //I want to send some kind of error message to the page here
//     //alert("Username and/or password fields can't be blank.");
//     alert(
//       'Please make sure Amount/Request Type/Current Status fields are not empty.'
//     );
//     return;
//   }

//   let response = await fetch(url + '/request', {
//     method: 'POST',
//     body: JSON.stringify(reimbursementRequest),
//     credentials: 'include', //TODO: Is this needed for cookies?
//   });

//   if (response.status === 200) {
//     //it can take awhile to write an object to JSON, so we put the await here to make sure it finishes before continuing
//     //otherwise me might log nothing in the next line of code
//     let data = await response.json(); //print something back from Javlin to the console as a means of debugging for now
//     console.log(data);
//     location.href = 'http://localhost:8081/MainPages/NonApproverMain.html';
//   } else {
//     alert('Amount is not valid. Please retype and try again.');
//   }
// }
