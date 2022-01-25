package com.revature;

import com.revature.controllers.Controller;
import com.revature.controllers.TestController;
import com.revature.models.users.User;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class App {

    private static Javalin app;

    public static void main(String[] args) {

        String originalPassword = "Haw4infect";
        byte[] encryptedPassword = User.encryptPassword(originalPassword);

        String decryptedPassword = User.decryptPassword(encryptedPassword);

        System.out.println(originalPassword);
        System.out.println(decryptedPassword);

        app = Javalin.create((config)->{
            config.addStaticFiles("/web", Location.CLASSPATH);
        });
        configure(new TestController());
        app.start();
    }

    public static void configure(Controller... controllers) {
        for (Controller c:controllers) {
            c.addRoutes(app);
        }
    }
}
