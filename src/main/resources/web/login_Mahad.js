//DOM Selection
let userName = document.getElementById('username');
let passWord = document.getElementById("password");
let button = document.getElementById("loginButton");

const url = "http://localhost:8080";

button.addEventListener("click", loginFunc);

async function loginFunc() {
    let loginAttempt = {
        username: userName.value,
        password: passWord.value
    }

    if (loginAttempt.username == "" || loginAttempt.password == "") {
        //I want to send some kind of error message to the page here
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
    } else {
        alert("Couldn't find user, either the username or password was incorrect. Please retype and try again.");
    }
}
