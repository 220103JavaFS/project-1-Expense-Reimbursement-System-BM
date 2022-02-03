package com.revature.controllers;

import com.revature.models.reimbursement.ReimbursementRequest;
import com.revature.models.users.Employee;
import com.revature.models.users.FinanceManager;
import com.revature.models.users.Intern;
import com.revature.models.users.User;
import com.revature.repos.UserDAOImpl;
import com.revature.services.ReimbursementRequestsService;
import com.revature.services.UsersService;
import com.revature.util.AppErrorCode;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.ArrayList;

public class ReimbursementRequestsController extends Controller {

    private ReimbursementRequestsService rrService = new ReimbursementRequestsService();
    private UserDAOImpl userDAO = new UserDAOImpl();

    Handler getUserReimbursementRequests = (ctx) -> {
        //Gets all the reimbursement requests for the logged in user. This information should already be stored in the
        //session cookie, however, if the user makes changes to their own requests list (i.e. adds or deletes requests)
        //then we need some way to update the information in the cookie.

        //first make sure that someone is actually logged in
        if (ctx.req.getSession(false) != null) {
            User currentUser = ctx.sessionAttribute("currentUser");

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

    Handler updateUserRequests = (ctx) -> {
        //first make sure that someone is actually logged in
        if (ctx.req.getSession(false) != null) {
            User currentUser = ctx.sessionAttribute("currentUser");
            ReimbursementRequest reimbursementRequest = ctx.bodyAsClass(ReimbursementRequest.class);

            int errorCode = rrService.editReimbursementRequestService(reimbursementRequest, currentUser);
            if (errorCode == 0) {
                //we need to update the current users requests (and available requests if it's a finance manager)
                //to do this it's easiest to just create the current user again from scratch (which won't require the updating
                //of current browser cookies
                User updatedUser = userDAO.getUser(currentUser.getUsername()); //this function will get all updated data
                ctx.sessionAttribute("currentUser", updatedUser); //update user which will include request data

                ctx.status(200);
            }
            else {
                AppErrorCode creationError = new AppErrorCode(errorCode);
                ctx.json(creationError);
                ctx.status(400);
            }

        }
        else {
            ctx.status(401);
        }
    };

    Handler createUserReimbursementRequests = (ctx) -> {
        //first make sure that someone is actually logged in
        if (ctx.req.getSession(false) != null) {
            User currentUser = ctx.sessionAttribute("currentUser");
            ReimbursementRequest reimbursementRequest = ctx.bodyAsClass(ReimbursementRequest.class);

            //we need to fill in the request author information
            reimbursementRequest.setReimbursementAuthor(currentUser.getUserID());

            int errorCode = rrService.createReimbursementRequestService(reimbursementRequest);

            if (errorCode == 0) {
                //we need to update the current users requests (and available requests if it's a finance manager)
                //to do this it's easiest to just create the current user again from scratch (which won't require the updating
                //of current browser cookies
                User updatedUser = userDAO.getUser(currentUser.getUsername()); //this function will get all updated data
                ctx.sessionAttribute("currentUser", updatedUser); //update user which will include request data

                ctx.status(200);
            }
            else {
                AppErrorCode creationError = new AppErrorCode(errorCode);
                ctx.json(creationError);
                ctx.status(400);
            }

        }
        else {
            ctx.status(401);
        }
    };

    Handler deleteReimbursementRequest = (ctx) -> {
        //first make sure that someone is actually logged in
        if (ctx.req.getSession(false) != null) {
            User currentUser = ctx.sessionAttribute("currentUser");
            ReimbursementRequest reimbursementRequest = ctx.bodyAsClass(ReimbursementRequest.class);

            //we need to fill in the request author information
            reimbursementRequest.setReimbursementAuthor(currentUser.getUserID());

            int errorCode = rrService.deleteReimbursementRequestService(reimbursementRequest);

            if (errorCode == 0) {
                //we need to update the current users requests (and available requests if it's a finance manager)
                //to do this it's easiest to just create the current user again from scratch (which won't require the updating
                //of current browser cookies
                User updatedUser = userDAO.getUser(currentUser.getUsername()); //this function will get all updated data
                ctx.sessionAttribute("currentUser", updatedUser); //update user which will include request data

                ctx.status(200);
            }
            else {
                AppErrorCode creationError = new AppErrorCode(errorCode);
                ctx.json(creationError);
                ctx.status(400);
            }
        }
        else {
            ctx.status(401);
        }
    };
    
    @Override
    public void addRoutes(Javalin app) {
        app.get("/ReimbursementRequest", getUserReimbursementRequests);
        app.get("/ReimbursementRequest/Approval", getPendingReimbursementRequests);

        app.post("/ReimbursementRequest", createUserReimbursementRequests);

        app.patch("/ReimbursementRequest/Edit", updateUserRequests);

        app.delete("/ReimbursementRequest", deleteReimbursementRequest);
    }
}
