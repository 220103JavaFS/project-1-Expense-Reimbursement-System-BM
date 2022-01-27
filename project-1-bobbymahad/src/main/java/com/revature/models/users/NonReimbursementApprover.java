package com.revature.models.users;

public abstract class NonReimbursementApprover extends Employee {
    //this class really just exists to separate the users that CAN'T approve reimbursement requests
    //from those who CAN. No special functionality for this abstract class

    //CONSTRUCTORS
    public NonReimbursementApprover() {super();}
    public NonReimbursementApprover(int userID, String username, String password, String firstName, String lastName, String emailAddress) {
        super(userID, username, password, firstName, lastName, emailAddress);
    }
}
