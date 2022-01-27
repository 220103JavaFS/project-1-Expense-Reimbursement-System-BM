package com.revature.models.users;

public class ExEmployee extends User{
    //FIELDS
    static private int userRoleIDAdder = 1;

    //GETTER AND SETTER
    @Override
    public int getUserRoleID() {
        //in the case that the FinanceManager user type is mistakenly deleted from the database, we can alter
        //the value of the userRoleIDAdder static variable
        return userRoleID + userRoleIDAdder;
    }
    public static int getUserRoleIDAdder() {
        //the userRoleID field can't be changed within the
        return userRoleIDAdder;
    }
    public static void setUserRoleIDAdder(int userRoleIDAdder) {
        ExEmployee.userRoleIDAdder = userRoleIDAdder;
    }
}
