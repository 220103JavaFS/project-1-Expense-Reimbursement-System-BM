package com.revature.models.users;

import com.revature.models.reimbursement.ReimbursementRequest;

import java.util.ArrayList;

public abstract class Employee extends User {
    //FIELDS
    protected ArrayList<ReimbursementRequest> currentReimbursementRequests;
    protected ArrayList<ReimbursementRequest> legacyReimbursementRequests;

    //CONSTRUCTORS
    public Employee() {super();}
    public Employee(int userID, String username, String password, String firstName, String lastName, String emailAddress) {
        super(userID, username, password, firstName, lastName, emailAddress);
    }

    //GETTERS AND SETTERS
    public ArrayList<ReimbursementRequest> getCurrentReimbursementRequests() {
        return currentReimbursementRequests;
    }
    public void setCurrentReimbursementRequests(ArrayList<ReimbursementRequest> currentReimbursementRequests) {
        this.currentReimbursementRequests = currentReimbursementRequests;
    }
    public ArrayList<ReimbursementRequest> getLegacyReimbursementRequests() {
        return legacyReimbursementRequests;
    }
    public void setLegacyReimbursementRequests(ArrayList<ReimbursementRequest> legacyReimbursementRequests) {
        this.legacyReimbursementRequests = legacyReimbursementRequests;
    }

    //METHODS
    /*
    note* - none of the employee classes will actually implement methods, these are just notes about which methods in the employee
    classes will get handled by which Handlers in the controller layer of the back-end

    public void viewLegacyReimbursementRequests() --> GET localhost:8080/ReimbursementRequests
    - This will let the user view all of their legacy reimbursement requests that have already been approved by a finance manager.

    public void createReimbursementRequest(ReimbursementRequest rr) --> POST localhost:8080/ReimbursementRequest/Creation
    - This method will create a brand new reimbursement request
    - The request can be submitted for approval immediately, or saved so that it can be updated and submitted at a later time

    public void viewCurrentReimbursementRequests() --> GET localhost:8080/ReimbursementRequests/Creation
    - This method will show the logged in user all the reimbursement requests they have which are currently in the 'created', 'submitted' or 'rejected' phase

    public void editReimbursementRequests(ReimbursementRequest rr) --> PUT localhost:8080/ReimbursementRequests/Creation/{request_number}
    - This method will allow the logged in user to update any reimbursement requests with a 'created' or 'rejected' status found with the viewCreatedReimbursementRequests() function
    - After updating, the user can choose to save the request again or to submit it
    - The format for updating a request is the same as for creating a new one

    public void deleteReimbursementRequest(int requestID) --> DELETE localhost:8080/ReimbursementRequests/Creation/{request_number}
    - If the reimbursement request is still in 'created' or 'rejected' status, the user can choose to delete it from the database entirely
    - This function won't work on requests that have the 'submitted' status

     */

}
