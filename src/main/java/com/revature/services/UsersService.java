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
    public int hireEmployee(User currentUser, NewUser newEmployee) {
        //allows a manager to potentially hire an employee.
        //Finance Managers can only hire Financial analysts while non-Financial Managers can hire Engineers and Interns
        //This function needs to have functionality to check that info for the new User is ok, such as making sure the username and email addresses are available
        //and that the password meets requirements

        //this function returns a binary number representing an error code. Each digit of the code is as follows:
        //0b0 = no errors, 0b1 = don't have hiring power, 0b10 = can't hire that type of employee, 0b100 = username isn't available
        //0b1000 = email isn't available, any combination of 0b1111110000 = password issue, 0b10000000000 = issue accessing database

        //Check 1: Make sure that the currentUser is actually a manager
        int userRoleID = currentUser.getUserRoleID();

        if (userRoleID != 3 && userRoleID != 4) {
            log.info(currentUser.getUsername() + " doesn't have access to hire employees.");
            return 0b1;
        }

        //Check 2. Make sure that the current manager can actually hire this newEmployee type
        int newEmployeeUserRoleID = newEmployee.userRoleID;
        if (userRoleID == 3) {
            //Finance Manager role
            if (newEmployeeUserRoleID != 5) {
                log.info("Sorry, you don't have access to hire that type of employee");
                return 0b10;
            }
        }
        else {
            if (newEmployeeUserRoleID != 6 && newEmployeeUserRoleID != 7) {
                log.info("Sorry, you don't have access to hire that type of employee");
                return 0b10;
            }
        }

        //3. Need to make sure that the username and email address for the newEmployee aren't already taken because the
        // database has a UNIQUE constraint on those columns
        Integer errorCode = userDAO.availableUsernameEmail(newEmployee.username, newEmployee.emailAddress);
        errorCode = passwordCheck(errorCode, newEmployee.password); //this function checks to make sure the password passes all tests

        if (errorCode == 0) {
            //All checks have been passed, we're free to hire the employee
            return userDAO.hireEmployee(newEmployee);
        }
        else {
            //Something was entered incorrectly and will be reflected in the error code
            return errorCode;
        }
    }

    public int passwordCheck(int errorCode, String password) {
        //There are 6 Requirements for passwords. Missing any will result in an error code:
        //1. Must be at least 8 characters in length (error code 10000)
        //2. Must contain at least 1 uppercase letter (error code 100000)
        //3. Must contain at least 1 lowercase letter (error code 1000000)
        //4. Must contain at least 1 number (error code 10000000)
        //5. Must contain at least 1 special character  (error code 100000000)
        //6. Special character must have an integer value of 32 or higher (error code 1000000000)
        //errors 2 - 5 will be assumed to be present in the password until proven otherwise,
        //i.e. the password will be considered "guilty until proven innocent"

        if (password.length() < 8) {
            errorCode |= 0b10000;
            return errorCode; //don't bother looking for other errors if password is too short
        }

        errorCode |=  0b111100000; //add on the "guilty until proven innocent bits"

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            //System.out.println("errorCode is currently: " + errorCode);

            //use the binary and operator to remove errors from error code if necessary
            if (c < ' ') errorCode |= 0b1000000000; //can't have characters with integer value less than 32
            else if (c >= 'A' && c <= 'Z') errorCode &= 0b1111011111; //set guilty uppercase bit to 0
            else if (c >= 'a' && c <= 'z') errorCode &= 0b1110111111; //set guilty lowercase bit to 0
            else if (c >= '0' && c <= '9') errorCode &= 0b1101111111; //set guilty number bit to 0
            else errorCode &= 0b1011111111; //set guilty special character bit to 0
        }

        return errorCode;
    }

    //DELETE METHODS
    public int fireEmployee(User currentUser, String fireableEmployee) {

        //1. Check that the current user is actually a manager
        int userRoleID = currentUser.getUserRoleID();

        if (userRoleID != 3 && userRoleID != 4) {
            log.info(currentUser.getUsername() + " doesn't have access to fire employees.");
            return 1;
        }

        //2. Make sure that the fireable Employee exists in the database
        User fEmployee = getUser(fireableEmployee);
        if (fEmployee == null) {
            log.info("An employee with that username doesn't exist, please re-type username.");
            return 2;
        }

        //3. Make sure that the current manager can actually fire this fireableEmployee type
        int FireableEmployeeUserRoleID = fEmployee.getUserRoleID();
        if (userRoleID == 3) {
            //Finance Manager role
            if (FireableEmployeeUserRoleID != 5) {
                log.info("Sorry, you don't have access to fire that type of employee");
                return 1;
            }
        }
        else {
            if (FireableEmployeeUserRoleID != 6 && FireableEmployeeUserRoleID != 7) {
                log.info("Sorry, you don't have access to fire that type of employee");
                return 1;
            }
        }

        log.info("About to fire employee: " + fireableEmployee);
        return userDAO.fireEmployee(fEmployee);
    }
}
