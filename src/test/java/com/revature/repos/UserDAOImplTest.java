package com.revature.repos;

import com.revature.models.users.*;
import com.revature.util.NewUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOImplTest {
    //TODO: In the service layer we mocked a copy of this UserDAO class which makes sense to me as we didn't want errors
    //  in this class to interfere with our unit testing there. For testing of this class, however, the only thing that we'd
    //  have to mock is our database itself. Do we need to actually mock anything for our tests here? Or should we really just
    //  be connecting to the physical database? For now just assume that we should be physically connecting to our database- Question for Tim

    //FIELDS
    private UserDAOImpl testInstance;

    //create Java versions of the users that already exist in the database. We need to ensure that the objects returned by
    //obtained by the DAO match what we would actually expect the objects to look like if we just created them in Java. The script
    //to create the database from scratch is located in this project as SQLscript.txt so the users here reflect the ones seen in
    //that file

    private String username;
    private String nonDBUsername;
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

    private User rfloyd01;
    private User rfloyd01WrongID;

    //BEFORE AND AFTER ANNOTATIONS
    @BeforeEach
    public void setUp() {
        //use BeforeEach because Mockito doesn't like BeforeAll
        testInstance = new UserDAOImpl();

        //create users for tests
        username = "rfloyd01";
        nonDBUsername = "rfloyd02";
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

        rfloyd01 = new FinanceManager(1, "rfloyd01", "Coding_is_Kewl34", "Robert", "Floyd", "robert.floyd@airproducts.com");
        rfloyd01WrongID = new FinanceManager(2, "rfloyd01", "Coding_is_Kewl34", "Robert", "Floyd", "robert.floyd@airproducts.com");
    }

    @Test
    void testGetUserSucccess() {
        System.out.println(rfloyd01); //TODO: Delete after debugging
        assertEquals(testInstance.getUser(rfloyd01.getUsername()), rfloyd01); //assert that the user returned from the database matches the pretend user we created here
//        assertEquals(testInstance.getUser(rfloyd01WrongID.getUsername()), rfloyd01); //assert that the user returned from the database matches our pretend user even if wrong info is passed (when searching for a user only the username is used)
//        assertNull(testInstance.getUser(nonDBUsername)); //searching for a user not in the actual database should return a null value
    }

//    @Test
//    public void testGetUserFailure() {
//        assertFalse(testInstance.getUser(username) == manager);
//        assertFalse(testInstance.getUser(noneDBUser) == intern);
//        assertFalse(testInstance.getUser("") == intern); //this test is to make sure that our service layer fails upon receiving an empty string
//    }
//
//    @Test
//    public void testHireEmployeeSuccess() {
//        assertTrue(testInstance.hireEmployee(newNonDBAnalyst) == 0); //Test that a finance manager can hire a financial employee not already in the database
//    }
//
//    @Test
//    public void testHireEmployeeFailure() {
//        assertFalse(testInstance.hireEmployee(newAnalyst) == 0); //this test is to make sure a non-financial manager can't hire a financial employee (but the employee input is valid)
//    }
//
//    @Test
//    public void testFireEmployeeSuccess() {
//        assertTrue(testInstance.fireEmployee(nonDBAnalyst) == 0); //This test is to make sure that a finance manager can fire an existing financial employee in the database
//    }
//
//    @Test
//    public void testFireEmployeeFailure() {
//        assertFalse(testInstance.fireEmployee(analyst) == 0);
//    }
//
//    @Test
//    public void testAvailableUsernameEmailSuccess() {
//        assertTrue(testInstance.availableUsernameEmail(availableUsername, availableEmail) == 0); //This test is to make sure that a finance manager can fire an existing financial employee in the database
//    }
//
//    @Test
//    public void testAvailableUsernameEmailFailure() {
//        assertFalse(testInstance.availableUsernameEmail(username, email) == 0);
//        assertFalse(testInstance.availableUsernameEmail(availableUsername, email) == 0);
//        assertFalse(testInstance.availableUsernameEmail(username, availableEmail) == 0);
//    }
}
