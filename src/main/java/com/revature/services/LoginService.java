package com.revature.services;

import com.revature.models.users.User;
import com.revature.repos.UsersDAO;
import com.revature.util.LoginAttempt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginService {

    private UsersDAO usersDAO;
    private Logger log = LoggerFactory.getLogger(LoginService.class);

    public LoginService() {}
    public LoginService(UsersDAO usersDAO) {
        //allows for mocking of UsersSDAO class with Mockcito
        this.usersDAO = usersDAO;
    }

    public User loginUser(LoginAttempt loginAttempt) {
        //this will return true if there's a username and password in the database that matches the login attempt

        User user = usersDAO.getUser(loginAttempt.getUsername());

        if (user == null) {
            log.info("Sorry, that username doesn't exist. Please re-type it and try to login again."); //TODO: THis should display somehow on the front end instead of server info log
            return null;
        }

        //We found the user, now we need to compare passwords. The user brought up from the DAO is going to have an
        //encrypted password so we need to decrypt it before comparing them.
        String decryptedUserPassword = User.decryptPassword(user.getPassword());

        if (loginAttempt.getPassword().equals(decryptedUserPassword)) {
            log.debug("The password entered was valid, preparing to log you in.");
            return user;
        }
        else {
            log.debug("The password entered was invalid, please try again.");
            return null;
        }
    }
}
