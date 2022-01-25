package com.revature.controllers;

import com.revature.util.LoginAttempt;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class LoginController extends Controller {

    Handler login = (ctx) -> {
        //only allow for login if no user is currently logged in, otherwise prompt the user to logout
        if (ctx.req.getSession(false) != null) {
            ctx.status(401); //not authorized until logout
            ctx.result("Already logged in, please log out to log in to another user.");
        }
        else {
            LoginAttempt log = ctx.bodyAsClass(LoginAttempt.class);
        }
    };

    @Override
    public void addRoutes(Javalin app) {
        app.post("/login", login);
        //app.delete("/logout", logout);
    }
}
