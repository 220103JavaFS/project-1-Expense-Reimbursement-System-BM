package com.revature.models.users;

import com.revature.models.reimbursement.ReimbursementRequest;

import java.util.ArrayList;

public class FinanceManager extends Employee implements Manager {

    //FIELDS
    static private int userRoleIDAdder = 2;
    private ArrayList<ReimbursementRequest> availableReimbursementRequests; //this is a list of all the reimbursement requests in the database waiting for approval

    //CONSTRUCTORS
    public FinanceManager() {
        super();
        userType = "FinanceManager";
    }
    public FinanceManager(int userID, String username, String password, String firstName, String lastName, String emailAddress) {
        super(userID, username, password, firstName, lastName, emailAddress);
        userType = "FinanceManager";
    }

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
        FinanceManager.userRoleIDAdder = userRoleIDAdder;
    }
    public ArrayList<ReimbursementRequest> getAvailableReimbursementRequests() {
        return availableReimbursementRequests;
    }
    public void setAvailableReimbursementRequests(ArrayList<ReimbursementRequest> availableReimbursementRequests) {
        this.availableReimbursementRequests = availableReimbursementRequests;
    }

    @Override
    public String toString() {
        //We override the standard Employee toString method to include the availableReimbursementRequests field
        return userType + "{" +
                "userRoleID='" + getUserRoleID() + '\'' +
                ", userID='" + userID + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", existingReimbursementRequests='" + existingReimbursementRequests + '\'' +
                ", availableReimbursementRequests='" + availableReimbursementRequests + '\'' +
                '}';
    }

    //METHODS
    /*
    note* - the finance manager class won't actually implement methods, these are just notes about which methods in the finance manager
    class will get executed by which Handlers in the controller layer of the back-end

    These methods are from the manager Interface
    void hireEmployee(Employee newEmployee) --> POST localhost:8080/Employees
    - Allows the financial manager to hire a new employee, although, only into a financial role

    void fireEmployee(Employee doomedEmployee) --> DELETE localhost:8080/Employees/{employee_number}
    - Allows the finance manager to fire any financial employees who aren't also managers

    These methods are distinct to the finance manager class
    void getPendingReimbursementRequests() --> GET localhost:8080/ReimbursementRequests/Pending
    - Gets all the current pending reimbursement requests in the database

    void decidePendingReimbursementRequest(RequestDecision rd) --> POST localhost:8080/ReimbursementRequests/Pending/{request_number}
    - The finance manager can either decide to approve or deny reimbursement requests with the 'submitted' status
    - The finance manager CAN'T approve their own reimbursement requests

     */
}
