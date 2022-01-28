package com.revature.services;

import com.revature.models.reimbursement.ReimbursementRequest;
import com.revature.models.users.User;
import com.revature.repos.ReimbursementRequestsDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ReimbursementRequestsService {

    private ReimbursementRequestsDAO reimbursementRequestsDAO; //used for Mockito purposes
    private Logger log = LoggerFactory.getLogger(ReimbursementRequestsService.class);

    //CONSTRUCTORS
    public ReimbursementRequestsService() {}
    public ReimbursementRequestsService(ReimbursementRequestsDAO reimbursementRequestsDAO) {
        this.reimbursementRequestsDAO = reimbursementRequestsDAO;
    }

    //GET METHODS
    public ArrayList<ReimbursementRequest> getCurrentUserReimbursementRequestsService(User user) {
        return reimbursementRequestsDAO.getUserCurrentReimbursementRequestsDAO(user); }
    public ArrayList<ReimbursementRequest> getAllPendingReimbursementRequestsService() {

        return reimbursementRequestsDAO.getAllPendingReimbursementRequestsDAO();
    }

    //POST METHODS
    public int createReimbursementRequestService(ReimbursementRequest RR) {
        //instead of passing back a boolean of true or false, we pass back an integer which will
        //represent an error code. A returned value of 0 means everything was ok

        //first, we need to check and make sure the requested amount isn't a negative value.
        if (RR.getReimbursementAmount() < 0) return -1;

        //next, we need to see if the amount is more than or equal to $500. if so then it needs to have a receipt attached to it
        if (RR.getReimbursementAmount() >= 500 && RR.getReimbursementReceipt() == null) return -2;

        //Our final check is to make sure that the description is 250 characters or less because the DB can't handle more than that
        if (RR.getReimbursementDescription().length() > 250) return -3;

        //All checks have passed. If the request was submitted with the "submit" status then we need to add a time stamp
        if (RR.getReimbursementStatus().getStatus().equals("Submit")) {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            RR.setReimbursementSubmitted(ts);
        }

        //Everything checks out now so ask the DAO to put the new request into the database. If there's some kind of
        //failure in the DAO layer then the error code will be passed up to the controller layer
        return reimbursementRequestsDAO.createReimbursementRequestDAO(RR);
    }

    //PATCH METHODS
    public int editReimbursementRequestService(ReimbursementRequest RR) {
        //this function allows all users to edit ReimbursementRequests that they have with a status of
        //either "created" or "denied". Furthermore, this function will also allow user's with the proper access
        //to approve or deny a request by changing the status. We go through the same checks as we did for
        //creating a reimbursement request, however, we also need to make sure that the status of the current
        //request is either "created" or "denied"

        //check to see if the status of the request is valid for editing
        if (!RR.getReimbursementStatus().getStatus().equals("Created") && !RR.getReimbursementStatus().getStatus().equals("Denied")) return -4;

        //first, we need to check and make sure the requested amount isn't a negative value.
        if (RR.getReimbursementAmount() < 0) return -1;

        //next, we need to see if the amount is more than or equal to $500. if so then it needs to have a receipt attached to it
        if (RR.getReimbursementAmount() >= 500 && RR.getReimbursementReceipt() == null) return -2;

        //Our final check is to make sure that the description is 250 characters or less because the DB can't handle more than that
        if (RR.getReimbursementDescription().length() > 250) return -3;

        //All checks have passed. If the request was submitted with the "submit" status then we need to add a time stamp
        if (RR.getReimbursementStatus().getStatus().equals("Submit")) {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            RR.setReimbursementSubmitted(ts);
        }

        //Everything checks out now so ask the DAO to put the new request into the database. If there's some kind of
        //failure in the DAO layer then the error code will be passed up to the controller layer
        return reimbursementRequestsDAO.editReimbursementRequestDAO(RR);
    }

    //DELETE METHODS
    public int deleteReimbursementRequestService(ReimbursementRequest RR) {
        //only requests that haven't been submitted yet are allowed to be deleted from the database
        if (!RR.getReimbursementStatus().getStatus().equals("Created")) return -1;

        return reimbursementRequestsDAO.deleteReimbursementRequestDAO(RR);
    }
}
