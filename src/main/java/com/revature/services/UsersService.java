package com.revature.services;

import com.revature.models.users.User;

public class UsersService {
    //GET METHODS
    public User getUser(String username) {
        //asks the DAO layer to get all basic info on the employee with the given username. Check to make sure the
        //username passed isn't null or empty before moving on to DAO layer
        return null;
    }

    //POST METHODS
    public boolean hireEmployee(User currentUser, User newEmployee) {
        //allows a manager to potentially hire an employee.
        //Finance Managers can only hire Financial analysts while non-Financial Managers can hire Engineers and Interns
        //This function needs to have functionality to check that info for the new User is ok, such as making sure the username and email addresses are available
        //and that the password meets requirements
        return true;
    }

    //DELETE METHODS
    public boolean fireEmployee(User currentUser, String usernameExEmployee) {
        //allows a manager to potentially fire an employee.
        //Finance Managers can only fire Financial analysts while non-Financial Managers can fire Engineers and Interns
        //Need to make checks that a) the employee with the given username actually exists and b) the manager has authority to fire them.
        //This can be done with a call to the DAO layer function getUser(usernameExEmployee). If the employee exists in the database we will
        //be able to check their userRoleId and see if our current manager has the power to fire them. If so, make another call to the DAO
        //layer which will remove the employee from the database.
        return true;
    }
}
