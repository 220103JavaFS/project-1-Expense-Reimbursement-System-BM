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
        //Javalin will handle setting appropriate cookies, redirect back to the main page
        //now that our user cookie is set we can redirect to the appropriate location
        mainPageFunc();

    } else if (response.status===401){
        alert("You're already logged in, please log out first to login with another user.");
    } else {
        alert("Couldn't find user, either the username or password was incorrect. Please retype and try again.");
    }
}

function mainPageFunc() {
    location.href = 'http://localhost:8081/mainPage.html';
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