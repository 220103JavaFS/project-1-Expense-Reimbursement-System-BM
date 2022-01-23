package com.revature.controllers;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestController extends Controller {
    private static Logger log = LoggerFactory.getLogger(TestController.class);

    //HANDLERS
    Handler getTest = (ctx) -> {
        log.debug("Here I am.");
    };

    Handler getHTMLOverride = (ctx) -> {
        //creating this handler with a GET path that's the same as one of the HTML pages in the project
        //overrode the GET for that HTML page. Meaning, the HTML page no longer loads. Only the below
        //message will print to the debug log.
        log.debug("Does the HTML page still load?");
    };

    //ENDPOINT DEFINITIONS
    @Override
    public void addRoutes(Javalin app) {
        app.get("/test", getTest);

        //If the below GET is uncommented the HelloFetch.html page will no longer load in the browser
        //app.get("/HelloFetch.html", getHTMLOverride);
    }
}
