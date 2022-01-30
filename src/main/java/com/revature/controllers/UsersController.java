package com.revature.controllers;

import com.revature.models.users.User;
import com.revature.repos.UserDAOImpl;
import com.revature.repos.UsersDAO;
import com.revature.services.UsersService;
import com.revature.util.NewUser;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UsersController extends Controller{

    private UsersService usersService = new UsersService();
    private UsersDAO ud = new UserDAOImpl();

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

        //System.out.println(newUser);
        //System.out.println(currentUser);

        //System.out.println(ud.availableUsernameEmail(newUser.username, newUser.emailAddress));

        boolean employeeHired = usersService.hireEmployee(currentUser, newUser);

        if (employeeHired) ctx.status(200);
        else ctx.status(400);
    };

    @Override
    public void addRoutes(Javalin app) {

        app.get("/users", getUser);
        app.get("/users/currentUser", getCurrentUser);
        app.post("/users", hireEmployee);
    }
}
