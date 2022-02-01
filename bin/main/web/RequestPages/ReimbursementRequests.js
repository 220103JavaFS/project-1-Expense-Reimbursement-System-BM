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
