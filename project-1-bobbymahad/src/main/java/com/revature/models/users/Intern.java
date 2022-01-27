package com.revature.models.users;

public class Intern extends NonReimbursementApprover{
    //FIELDS
    static private int userRoleIDAdder = 6;

    //CONSTRUCTORS
    public Intern() {
        super();
        userType = "Intern";
    }
    public Intern(int userID, String username, String password, String firstName, String lastName, String emailAddress) {
        super(userID, username, password, firstName, lastName, emailAddress);
        userType = "Intern";
    }

    @Override
    public int getUserRoleID() {
        //in the case that the FinanceManager user type is mistakenly deleted from the database, we can alter
        //the value of the userRoleIDAdder static variable
        return userRoleID + userRoleIDAdder;
    }
}
