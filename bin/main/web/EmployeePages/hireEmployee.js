//DOM Selection
let userName = document.getElementById("username");
let passWord = document.getElementById("password");
let first_name = document.getElementById("firstName");
let last_name = document.getElementById("lastName");
let email = document.getElementById("email");
let employeeType = document.getElementById("employeeType");
let submitButton = document.getElementById("submitButton");

let financeManager = document.getElementById("financeManager");
let nonFinanceManager = document.getElementById("nonFinanceManager");
let financeAnalyst = document.getElementById("financeAnalyst");
let engineer = document.getElementById("engineer");
let intern = document.getElementById("intern");

const url = "http://localhost:8081";

submitButton.addEventListener("click", submitFunc);

//All employee types are hidden by default. We "un-hide" them based on who's currently logged in.
let currentUser = get_cookie("currentUserRole");

switch(currentUser) {
    case "3":
        financeManager.hidden = false;
        financeAnalyst.hidden = false;
        break;
    case "4":
        nonFinanceManager.hidden = false;
        engineer.hidden = false;
        intern.hidden = false;
        break;
}

async function submitFunc() {
    //first convert userType text to useable Role ID number:

    let userRole = 0;
    switch(employeeType.value) {
        case "financeManager":
            userRole = 3;
            break;
        case "nonFinanceManager":
            userRole = 4;
            break;
        case "financeAnalyst":
            userRole = 5;
            break;
        case "engineer":
            userRole = 6;
            break;
        case "intern":
            userRole = 7;
            break;
        default:
            userRole = 0; //leave the value as 0
            break;
    }

    //the userID doesn't matter as it will be assigned upon entry to the database
    let newUser = {
        userRoleID: userRole,
        username: userName.value,
        password: passWord.value,
        firstName: first_name.value,
        lastName: last_name.value,
        emailAddress: email.value
    };

    ////TODO: Would be much easier to make use of the the 'required' field for form elements.
    //   currently though, I'm not sure how to check for blanks without submitting the form which causes a
    //   page refresh. For now, is there a better way to check for blanks other than multiple else if statements
    //   or a single if statement with multiple || operators?

    //we need to make sure that none of the form fields are blank:
    let nothingBlank = true;
    if (newUser.username == "") nothingBlank = false;
    else if (newUser.password == "") nothingBlank = false;
    else if (newUser.firstName == "") nothingBlank = false;
    else if (newUser.lastName == "") nothingBlank = false;
    else if (newUser.emailAddress == "") nothingBlank = false;
    else if (newUser.userRoleID == 0) nothingBlank = false;
    
    
    if (!nothingBlank) {
        alert("All fields must be filled out.");
        return;
    }

    let response = await fetch(url + "/users",
        {
            method: "POST",
            body: JSON.stringify(newUser),
            credentials: "include"
        }
    );

    if (response.status===200) {
        //Javalin will handle setting appropriate cookies, redirect back to the main page
        //now that our user cookie is set we can redirect to the appropriate location
        alert("Employee succesfully hired!")
        location.href = 'http://localhost:8081/EmployeePages/hireEmployee.html';

    } else if (response.status===401){
        alert("You don't have access to do that.");
    } else {
        //if we get here it means there was an issue with the request, parse the received error code
        //to find out what went wrong.
        let jsonResponse = await response.json();
        let errorCode = jsonResponse.errorCode; //gets the error code as an integer
        let errorMessage = "";

        //now we check all possible errors (which are defined in the backend). Since there can be multiple
        //errors contained within the same code we only use IF statements and not ELSE IF statements
        if (errorCode & 0b1) errorMessage += "Only managers can hire and employee.\n";
        if (errorCode & 0b10) errorMessage += "You can't hire that type of employee.\n";
        if (errorCode & 0b100) errorMessage += "That username isn't available.\n";
        if (errorCode & 0b1000) errorMessage += "That email address isn't available.\n";
        if (errorCode & 0b10000) errorMessage += "Password needs to be at least 8 characters lone.\n";
        if (errorCode & 0b100000) errorMessage += "Password must have an upper case letter.\n";
        if (errorCode & 0b1000000) errorMessage += "Password must have a lower case letter.\n";
        if (errorCode & 0b10000000) errorMessage += "Password must have at least one number.\n";
        if (errorCode & 0b100000000) errorMessage += "Password must have at least one special character.\n";
        if (errorCode & 0b1000000000) errorMessage += "Password used an incompatible character.\n";
        if (errorCode & 0b10000000000 ) errorMessage += "There was a problem accessing the user database.\n";
        alert(errorMessage);
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