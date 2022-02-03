package com.revature.models.users;

public class NonFinanceManager extends NonReimbursementApprover implements Manager {
    //FIELDS
    static private int userRoleIDAdder = 3; //same value for all Managers

    //CONSTRUCTORS
    public NonFinanceManager() {
        super();
        userType = "NonFinanceManager";
    }
    public NonFinanceManager(int userID, String username, String password, String firstName, String lastName, String emailAddress) {
        super(userID, username, password, firstName, lastName, emailAddress);
        userType = "NonFinanceManager";
    }

    //GETTERS AND SETTERS
    @Override
    public int getUserRoleID() {
        //in the case that the FinanceManager user type is mistakenly deleted from the database, we can alter
        //the value of the userRoleIDAdder static variable
        return userRoleID + userRoleIDAdder;
    }

    //METHODS
    /*
    note* - the manager class won't actually implement methods, these are just notes about which methods in the manager
    class will get executed by which Handlers in the controller layer of the back-end

    These methods are from the manager Interface
    void hireEmployee(Employee newEmployee) --> POST localhost:8080/Employees
    - Allows the manager to hire a new employee, although, only to a non-financial role

    void fireEmployee(Employee doomedEmployee) --> DELETE localhost:8080/Employees/{employee_number}
    - Allows the manager to fire any non-financial employee. This effectively removes all record of them from the database
    - The manager can't fire anyone with the Manager, Financial Analyst or Finance Manager role
     */
}
