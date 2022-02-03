//DOM Selection
let userName = document.getElementById("username");
let submitButton = document.getElementById("submitButton");

const url = "http://localhost:8081";

submitButton.addEventListener("click", submitFunc);

//All employee types are hidden by default. We "un-hide" them based on who's currently logged in.
// let currentUser = get_cookie("currentUserRole");

// switch(currentUser) {
//     case "3":
//         financeManager.hidden = false;
//         financeAnalyst.hidden = false;
//         break;
//     case "4":
//         nonFinanceManager.hidden = false;
//         engineer.hidden = false;
//         intern.hidden = false;
//         break;
// }

async function submitFunc() {
    //create a newUser object, however, for firing purposes we only need to username
    let newUser = {
        userRoleID: 0,
        username: userName.value,
        password: "",
        firstName: "",
        lastName: "",
        emailAddress: ""
    };

    //we need to make sure that the username field isn't blank
    if (newUser.username == "") {
        alert("Please enter the username of the employee you wish to fire.");
        return;
    }

    let response = await fetch(url + "/users",
        {
            method: "DELETE",
            body: JSON.stringify(newUser),
            credentials: "include"
        }
    );

    if (response.status===200) {
        //Javalin will handle setting appropriate cookies, redirect back to the main page
        //now that our user cookie is set we can redirect to the appropriate location

        //location.href = 'http://localhost:8081/MainPages/CombinedMain.html';
        alert("Employee was fired and successfully removed from the database.");
        location.href = 'http://localhost:8081/EmployeePages/fireEmployee.html';
    } else if (response.status===401){
        alert("You don't have permission to fire this employee.");
    } else if (response.status===404){
        alert("That employee doesn't exist, please re-type the username.");
    } else {
        alert("Couldn't connect to the database");
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