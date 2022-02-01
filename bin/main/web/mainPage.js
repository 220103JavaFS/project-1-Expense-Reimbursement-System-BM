//DOM Selection
let loginButton = document.getElementById("loginButton");
let logoutButton = document.getElementById("logoutButton");
let approveButton = document.getElementById("approveButton");
let viewButton = document.getElementById("viewButton");
let editButton = document.getElementById("editButton");
let hireButton = document.getElementById("hireButton");
let fireButton = document.getElementById("fireButton");
let welcomeMessage = document.getElementById("welcomeMessage");

const url = "http://localhost:8081";

logoutButton.addEventListener("click", logoutFunc);
approveButton.addEventListener("click", approveFunc);
viewButton.addEventListener("click", viewFunc);
editButton.addEventListener("click", editFunc);
hireButton.addEventListener("click", hireFunc);
fireButton.addEventListener("click", fireFunc);

//Upon opening or reloading this page, we need to ask the database who's currently logged in.
//This information is stored in the Javalin session. We might already have information on the current user
//in the browser cookie, however, if the Javalin server stops before we explicitly logout then the browser
//cookie information will no longer match the Javalin session information. We do this check on page reload
//just to confirm everything matches (this check should happen on all HTML pages for this project).
let currentUser = get_cookie("currentUserRole");

//All page buttons are hidden by default. We "un-hide" them based on who's currently logged in
if (currentUser == "") {
    loginButton.hidden = false;
}
else {
    welcomeMessage.innerText = "Welcome " + get_cookie("currentUserName") + "! What would you like to do today?";

    switch(currentUser) {
        case "2":
            logoutButton.hidden = false;
            break;
        case "1":
            logoutButton.hidden = false;
            viewButton.hidden = false;
            break;
        case "3":
            logoutButton.hidden = false;
            viewButton.hidden = false;
            approveButton.hidden = false;
            editButton.hidden = false;
            hireButton.hidden = false;
            fireButton.hidden = false;
            break;
        case "4":
            logoutButton.hidden = false;
            viewButton.hidden = false;
            editButton.hidden = false;
            hireButton.hidden = false;
            fireButton.hidden = false;
            break;
        default:
            logoutButton.hidden = false;
            viewButton.hidden = false;
            editButton.hidden = false;
            break;
    }
}


async function logoutFunc() {

    await fetch(url + "/logout",
        {
            method: "DELETE",
            credentials: "include"
        }
    );

    //"refresh" the main page after logout
    location.href = 'http://localhost:8081/mainPage.html';
}

async function approveFunc() {
    //before going to the edit page, get the current user from the Javalin session. Make sure it matches
    //what's currently in the cookie
    let currentJavalinUser = await getCurrentUser(); //make sure that user cookie is up to date
    if (currentJavalinUser == null || currentJavalinUser.username != get_cookie("currentUserUsername")) {
        alert("You're connection has timed out, please log back in and try again.");
        logoutFunc()//call the logout function as the browser and javalin cookies no longer match
    }
    else location.href =  'http://localhost:8081/MainPages/CombinedMain.html';
}

async function viewFunc() {
    //before going to the edit page, get the current user from the Javalin session. Make sure it matches
    //what's currently in the cookie
    let currentJavalinUser = await getCurrentUser(); //make sure that user cookie is up to date
    if (currentJavalinUser == null || currentJavalinUser.username != get_cookie("currentUserUsername")) {
        alert("You're connection has timed out, please log back in and try again.");
        logoutFunc()//call the logout function as the browser and javalin cookies no longer match
    }
    else location.href =  'http://localhost:8081/RequestPages/ReimbursementRequests.html';
}

async function editFunc() {
    //before going to the edit page, get the current user from the Javalin session. Make sure it matches
    //what's currently in the cookie
    let currentJavalinUser = await getCurrentUser(); //make sure that user cookie is up to date
    if (currentJavalinUser == null || currentJavalinUser.username != get_cookie("currentUserUsername")) {
        alert("You're connection has timed out, please log back in and try again.");
        logoutFunc()//call the logout function as the browser and javalin cookies no longer match
    }
    else location.href = 'http://localhost:8081/RequestPages/Request.html';
}

async function hireFunc() {
    //before going to the edit page, get the current user from the Javalin session. Make sure it matches
    //what's currently in the cookie
    let currentJavalinUser = await getCurrentUser(); //make sure that user cookie is up to date
    if (currentJavalinUser == null || currentJavalinUser.username != get_cookie("currentUserUsername")) {
        alert("You're connection has timed out, please log back in and try again.");
        logoutFunc()//call the logout function as the browser and javalin cookies no longer match
    }
    else location.href = 'http://localhost:8081/EmployeePages/hireEmployee.html';
}

async function fireFunc() {
    //before going to the edit page, get the current user from the Javalin session. Make sure it matches
    //what's currently in the cookie
    let currentJavalinUser = await getCurrentUser(); //make sure that user cookie is up to date
    if (currentJavalinUser == null || currentJavalinUser.username != get_cookie("currentUserUsername")) {
        alert("You're connection has timed out, please log back in and try again.");
        logoutFunc()//call the logout function as the browser and javalin cookies no longer match
    }
    else location.href = 'http://localhost:8081/EmployeePages/fireEmployee.html';
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

async function getCurrentUser() {
    //gets the current user from the Javalin Session Attribute. It's useful to check that this
    //user matches the user currently stored in the browser cookie for current user to make sure
    //they match

    try {
        let response = await fetch(url + "/users/currentUser");

        if (response.status === 200) {
            return await response.json();
        }
        else {
            //if we can't connect to the web server then nobody should be logged in, default to no user in this case
            return null;
        }
    }
    catch (e) {
        return null; //connection to server has been lost, force the user to logout but updating cookie
    }

}