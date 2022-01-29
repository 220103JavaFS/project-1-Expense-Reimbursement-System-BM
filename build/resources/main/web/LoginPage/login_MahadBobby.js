//DOM Selection
let userName = document.getElementById('floatingInput');
let passWord = document.getElementById("floatingPassword");
let button = document.getElementById("loginButton");
let mainButton = document.getElementById("mainPageButton");

const url = "http://localhost:8081";

button.addEventListener("click", loginFunc);
mainButton.addEventListener("click", mainPageFunc);

async function loginFunc() {
    let loginAttempt = {
        username: userName.value,
        password: passWord.value
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
        //it can take awhile to write an object to JSON, so we put the await here to make sure it finishes before continuing
        //otherwise me might log nothing in the next line of code
        let data = await response.json(); //print something back from Javlin to the console as a means of debugging for now
        console.log(data);

        //after sucessfull login the user should be redirected back to the main webpage, however, there are different
        //versions of this page depending on what kind of user is logged in. Check the user type received from web server
        //and redirect accordingly. TODO: should the response status change to 300 for a redirection?
        //let userType = JSON.parse(data);
        console.log("The user role type is: " + data.userRoleID);

        //now that our user cookie is set we can redirect to the appropriate location
        if (data.userRoleID == "") location.href = 'http://localhost:8081/MainPages/NotLoggedInMain.html';
        else if (data.userRoleID == "3") location.href = 'http://localhost:8081/MainPages/ApproverMainBobby.html';
        else location.href = 'http://localhost:8081/MainPages/NonApproverMain.html';
    } else if (response.status===401){
        alert("You're already logged in, please log out first to login with another user.");
    } else {
        alert("Couldn't find user, either the username or password was incorrect. Please retype and try again.");
    }
}

async function mainPageFunc() {
    //takes the user back to the main page. If no one is currently logged in it will be the default main page,
    //otherwise it will be the main page for the appropriate user type

    //first, check the Javalin server to make sure someone is supposed to be logged in (even if we have user cookie information
    //in the browser, its possible that it's just a remnent from another user's previous session and no one is currently stored in the 
    //current Javalin session).
    let response = await fetch(url + "/users/currentUser");

    if (response.status === 200) {
        let currentUser = await response.json();

        //console.log(currentUser.username);

        //...and then go to the appropriate location
        if (currentUser.username == null) location.href = 'http://localhost:8081/MainPages/NotLoggedInMain.html';
        else if (currentUser.userRoleID == "3") location.href = 'http://localhost:8081/MainPages/ApproverMainBobby.html';
        else location.href = 'http://localhost:8081/MainPages/NonApproverMain.html';
    }
    else {
        //there was some error trying to reach the server, default to the values stored in userRoleID cookie
        let curretUserIDCookie = get_cookie(userRoleID);

        //console.log("current userCookie says: " + curretUserIDCookie);
        
        if (curretUserIDCookie == "") location.href = 'http://localhost:8081/MainPages/NotLoggedInMain.html';
        else if (curretUserIDCookie == "3") location.href = 'http://localhost:8081/MainPages/ApproverMainBobby.html';
        else location.href = 'http://localhost:8081/MainPages/NonApproverMain.html';
    }
    
}

function get_cookie(Name) {
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

function getAllCookies() {
  console.log("There are: " + document.cookie.length + " cookies currently.");
}