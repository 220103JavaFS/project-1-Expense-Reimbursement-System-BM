package com.revature.controllers;

import com.revature.models.users.User;
import com.revature.services.LoginService;
import com.revature.util.LoginAttempt;
import io.javalin.Javalin;
import io.javalin.http.Cookie;
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

                //create a few basic cookies to help the browser keep track of who is currently logged in
                Integer userRole = user.getUserRoleID();
                Integer userId = user.getUserID();
                ctx.cookie("currentUserRole", userRole.toString());
                ctx.cookie("currentUserName", user.getFirstName());
                ctx.cookie("currentUserUsername", user.getUsername());
                ctx.cookie("currentUserId", userId.toString());

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
            ctx.status(202); //return 202 Accepted code
        }

        //it's possible for cookies to persist in the browser if the Javalin application crashes. Because of this,
        //even if there's no current user data on the Javalin side we need to double-check and make sure that there's also
        //no user information stored on the browser side in cookies.
        if (ctx.cookie("currentUserRole") != null) ctx.removeCookie("currentUserRole");
        if (ctx.cookie("currentUserName") != null)ctx.removeCookie("currentUserName");
        if (ctx.cookie("currentUserUsername") != null)ctx.removeCookie("currentUserUsername");
        if (ctx.cookie("currentUserId") != null)ctx.removeCookie("currentUserId");
    };

    @Override
    public void addRoutes(Javalin app) {
        app.post("/login", login);
        app.delete("/logout", logout); //felt it was easier to include this in the login controller instead of creating separate logout controller
    }
}
