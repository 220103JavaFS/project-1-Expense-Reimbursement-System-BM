package com.revature.repos;

import com.revature.models.users.*;
import com.revature.repos.UserDAOImpl;
import com.revature.repos.UsersDAO;
import com.revature.services.UsersService;
import com.revature.util.NewUser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDAOImplTest {

    //FIELDS
    private UserDAOImpl testInstance;


    private String username;
    private String noneDBUser;
    private String email;
    private FinanceManager manager;
    private NonFinanceManager nfManager;
    private FinanceAnalyst analyst;
    private FinanceAnalyst nonDBAnalyst;
    private FinanceAnalyst repeatUsername;
    private FinanceAnalyst repeatEmail;
    private Intern intern;
    private Intern nonDBIntern;
    private String  availableUsername;
    private String availableEmail;

    private NewUser newNonDBAnalyst;
    private NewUser newAnalyst;

    //BEFORE AND AFTER ANNOTATIONS
    @BeforeEach
    public void setUp() {
        //use BeforeEach because Mockito doesn't like BeforeAll

        //create users for tests
        username = "rfloyd01";
        noneDBUser = "rfloyd02";
        email = "robert.floyd@company.com";
        availableUsername = "mahad12";
        availableEmail = "mahad12@gmail.com";



        //The below users represent people already in the database
        manager = new FinanceManager(1, "moneyManage2", "helloW0rld", "Dan", "Preuss", "daniel.preuss@company.com");
        nfManager = new NonFinanceManager(2, "Manage1", "helloW0rld", "Matt", "Damon", "matthew.damon@company.com");
        analyst = new FinanceAnalyst(3, "Capn", "helloW0rld", "Steve", "Rogers", "captain.america@company.com");
        intern = new Intern(4, "rfloyd01", "helloW0rld", "Bobby", "Floyd", "robert.floyd@company.com");

        //The below users represent people NOT in the database
        nonDBAnalyst = new FinanceAnalyst(5, "Ir0nMan", "helloW0rld", "Tony", "Stark", "tony.stark@company.com");
        repeatUsername = new FinanceAnalyst(6, "rfloyd01", "helloW0rld", "Bobby", "Floyd", "robert.floyd2@company.com"); //same username
        repeatEmail = new FinanceAnalyst(7, "rfloyd02", "helloW0rld", "Bobby", "Floyd", "robert.floyd@company.com"); //same email as someone already in the db
        nonDBIntern = new Intern(8, "rfloyd02", "helloW0rld", "Robert", "Floyd", "robert.floyd2@company.com");

        //create NewUser types for the hire function
        newNonDBAnalyst = new NewUser(5, "Ir0nMan", "helloW0rld", "Tony", "Stark", "tony.stark@company.com");
        newAnalyst = new NewUser(5, "Capn", "helloW0rld", "Steve", "Rogers", "captain.america@company.com");
    }

    @Test
    public void testGetUserSucccess() {
        assertTrue(testInstance.getUser(username) == intern);
        assertTrue(testInstance.getUser(noneDBUser) == null);
        assertTrue(testInstance.getUser(null) == null); //actual service layer logic
    }

    @Test
    public void testGetUserFailure() {
        assertFalse(testInstance.getUser(username) == manager);
        assertFalse(testInstance.getUser(noneDBUser) == intern);
        assertFalse(testInstance.getUser("") == intern); //this test is to make sure that our service layer fails upon receiving an empty string
    }

    @Test
    public void testHireEmployeeSuccess() {
        assertTrue(testInstance.hireEmployee(newNonDBAnalyst) == 0); //Test that a finance manager can hire a financial employee not already in the database
    }

    @Test
    public void testHireEmployeeFailure() {
        assertFalse(testInstance.hireEmployee(newAnalyst) == 0); //this test is to make sure a non-financial manager can't hire a financial employee (but the employee input is valid)
    }

    @Test
    public void testFireEmployeeSuccess() {
        assertTrue(testInstance.fireEmployee(nonDBAnalyst) == 0); //This test is to make sure that a finance manager can fire an existing financial employee in the database
    }

    @Test
    public void testFireEmployeeFailure() {
        assertFalse(testInstance.fireEmployee(analyst) == 0);
    }

    @Test
    public void testAvailableUsernameEmailSuccess() {
        assertTrue(testInstance.availableUsernameEmail(availableUsername, availableEmail) == 0); //This test is to make sure that a finance manager can fire an existing financial employee in the database
    }

    @Test
    public void testAvailableUsernameEmailFailure() {
        assertFalse(testInstance.availableUsernameEmail(username, email) == 0);
        assertFalse(testInstance.availableUsernameEmail(availableUsername, email) == 0);
        assertFalse(testInstance.availableUsernameEmail(username, availableEmail) == 0);
    }
}
