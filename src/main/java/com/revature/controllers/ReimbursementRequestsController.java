package com.revature.controllers;

import com.revature.models.reimbursement.ReimbursementRequest;
import com.revature.models.users.User;
import com.revature.services.ReimbursementRequestsService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.ArrayList;

public class ReimbursementRequestsController extends Controller {

    private ReimbursementRequestsService rrService = new ReimbursementRequestsService();

    Handler getUserReimbursementRequests = (ctx) -> {
        //Gets all the reimbursement requests for the logged in user. This information should already be stored in the
        //session cookie, however, if the user makes changes to their own requests list (i.e. adds or deletes requests)
        //then we need some way to update the information in the cookie.

        //first make sure that someone is actually logged in
        if (ctx.req.getSession(false) != null) {
            User currentUser = ctx.sessionAttribute("User");
            ArrayList<ReimbursementRequest> currentRequests = rrService.getCurrentUserReimbursementRequestsService(currentUser);

            if (currentRequests == null) {
                //something went wrong in the DAO layer, don't update the current user's list
                ctx.status(500);
            }
            else {
                //we have the updated list of user requests. Use this list to update the information in the user cookie
                rrService.updateCurrentUserReimbursementRequestsService(currentUser, currentRequests);

                //we also need to
                ctx.sessionAttribute("User", currentUser);

                //after updating the cookie, return the array of requests back to the front end in JSON format
                ctx.json(currentRequests);
                ctx.status(200); //everything is ok
            }
        }
        else {
            ctx.status(401);
        }
    };

    Handler getPendingReimbursementRequests = (ctx) -> {

    };
    
    @Override
    public void addRoutes(Javalin app) {
        app.get("/ReimbursementRequest", getUserReimbursementRequests);
        app.get("/ReimbursementRequest/Approval", getPendingReimbursementRequests);
    }
}
