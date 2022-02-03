//DOM Selection
let amount = document.getElementById('amount');
let description = document.getElementById('description');
let receipt = document.getElementById('receipt');
let type = document.getElementById('type');
let currentStatus = document.getElementById('status');

let button = document.getElementById('submit');

const url = 'http://localhost:8081';

button.addEventListener('click', requestFunc);

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
  
  // console.log("amount = " + amount);
  // console.log("type = " + type);
  // console.log("status = " + currentStatus);

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
    reimbursementID: 0,
    reimbursementAmount: amount.value,
    reimbursementSubmitted: null,
    reimbursementResolved: null,
    reimbursementDescription: description.value,
    reimbursementReceipt: null,
    reimbursementAuthor: 0,
    reimbursementResolver: 0,
    reimbursementStatusId: requestStatuses[currentStatus.value],
    reimbursementTypeId: requestTypes[type.value],
  };

  try {
    let reciptBuffer = await receipt.files[0].arrayBuffer();
    let unsignedArray = new Uint8Array(reciptBuffer)
    let actualArray = [];
    for (let byte in unsignedArray) actualArray.push(unsignedArray[byte]);

    reimbursementRequest.reimbursementReceipt = actualArray;
  }
  catch (e) {
      console.log("No receipt detected.");
  }

  let response = await fetch(url + '/ReimbursementRequest', {
    method: 'POST',
    body: JSON.stringify(reimbursementRequest),
    credentials: 'include', //TODO: Is this needed for cookies?
  });

  if (response.status === 200) {
    //it can take awhile to write an object to JSON, so we put the await here to make sure it finishes before continuing
    //otherwise me might log nothing in the next line of code
    alert('New request was successfully made.');
    location.href = 'http://localhost:8081/RequestPages/CreateRequest.html';
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
