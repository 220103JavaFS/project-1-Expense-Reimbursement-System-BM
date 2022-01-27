package com.revature;

import com.revature.controllers.Controller;
import com.revature.controllers.LoginController;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class App {

    private static Javalin app;

    public static void main(String[] args) {

        app = Javalin.create((config)->{
            config.addStaticFiles("/web", Location.CLASSPATH);
        });
        configure(new LoginController());

        app.start(8081); //changed this because there's now a Jenkins instance set on port 8080 of my PC
    }

    public static void configure(Controller... controllers) {
        for (Controller c:controllers) {
            c.addRoutes(app);
        }
    }
}
