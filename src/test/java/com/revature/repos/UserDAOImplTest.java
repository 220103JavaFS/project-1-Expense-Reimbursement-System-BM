package com.revature.repos;

import com.revature.models.users.*;
import com.revature.util.NewUser;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

//Adding test orders as we want to make sure we Hire a test employee before trying to fire the test employee
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

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

    private String nonDBUsername;
    private FinanceManager manager;
    private Intern nonDBIntern;
    private String  availableUsername;
    private String availableEmail;

    private NewUser existingUsername;
    private NewUser existingEmail;
    private NewUser newAnalyst;
    private User newAnalystUser;

    private User rfloyd01;
    private User rfloyd01WrongID;

    //BEFORE AND AFTER ANNOTATIONS
    @BeforeEach
    public void setUp() {
        testInstance = new UserDAOImpl();

        //create users, usernames and newUsers for the tests
        nonDBUsername = "CousinSkeeter";
        availableUsername = "mahad12";
        availableEmail = "mahad12@gmail.com";

        manager = new FinanceManager(1, "moneyManage2", "helloW0rld", "Dan", "Preuss", "daniel.preuss@company.com");

        nonDBIntern = new Intern(8, "rfloyd02", "helloW0rld", "Robert", "Floyd", "robert.floyd2@company.com");

        existingUsername = new NewUser(5, "Ir0nMan", "helloW0rld", "Tony", "Stark", "tony.star2k@airproducts.com");
        existingEmail = new NewUser(5, "Ir0nMan2", "helloW0rld", "Tony", "Stark", "tony.stark@airproducts.com");
        newAnalyst = new NewUser(5, "Capn", "helloW0rld", "Steve", "Rogers", "captain.america@airproducts.com");
        newAnalystUser = new User(5, "Capn", "helloW0rld", "Steve", "Rogers", "captain.america@airproducts.com");

        rfloyd01 = new FinanceManager(1, "rfloyd01", "Coding_is_Kewl34", "Robert", "Floyd", "robert.floyd@airproducts.com");
        rfloyd01WrongID = new FinanceManager(2, "rfloyd01", "Coding_is_Kewl34", "Robert", "Floyd", "robert.floyd@airproducts.com");
    }

    @Test
    @Order(1)
    void testGetUserSucccess() {
        assertEquals(testInstance.getUser(rfloyd01.getUsername()), rfloyd01); //assert that the user returned from the database matches the pretend user we created here
        assertEquals(testInstance.getUser(rfloyd01WrongID.getUsername()), rfloyd01); //assert that the user returned from the database matches our pretend user even if wrong info is passed (when searching for a user only the username is used)
    }

    @Test
    @Order(2)
    public void testGetUserFailure() {
        assertNull(testInstance.getUser(nonDBUsername)); //searching for a user not in the actual database should return a null value
        assertNull(testInstance.getUser("")); //blank names should be captured in service layer but just to test anyway, searching on a blank name should return a null value
        assertNotEquals(testInstance.getUser(rfloyd01.getUsername()), manager); //searching for an existing user shouldn't return a different existing user
    }

    @Test
    @Order(3)
    public void testHireEmployeeSuccess() {
        assertEquals(testInstance.hireEmployee(newAnalyst), 0); //Hiring a user with a completely original username and email address should work without issue
    }

    @Test
    @Order(4)
    public void testHireEmployeeFailure() {
        //The three tests below should actually be caught elsewhere before the Hire() method is ever invoked, however, should still be tested here just in case
        assertEquals(testInstance.hireEmployee(existingUsername), 0b10000000000); //Trying to hire an employee with a username that already exists in the database should throw a SQLException and return the given error code (chosen by us)
        assertEquals(testInstance.hireEmployee(existingEmail), 0b10000000000); //Trying to hire an employee with an email that already exists in the database should throw a SQLException and return the given error code (chosen by us)
    }

    @Test
    @Order(5)
    public void testFireEmployeeSuccess() {
        assertEquals(testInstance.fireEmployee(newAnalystUser), 0); //We should be able to fire the employee entered into the database during test 3 above
        assertEquals(testInstance.fireEmployee(nonDBIntern), 0); //trying to fire someone NOT in the database will actually work, however, nothing will happen so we should actually shouldn't return an error here
    }

    //I'm currently not sure of good tests to the fire employee failure. It will only fail if a SQLException is raised. Comment it out for now
//    @Test
//    public void testFireEmployeeFailure() {
//
//    }

    @Test
    @Order(6)
    public void testAvailableUsernameEmailSuccess() {
        assertEquals(testInstance.availableUsernameEmail(availableUsername, availableEmail), 0); //This test is to make sure that a finance manager can fire an existing financial employee in the database
    }

    @Test
    @Order(7)
    public void testAvailableUsernameEmailFailure() {
        assertEquals(testInstance.availableUsernameEmail(existingUsername.username, nonDBIntern.getEmailAddress()), 4); //if the username isn't available but the email is, we should get error code 4
        assertEquals(testInstance.availableUsernameEmail(nonDBIntern.getUsername(), existingEmail.emailAddress), 8); //if the email isn't available but the username is, we should get error code 8
        assertEquals(testInstance.availableUsernameEmail(existingUsername.username, existingEmail.emailAddress), 12); //if neither the username or email is available we should get both of the above error codes which combine for a value of 12
    }
}
