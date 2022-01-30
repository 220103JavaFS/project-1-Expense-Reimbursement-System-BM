//DOM Selection
let userName = document.getElementById("username");
let passWord = document.getElementById("password");
let first_name = document.getElementById("firstName");
let last_name = document.getElementById("lastName");
let email = document.getElementById("email");
let employeeType = document.getElementById("emplyeeType");
let submitButton = document.getElementById("submitButton");

const url = "http://localhost:8081";

submitButton.addEventListener("click", submitFunc);

async function submitFunc() {
    //first convert userType text to useable Role ID number:
    let userRole = 0;
    switch(employeeType) {
        case "finanaceManager":
            userRole = 3;
            break;
        case "nonFinanaceManager":
            userRole = 4;
            break;
        case "finanaceAnalyst":
            userRole = 5;
            break;
        case "engineer":
            userRole = 6;
            break;
        default:
            userRole = 7;
            break;
    }

    //the userID doesn't matter as it will be assigned upon entry to the database
    let newUser = {
        userRoleID: userRole,
        userID: 0,
        username: userName.value,
        password: passWord.value,
        firstName: first_name,
        lastName: last_name,
        emailAddress: email
    };

    if (loginAttempt.username == "" || loginAttempt.password == "") {
        //I want to send some kind of error message to the page here
        //alert("Username and/or password fields can't be blank.");
        alert("Username and/or password fields can't be blank.");
        return;
    }

    let response = await fetch(url + "/login",
        {
            method: "POST",
            body: JSON.stringify(loginAttempt),
            credentials: "include"
        }
    );

    if (response.status===200) {
        //Javalin will handle setting appropriate cookies, redirect back to the main page
        //now that our user cookie is set we can redirect to the appropriate location
        location.href = 'http://localhost:8081/MainPages/CombinedMain.html';

    } else if (response.status===401){
        alert("You're already logged in, please log out first to login with another user.");
    } else {
        alert("Couldn't find user, either the username or password was incorrect. Please retype and try again.");
    }
}