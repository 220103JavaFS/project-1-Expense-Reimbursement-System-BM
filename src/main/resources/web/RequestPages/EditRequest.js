//DOM Selection
let amount = document.getElementById('amount');
let description = document.getElementById('description');
let receipt = document.getElementById('receipt');
let type = document.getElementById('type');
let currentStatus = document.getElementById('status');

let button = document.getElementById('submit');
var currenRequestId = get_cookie("currentRequestId");

const url = 'http://localhost:8081';

button.addEventListener('click', requestFunc);

var currentRequest = get_cookie("reimbursementRequest");
var currentUser = get_cookie("currentUserId");

var requestStatuses = {
  created: 1,
  submitted: 2,
  approved: 3,
  denied: 4
}

var requestTypes = {
  lodging: 1,
  food: 2,
  travel: 3,
  other: 4
}

async function requestFunc() {
  
  if (
    amount.value == '' ||
    (type.value == '' ||
    currentStatus.value == '')
  ) {
    //I want to send some kind of error message to the page here
    //alert("Username and/or password fields can't be blank.");
    alert(
      'Please make sure Amount/Request Type/Current Status fields are not empty.'
    );
    return;
  }

  let reimbursementRequest = {
    reimbursementID: Number(currentRequest),
    reimbursementAmount: amount.value,
    reimbursementSubmitted: null,
    reimbursementResolved: null,
    reimbursementDescription: description.value,
    reimbursementReceipt: null,
    reimbursementAuthor: currentUser,
    reimbursementResolver: 0,
    reimbursementStatusId: requestStatuses[currentStatus.value],
    reimbursementTypeId: requestTypes[type.value],
  };

  try {
    let reciptBuffer = await receipt.files[0].arrayBuffer();
    reimbursementRequest.reimbursementReceipt = new Uint8Array(reciptBuffer);
  }
  catch (e) {
      console.log("No receipt detected.");
  }

  //console.log(reimbursementRequest.reimbursementReceipt);

  let response = await fetch(url + '/ReimbursementRequest/Edit', {
    method: 'PATCH',
    body: JSON.stringify(reimbursementRequest),
    credentials: 'include', //TODO: Is this needed for cookies?
  });

  if (response.status === 200) {
    //it can take awhile to write an object to JSON, so we put the await here to make sure it finishes before continuing
    //otherwise me might log nothing in the next line of code
    alert('Request was successfully edited.');
    location.href = 'http://localhost:8081/RequestPages/ReimbursementRequestsBobby.html';
  }
  else if (response.status == 400) {
    let jsonResponse = await response.json();
    let errorCode = jsonResponse.errorCode; //gets the error code as an integer
    let errorMessage = "";

    if (errorCode & 0b1) errorMessage += "Can't input a negative value.\n";
    if (errorCode & 0b10) errorMessage += "Requests over $500.00 MUST have a receipt attached.\n";
    if (errorCode & 0b100) errorMessage += "Request description must be less than 250 characters in length.\n";
    if (errorCode & 0b1000) errorMessage += "Error while communicating with the database.\n";

    alert(errorMessage);
  }
  else {
    alert('Amount is not valid. Please retype and try again.');
  }
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
