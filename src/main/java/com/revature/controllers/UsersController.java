package com.revature.controllers;

import com.revature.models.users.User;
import com.revature.services.UsersService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UsersController extends Controller{

    private UsersService usersService = new UsersService();

    Handler getUser = (ctx) -> {
        usersService.getUser("sno19");
    };

    Handler getCurrentUser = (ctx) -> {
        //all this function does is check to see if a user is currently logged in, and if so,
        //returns information about them to the client. If no one is currently logged in, then a
        //generic user with blank fields will be sent back to the server.
        if (ctx.req.getSession(false) != null) {
            User currentUser = ctx.sessionAttribute("currentUser");
            ctx.json(currentUser); //send back a JSON object so the browser can compare with its current cookie
        }
        else {
            User blankUser = new User();
            ctx.json(blankUser);
        }

        //either way set the status code to 200
        ctx.status(200);
    };

    @Override
    public void addRoutes(Javalin app) {

        app.get("/users", getUser);
        app.get("/users/currentUser", getCurrentUser);
    }
}
