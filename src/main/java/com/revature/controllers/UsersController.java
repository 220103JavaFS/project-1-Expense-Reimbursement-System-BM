package com.revature.controllers;

import com.revature.services.UsersService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UsersController extends Controller{

    private UsersService usersService = new UsersService();

    Handler getUser = (ctx) -> {
        usersService.getUser("sno19");
    };

    @Override
    public void addRoutes(Javalin app) {
        app.get("/users", getUser);
    }
}
