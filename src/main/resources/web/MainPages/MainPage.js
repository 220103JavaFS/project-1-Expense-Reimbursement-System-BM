//DOM Selection
let logoutButton = document.getElementById("logoutButton");

const url = "http://localhost:8081";

logoutButton.addEventListener("click", logoutFunc);

async function logoutFunc() {

    let response = await fetch(url + "/logout",
        {
            method: "DELETE",
            credentials: "include"
        }
    );

    if (response.status===202) {
        //The user has been successfully logged out, go to the non logged in user main page
        location.href = 'http://localhost:8081/MainPages/NotLoggedInMain.html';

    } else {
        alert("No one is logged in, redirecting.");
        location.href = 'http://localhost:8081/MainPages/NotLoggedInMain.html';
    }
}

function mainPageFunc() {
    //takes the user back to the main page. If no one is currently logged in it will be the default main page,
    //otherwise it will be the main page for the appropriate user type

    //check to see if we have an active cookie...
    let userType = get_cookie("userRoleId");

    //...and then go to the appropriate location
    if (userType == "") location.href = 'http://localhost:8081/MainPages/NotLoggedInMain.html';
    else if (userType == "3") location.href = 'http://localhost:8081/MainPages/ApproverMain.html';
    else location.href = 'http://localhost:8081/MainPages/NonApproverMain.html';
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