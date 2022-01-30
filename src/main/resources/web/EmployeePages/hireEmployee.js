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

    console.log("Current Employee type is:" + employeeType.value);

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
    //   page refresh.

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

        //location.href = 'http://localhost:8081/MainPages/CombinedMain.html';
        console.log("info went through to Javalin");

    } else if (response.status===401){
        alert("You're already logged in, please log out first to login with another user.");
    } else {
        alert("Couldn't find user, either the username or password was incorrect. Please retype and try again.");
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