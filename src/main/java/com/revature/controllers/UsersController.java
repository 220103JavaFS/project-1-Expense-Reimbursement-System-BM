package com.revature.controllers;

import com.revature.models.users.User;
import com.revature.repos.UserDAOImpl;
import com.revature.repos.UsersDAO;
import com.revature.services.UsersService;
import com.revature.util.AppErrorCode;
import com.revature.util.NewUser;
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

    Handler hireEmployee = (ctx) -> {
        //I'm not going to add logic to make sure that a user is logged in directly here. A user needs to be logged in
        //to reach the page that executes this handler, and there's even a separate check when they click the button
        //to submit employee information so a third login check here would just be redundant.
        NewUser newUser = ctx.bodyAsClass(NewUser.class);
        User currentUser = ctx.sessionAttribute("currentUser");

        int employeeHired = usersService.hireEmployee(currentUser, newUser); //TODO: Want to change return type to int for returning of distinct errors

        if (employeeHired == 0) ctx.status(200);
        else {
            AppErrorCode errorCode = new AppErrorCode(employeeHired);
            ctx.json(errorCode);
            ctx.status(400);
        }
    };

    Handler fireEmployee = (ctx) -> {
        //I'm not going to add logic to make sure that a user is logged in directly here. A user needs to be logged in
        //to reach the page that executes this handler, and there's even a separate check when they click the button
        //to submit employee information so a third login check here would just be redundant.
        NewUser firedUsername = ctx.bodyAsClass(NewUser.class); //we're only interested in the username here

        User currentUser = ctx.sessionAttribute("currentUser");
        int fired = usersService.fireEmployee(currentUser, firedUsername.username);

        if (fired == 0) ctx.status(200);
        else if (fired == 1) ctx.status(401);
        else if (fired == 2) ctx.status(404);
        else ctx.status(500);
    };

    @Override
    public void addRoutes(Javalin app) {

        app.get("/users", getUser);
        app.get("/users/currentUser", getCurrentUser);
        app.post("/users", hireEmployee);
        app.delete("/users", fireEmployee);
    }
}
