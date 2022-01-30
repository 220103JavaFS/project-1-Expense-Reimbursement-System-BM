package com.revature.services;

import com.revature.models.users.User;
import com.revature.repos.UserDAOImpl;
import com.revature.repos.UsersDAO;
import com.revature.util.NewUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsersService {

    private UsersDAO usersDAO; //used for mocking of service layer tests
    private UserDAOImpl userDAO;
    private Logger log = LoggerFactory.getLogger(UsersService.class);

    //CONSTRUCTORS
    public UsersService() {
        this.userDAO = new UserDAOImpl(); //uncomment when done with UsersDaoImpl class
    }
    public UsersService(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    //GET METHODS
    public User getUser(String username) {
        //asks the DAO layer to get all basic info on the employee with the given username. Check to make sure the
        //username passed isn't null or empty before moving on to DAO layer

        try {
            if (username.equals("")) {
                System.out.println(username);
                return null;
            }
            return userDAO.getUser(username);
        } catch (Exception e) {
            log.debug("Username string was null");
            return null;
        }
    }

    //POST METHODS
    public boolean hireEmployee(User currentUser, NewUser newEmployee) {
        //allows a manager to potentially hire an employee.
        //Finance Managers can only hire Financial analysts while non-Financial Managers can hire Engineers and Interns
        //This function needs to have functionality to check that info for the new User is ok, such as making sure the username and email addresses are available
        //and that the password meets requirements

        //Check 1: Make sure that the currentUser is actually a manager
        int userRoleID = currentUser.getUserRoleID();

        if (userRoleID != 3 && userRoleID != 4) {
            log.info(currentUser.getUsername() + " doesn't have access to hire employees.");
            return false;
        }

        //Check 2. Make sure that the current manager can actually hire this newEmployee type
        int newEmployeeUserRoleID = newEmployee.userRoleID;
        if (userRoleID == 3) {
            //Finance Manager role
            if (newEmployeeUserRoleID != 5) {
                log.info("Sorry, you don't have access to hire that type of employee");
                return false;
            }
        }
        else {
            if (newEmployeeUserRoleID != 6 && newEmployeeUserRoleID != 7) {
                log.info("Sorry, you don't have access to hire that type of employee");
                return false;
            }
        }

        //3. Need to make sure that the username and email address for the newEmployee aren't already taken because the
        // database has a UNIQUE constraint on those columns
        if (userDAO.availableUsernameEmail(newEmployee.username, newEmployee.emailAddress)) {
            return userDAO.hireEmployee(newEmployee);
        }

        log.info("Either the username or email address already exists in the database, please enter new info.");
        return false;
    }

    //DELETE METHODS
    public boolean fireEmployee(User currentUser, String fireableEmployee) {

        //1. Check that the current user is actually a manager
        int userRoleID = currentUser.getUserRoleID();

        if (userRoleID != 3 && userRoleID != 4) {
            log.info(currentUser.getUsername() + " doesn't have access to hire employees.");
            return false;
        }

        //2. Make sure that the fireable Employee exists in the database
        User fEmployee = getUser(fireableEmployee);
        if (fEmployee == null) {
            log.info("An employee with that username doesn't exist, please re-type username.");
            return false;
        }

        //3. Make sure that the current manager can actually fire this fireableEmployee type
        int FireableEmployeeUserRoleID = fEmployee.getUserRoleID();
        if (userRoleID == 3) {
            //Finance Manager role
            if (FireableEmployeeUserRoleID != 5) {
                log.info("Sorry, you don't have access to fire that type of employee");
                return false;
            }
        }
        else {
            if (FireableEmployeeUserRoleID != 6 && FireableEmployeeUserRoleID != 7) {
                log.info("Sorry, you don't have access to fire that type of employee");
                return false;
            }
        }

        log.info("About to fire employee: " + fireableEmployee);
        return userDAO.fireEmployee(fEmployee);
    }
}
