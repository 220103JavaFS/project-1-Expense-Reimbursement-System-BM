package com.revature;

import com.revature.controllers.Controller;
import com.revature.controllers.LoginController;
import com.revature.models.users.Intern;
import com.revature.models.users.User;
import com.revature.repos.UserDAOImpl;
import com.revature.services.LoginService;
import com.revature.util.LoginAttempt;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class App {

    private static Javalin app;
    private static UserDAOImpl userDAO = new UserDAOImpl();
    private static LoginService loginService = new LoginService();

    public static void main(String[] args) {

        app = Javalin.create((config)->{
            config.addStaticFiles("/web", Location.CLASSPATH);
        });
        configure(new LoginController());

        //This SHOULD be included with git push...

        app.start(8081); //changed this because there's now a Jenkins instance set on port 8080 of my PC
    }

    public static void configure(Controller... controllers) {
        for (Controller c:controllers) {
            c.addRoutes(app);
        }
    }
}
