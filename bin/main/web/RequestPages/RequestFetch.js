//DOM Selection
let amount = document.getElementById('amount').value;
let description = document.getElementById('Description').value;
let receipt = document.getElementById('Receipt').value;
let type = document.getElementById('Type').value;
let status = document.getElementById('Status').value;

let button = document.getElementById('submit');

const url = 'http://localhost:8081';
let restApi = 'https://reqres.in/api/register';
button.addEventListener('click', requestFunc);

async function requestFunc() {
  let reimbursementRequest = {
    amount: amount,
    description: description,
    receipt: receipt,
    type: type,
    status: status,
  };

  if (
    reimbursementRequest.amount == '' ||
    reimbursementRequest.type == '' ||
    reimbursementRequest.status == ''
  ) {
    //I want to send some kind of error message to the page here
    //alert("Username and/or password fields can't be blank.");
    alert(
      'Please make sure Amount/Request Type/Current Status fields are not empty.'
    );
    return;
  }

  let response = await fetch(url + '/request', {
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
// const sendHTTPRequest = async (method, url, data) => {
//   const response = await fetch(url, {
//     method: method,
//     body: JSON.stringify(data),
//     headers: data ? { 'Content-Type': 'application/json' } : {},
//   });
//   if (response.status >= 400) {
//     return response.json().then((errResData) => {
//       const error = new Error('Something went wrong!');
//       error.data = errResData;
//       throw error;
//     });
//   }
//   return await response.json();
// };

// const sendData = () => {
//   sendHTTPRequest('POST', restApi, {
//     _email: 'eve.holt@reqres.in',
//     get email() {
//       return this._email;
//     },
//     set email(value) {
//       this._email = value;
//     },
//     _password: 'pistol',
//     get password() {
//       return this._password;
//     },
//     set password(value) {
//       this._password = value;
//     },
//   })
//     .then((responseData) => {
//       console.log(responseData);
//     })
//     .catch((err) => {
//       console.log(err, err.data);
//     });
// };

// button.addEventListener('click', sendData);
