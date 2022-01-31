package com.revature.services;

import com.revature.models.reimbursement.ReimbursementRequest;
import com.revature.models.users.*;
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
        if (RR.getReimbursementAmount() <= 0) return -1;

        //next, we need to see if the amount is more than or equal to $500. if so then it needs to have a receipt attached to it
        if (RR.getReimbursementAmount() >= 500 && RR.getReimbursementReceipt() == null) return -2;

        //Our final check is to make sure that the description is 250 characters or less because the DB can't handle more than that
        if (RR.getReimbursementDescription().length() > 250) return -3;

        //All checks have passed. If the request was submitted with the "submit" status then we need to add a time stamp
        if (RR.getReimbursementStatusId() == 2) {
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
        if (RR.getReimbursementStatusId() != 1 && RR.getReimbursementStatusId() != 5) return -4;

        //first, we need to check and make sure the requested amount isn't a negative value.
        if (RR.getReimbursementAmount() < 0) return -1;

        //next, we need to see if the amount is more than or equal to $500. if so then it needs to have a receipt attached to it
        if (RR.getReimbursementAmount() >= 500 && RR.getReimbursementReceipt() == null) return -2;

        //Our final check is to make sure that the description is 250 characters or less because the DB can't handle more than that
        if (RR.getReimbursementDescription().length() > 250) return -3;

        //All checks have passed. If the request was submitted with the "submit" status then we need to add a time stamp
        if (RR.getReimbursementStatusId() == 2) {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            RR.setReimbursementSubmitted(ts);
        }

        //Everything checks out now so ask the DAO to put the new request into the database. If there's some kind of
        //failure in the DAO layer then the error code will be passed up to the controller layer
        return reimbursementRequestsDAO.editReimbursementRequestDAO(RR);
    }

    //POST METHODS
    public void updateCurrentUserReimbursementRequestsService(User currentUser, ArrayList<ReimbursementRequest> newRequests) {
        //this function is used to allow the current user to refresh their list of reimbursement requests.
        //Since the user class doesn't directly have access to the reimbursement requests (because the Employee
        //class where the requests are created extends the user class).

        //create an updated version of the current user that has a refreshed list of reimbursement requests.
        //This is necessary because the user class doesn't have direct access to the list of reimbursement requests
        int userRoleId = currentUser.getUserRoleID();

        if (userRoleId == 1) {
            //as stated above, the generic user type doesn't have access to reimbursements directly
            //if our currentUser is only a generic user then we can't update request list as they don't
            //have one. Return without doing anything.
            return;
        }
        else if (userRoleId == 2) {
            //same deal as the generic user. ex-employee's don't have request lists either so return without
            //doing anything
            return;
        }
        else if (userRoleId == 3) {
            FinanceManager fm = (FinanceManager) currentUser; //the downcast is safe because userRoleId confirms the employee type
            fm.setCurrentReimbursementRequests(newRequests);

            currentUser = fm; //update the currentUser with the new information
        }
        else if (userRoleId == 4) {
            NonFinanceManager nfm = (NonFinanceManager) currentUser; //the downcast is safe because userRoleId confirms the employee type
            nfm.setCurrentReimbursementRequests(newRequests);

            currentUser = nfm; //update the currentUser with the new information
        }
        else if (userRoleId == 5) {
            FinanceAnalyst fa = (FinanceAnalyst) currentUser; //the downcast is safe because userRoleId confirms the employee type
            fa.setCurrentReimbursementRequests(newRequests);

            currentUser = fa; //update the currentUser with the new information
        }
        else if (userRoleId == 6) {
            Engineer eng = (Engineer) currentUser; //the downcast is safe because userRoleId confirms the employee type
            eng.setCurrentReimbursementRequests(newRequests);

            currentUser = eng; //update the currentUser with the new information
        }
        else {
            Intern in = (Intern) currentUser; //the downcast is safe because userRoleId confirms the employee type
            in.setCurrentReimbursementRequests(newRequests);

            currentUser = in; //update the currentUser with the new information
        }
    }
    public void updatePendingPendingReimbursementRequestsService(User currentUser, ArrayList<ReimbursementRequest> newRequests) {
        //Very similar to the updateCurrentReimbursementRequestsService() method, however, only Finance Managers can
        //use this method. It updates their list of pending requests and should be used after they approve or deny a
        //request. This way the request will no longer show up in their log.
        if (currentUser.getUserRoleID() == 3) {
            FinanceManager fm = (FinanceManager) currentUser; //the downcast is safe because userRoleId confirms the employee type
            fm.setAvailableReimbursementRequests(newRequests);

            currentUser = fm; //update the currentUser with the new information
        }
    }

    //DELETE METHODS
    public int deleteReimbursementRequestService(ReimbursementRequest RR) {
        //only requests that haven't been submitted yet are allowed to be deleted from the database
        if (RR.getReimbursementStatusId() != 1) return -1;

        return reimbursementRequestsDAO.deleteReimbursementRequestDAO(RR);
    }
}
