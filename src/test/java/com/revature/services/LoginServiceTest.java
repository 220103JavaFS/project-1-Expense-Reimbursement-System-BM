package com.revature.services;

import com.revature.models.users.Intern;
import com.revature.repos.UsersDAO;
import com.revature.util.LoginAttempt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginServiceTest {

    //FIELDS
    private LoginService testInstance;

    @Mock
    private UsersDAO mockedDAO;

    private LoginAttempt goodCredentials;
    private LoginAttempt badUsername;
    private LoginAttempt badPassword;
    private LoginAttempt badUsernameAndPassword;
    private Intern intern;

    @BeforeEach
    public void setUp() {

        //set login attempt information
        goodCredentials = new LoginAttempt("rfloyd01", "coding_is_kewl34");
        badUsername = new LoginAttempt("rfloyd02", "coding_is_kewl34");
        badPassword = new LoginAttempt("rfloyd01", "coding_is_kewl35");
        badUsernameAndPassword = new LoginAttempt("rfloyd02", "coding_is_kewl35");

        //the actual user
        intern = new Intern(4, "rfloyd01", "coding_is_kewl34", "Bobby", "Floyd", "robert.floyd@company.com");

        MockitoAnnotations.openMocks(this);
        testInstance = new LoginService(mockedDAO);

        Mockito.when(mockedDAO.getUser("rfloyd01")).thenReturn(intern);
    }

    @Test
    public void testLoginSuccess() {
        assertTrue(testInstance.loginUser(goodCredentials) == intern); //when the credentials match what's in the database, we should get a true value
    }

    @Test
    public void testLoginFailure() {
        //assertFalse(testInstance.loginUser(badUsername) == intern); //if username isn't in database, we can't login
        assertFalse(testInstance.loginUser(badPassword) == intern); //if password doesn't match what's in DB, can't login
        //assertFalse(testInstance.loginUser(badUsernameAndPassword) == intern); //should fail as both criteria or wrong
    }
}
