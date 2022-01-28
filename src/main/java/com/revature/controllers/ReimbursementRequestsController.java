package com.revature.controllers;

import com.revature.models.users.User;
import com.revature.services.ReimbursementRequestsService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.ArrayList;

public class ReimbursementRequestsController extends Controller {

    private ReimbursementRequestsService rrService = new ReimbursementRequestsService();

    Handler getUserReimbursementRequests = (ctx) -> {
        //get's the reimbursement requests for the logged in user.
        //this is useful for refreshing the list of all requests after a new one is created

        //first make sure that someone is actually logged in
        if (ctx.req.getSession(false) != null) {
            User currentUser = ctx.sessionAttribute("user");
            ArrayList<ReimbursementRequestsController> = rrService.getCurrentUserReimbursementRequestsService();
        }
        else {
            ctx.status(401);
        }
    };

    @Override
    public void addRoutes(Javalin app) {
        //POST localhost:8080/ReimbursementRequest
        app.get("/ReimbursementRequest", getUserReimbursementRequests)
    }
}
