package com.revature.services;

import com.revature.models.users.*;
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

public class UsersServiceTest {

    //FIELDS
    private UsersService testInstance;

    @Mock
    private UsersDAO mockedDAO;

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

    private NewUser newNonDBIntern;
    private NewUser newNonDBAnalyst;
    private NewUser newIntern;
    private NewUser newAnalyst;
    private NewUser newNFManager;
    private NewUser newRepeatUsername;
    private NewUser newRepeatEmail;

    //BEFORE AND AFTER ANNOTATIONS
    @BeforeEach
    public void setUp() {
        //use BeforeEach because Mockito doesn't like BeforeAll

        //create users for tests
        username = "rfloyd01";
        noneDBUser = "rfloyd02";
        email = "robert.floyd@company.com";

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

        //create Some NewUser types to test our hireEmployee functionality
        newNonDBIntern = new NewUser(7, "rfloyd02", "helloW0rld", "Robert", "Floyd", "robert.floyd2@company.com");
        newNonDBAnalyst = new NewUser(5, "Ir0nMan", "helloW0rld", "Tony", "Stark", "tony.stark@company.com");
        newIntern = new NewUser(7, "rfloyd01", "helloW0rld", "Bobby", "Floyd", "robert.floyd@company.com");
        newAnalyst = new NewUser(5, "Capn", "helloW0rld", "Steve", "Rogers", "captain.america@company.com");
        newNFManager = new NewUser(4, "Manage1", "helloW0rld", "Matt", "Damon", "matthew.damon@company.com");
        newRepeatUsername = new NewUser(5, "rfloyd01", "helloW0rld", "Bobby", "Floyd", "robert.floyd2@company.com"); //same username
        newRepeatEmail = new NewUser(5, "rfloyd02", "helloW0rld", "Bobby", "Floyd", "robert.floyd@company.com"); //same username

        MockitoAnnotations.openMocks(this);
        testInstance = new UsersService(mockedDAO);

        Mockito.when(mockedDAO.hireEmployee(newIntern)).thenReturn(false);
        Mockito.when(mockedDAO.hireEmployee(newAnalyst)).thenReturn(false);
        Mockito.when(mockedDAO.hireEmployee(newNonDBAnalyst)).thenReturn(true);
        Mockito.when(mockedDAO.hireEmployee(newNonDBIntern)).thenReturn(true);
        Mockito.when(mockedDAO.hireEmployee(newNFManager)).thenReturn(false);

        Mockito.when(mockedDAO.fireEmployee(analyst)).thenReturn(true);
        Mockito.when(mockedDAO.fireEmployee(nonDBAnalyst)).thenReturn(false);
        Mockito.when(mockedDAO.fireEmployee(intern)).thenReturn(true);
        Mockito.when(mockedDAO.fireEmployee(nonDBIntern)).thenReturn(false);

        //set return values for mockedDAO
        Mockito.when(mockedDAO.getUser(username)).thenReturn(intern);
        Mockito.when(mockedDAO.getUser(analyst.getUsername())).thenReturn(analyst);
        Mockito.when(mockedDAO.getUser(noneDBUser)).thenReturn(null);
        Mockito.when(mockedDAO.getUser("")).thenReturn(intern);
        Mockito.when(mockedDAO.getUser(null)).thenReturn(null);
        Mockito.when(mockedDAO.getUser("moneyManage2")).thenReturn(manager);
        Mockito.when(mockedDAO.getUser("Manage1")).thenReturn(nfManager);

        Mockito.when(mockedDAO.availableUsernameEmail(repeatUsername.getUsername(), repeatUsername.getEmailAddress())).thenReturn(false);
        Mockito.when(mockedDAO.availableUsernameEmail(repeatEmail.getUsername(), repeatEmail.getEmailAddress())).thenReturn(false);
        Mockito.when(mockedDAO.availableUsernameEmail(nonDBAnalyst.getUsername(), nonDBAnalyst.getEmailAddress())).thenReturn(true);
        Mockito.when(mockedDAO.availableUsernameEmail(nonDBIntern.getUsername(), nonDBIntern.getEmailAddress())).thenReturn(true);

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
        assertTrue(testInstance.hireEmployee(manager, newNonDBAnalyst)); //Test that a finance manager can hire a financial employee not already in the database
        assertTrue(testInstance.hireEmployee(nfManager, newNonDBIntern)); //Test that a non-finance manager can hire a non-financial employee that's not already in the database
    }

    @Test
    public void testHireEmployeeFailure() {
        assertFalse(testInstance.hireEmployee(nfManager, newNonDBAnalyst)); //this test is to make sure a non-financial manager can't hire a financial employee (but the employee input is valid)
        assertFalse(testInstance.hireEmployee(manager, newNonDBIntern)); //this test is to make sure a financial manager can't hire a non-financial employee (but the employee isn't in the database)

        assertFalse(testInstance.hireEmployee(nfManager, newIntern)); //this test is to make sure a non-financial manager can't hire a non-financial employee who already exists
        assertFalse(testInstance.hireEmployee(manager, newAnalyst)); //this test is to make sure a financial manager can't hire a financial employee who already exists

        assertFalse(testInstance.hireEmployee(analyst, newNonDBAnalyst)); //this test is to make sure a non-manager role can't hire anybody
        assertFalse(testInstance.hireEmployee(nfManager, newNFManager)); //this test is to make sure that a mangager can't hire themselves

        assertFalse(testInstance.hireEmployee(manager, newRepeatUsername)); //this test is to make sure we can't hire anyone whose username already exists in the database
        assertFalse(testInstance.hireEmployee(manager, newRepeatEmail)); //this test is to make sure we can't hire anyone whose email already exists in the database
    }

    @Test
    public void testFireEmployeeSuccess() {
        assertTrue(testInstance.fireEmployee(manager, "Capn")); //This test is to make sure that a finance manager can fire an existing financial employee in the database
        assertTrue(testInstance.fireEmployee(nfManager, "rfloyd01")); //This test is to make sure that a non-finance manager can fire an existing non-financial employee in the database
    }

    @Test
    public void testFireEmployeeFailure() {
        assertFalse(testInstance.fireEmployee(manager, "rfloyd01")); //This test is to make sure that a finance manager can't fire an existing non-financial employee in the database
        assertFalse(testInstance.fireEmployee(nfManager, "Capn")); //This test is to make sure that a non-finance manager can't fire an existing financial employee in the database

        assertFalse(testInstance.fireEmployee(nfManager, "rfloyd02")); //This test is to make sure that you can't fire someone who doesn't exist in the database
        assertFalse(testInstance.fireEmployee(nfManager, "moneyManage2"));
        assertFalse(testInstance.fireEmployee(manager, "moneyManage2"));
        assertFalse(testInstance.fireEmployee(manager, "Manage1"));
    }
}
