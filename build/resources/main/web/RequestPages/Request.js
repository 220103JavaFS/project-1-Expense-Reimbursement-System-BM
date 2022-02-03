//DOM Selection
let amount = document.getElementById('amount');
let description = document.getElementById('Description');
//let receipt = document.getElementById('Receipt').value;
let type = document.getElementById('type');
//let status = document.getElementById('Status').value;

let button = document.getElementById('submit');

const url = 'http://localhost:8081';

button.addEventListener('click', requestFunc);

async function requestFunc() {
  let reimbursementRequest = {
    reimbursementAmount: amount.value,
    reimbursementDescription: description.value,
    //receipt: receipt,
    reimbursementTypeId: type.value,
    //status: status,
  };

  if (
    reimbursementRequest.reimbursementAmount == '' ||
    reimbursementRequest.reimbursementTypeId == ''
    //reimbursementRequest.status == ''
  ) {
    //I want to send some kind of error message to the page here
    //alert("Username and/or password fields can't be blank.");
    alert('Please make sure Amount/Request Type fields are not empty.');
    return;
  }

  let response = await fetch(url + '/ReimbursementRequest/Request', {
    method: 'POST',
    body: JSON.stringify(reimbursementRequest),
    credentials: 'include', //TODO: Is this needed for cookies?
  });

  if (response.status === 200) {
    //it can take awhile to write an object to JSON, so we put the await here to make sure it finishes before continuing
    //otherwise me might log nothing in the next line of code
    let data = await response.json(); //print something back from Javlin to the console as a means of debugging for now
    console.log(data);
    location.href = 'http://localhost:8081/MainPages/NonApproverMain.html';
  } else {
    alert('Amount is not valid. Please retype and try again.');
  }
}
