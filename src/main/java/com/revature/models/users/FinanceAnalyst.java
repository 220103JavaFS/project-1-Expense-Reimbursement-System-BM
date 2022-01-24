package com.revature.models.users;

public class FinanceAnalyst extends NonReimbursementApprover{
    //FIELDS
    static private int userRoleIDAdder = 4;

    //CONSTRUCTORS
    public FinanceAnalyst() {
        super();
        userType = "FinanceAnalyst";
    }
    public FinanceAnalyst(int userID, String username, String password, String firstName, String lastName, String emailAddress) {
        super(userID, username, password, firstName, lastName, emailAddress);
        userType = "FinanceAnalyst";
    }

    @Override
    public int getUserRoleID() {
        //in the case that the FinanceManager user type is mistakenly deleted from the database, we can alter
        //the value of the userRoleIDAdder static variable
        return userRoleID + userRoleIDAdder;
    }
}
