package com.revature.controllers;

import com.revature.models.reimbursement.ReimbursementRequest;
import com.revature.models.users.Employee;
import com.revature.models.users.FinanceManager;
import com.revature.models.users.Intern;
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
            User currentUser = ctx.sessionAttribute("currentUser");
//            ArrayList<ReimbursementRequest> currentRequests = rrService.getCurrentUserReimbursementRequestsService(currentUser);
//
//            if (currentRequests == null) {
//                //something went wrong in the DAO layer, don't update the current user's list
//                ctx.status(500);
//            }
//            else {
//                //we have the updated list of user requests. Use this list to update the information in the user cookie
//                rrService.updateCurrentUserReimbursementRequestsService(currentUser, currentRequests);
//
//                //we also need to
//                ctx.sessionAttribute("User", currentUser);
//
//                //after updating the cookie, return the array of requests back to the front end in JSON format
//                ctx.json(currentRequests);
//                ctx.status(200); //everything is ok
//            }

            //current reimbursement data should be stored already in curretUser session attribute
            //since all employees have reimbursement data, and all users can be cast to an employee, cast the user to access the data.
            //Maybe a design flaw but generic "users" don't actually have reimbursement data

            Employee emp = (Employee) currentUser;
            ctx.json(emp.getCurrentReimbursementRequests());
            ctx.status(200);
        }
        else {
            ctx.status(401);
        }
    };

    Handler getPendingReimbursementRequests = (ctx) -> {
        //Gets all the available reimbursement requests for the logged in Finance Manager. This information should already be stored in the
        //session attribute.

        //first make sure that someone is actually logged in
        if (ctx.req.getSession(false) != null) {
            User currentUser = ctx.sessionAttribute("currentUser");

            //only Finance Managers have the power to approve requests. Furthermore, only Finance Managers should have
            //the power to get to the HTML page that accesses this endpoint. Because of this we can safely cast
            //the current user to a finance manager type.

            FinanceManager fm = (FinanceManager) currentUser;
            ctx.json(fm.getAvailableReimbursementRequests()); //return the array of available requests
            ctx.status(200);
        }
        else {
            ctx.status(401);
        }
    };

    //Handler updateUserRequests
    //step 1: go down to Reimbursement DAO and update the request in the DB
    //step 2: update the current user data stored in ctx.getSession("currentUser") to reflect the changes made in step 1
    
    @Override
    public void addRoutes(Javalin app) {
        app.get("/ReimbursementRequest", getUserReimbursementRequests);
        app.get("/ReimbursementRequest/Approval", getPendingReimbursementRequests);
    }
}
