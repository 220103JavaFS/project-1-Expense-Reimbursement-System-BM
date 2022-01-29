package com.revature.controllers;

import com.revature.models.users.User;
import com.revature.services.LoginService;
import com.revature.util.LoginAttempt;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class LoginController extends Controller {

    private LoginService loginService = new LoginService();

    Handler login = (ctx) -> {
        //only allow for login if no user is currently logged in, otherwise prompt the user to logout

        if (ctx.req.getSession(false) != null) {
            ctx.status(401); //not authorized until logout
            ctx.result("Already logged in, please log out to log in with another user.");
        }
        else {
            LoginAttempt log = ctx.bodyAsClass(LoginAttempt.class); //JS logic ensures that username and password aren't blank
            User user = loginService.loginUser(log);

            if (user == null) {
                //either the username wasn't in the database or the password was incorrect
                ctx.result("Sorry, either the username or password was incorrect. Please retype and then try again.");
                ctx.status(400);
            }
            else {
                //We need to do a few things upon successful login. First we start a new Session in Javalin. Then we store
                //the logged in user's attributes in a Javalin session attribute. Finally, we need to create a cookie so
                //that the browser has some idea of the clearance level for the person who just logged in.
                ctx.req.getSession();
                ctx.sessionAttribute("currentUser", user);

                //Now set the userRoleId cookie for the browser
                Integer urid = user.getUserRoleID();
                ctx.cookie("userRoleId", urid.toString());

                ctx.json(user);
                ctx.status(200);
            }
        }
    };

    Handler logout = (ctx) -> {
        //we can only access the logout page if we're actually logged in, or, if there's an existing cookie in the browser
        //(this can happen if we close down the Javalin server without first logging out in the browser).

        if (ctx.req.getSession(false) != null) {
            //log the current user out
            ctx.req.getSession().invalidate();
            ctx.removeCookie("userRoleId"); //remove the userRoleId cookie in the broswer upon logging out.
            ctx.status(202); //return 202 Accepted code
        }
        else {
            //it's possible for the userRoleId cookie to persist in the browser if the Javalin application crashes. Because of this,
            //even if there's no current user data on the Javalin side we need to double-check and make sure that there's also
            //no user information stored on the browser side.
            if (ctx.cookie("userRoleId") == null) ctx.removeCookie("userRoleId"); //remove the userRoleId cookie in the browser if it exists
            ctx.status(401); //no one is logged in, restrict access the logout page
        }
    };

    @Override
    public void addRoutes(Javalin app) {
        app.post("/login", login);
        app.delete("/logout", logout); //felt it was easier to include this in the login controller instead of creating separate logout controller
    }
}
