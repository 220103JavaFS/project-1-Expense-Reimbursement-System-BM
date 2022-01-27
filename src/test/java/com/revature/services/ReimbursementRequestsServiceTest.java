package com.revature.services;

import com.revature.models.reimbursement.ReimbursementRequest;
import com.revature.models.reimbursement.ReimbursementStatus;
import com.revature.models.reimbursement.ReimbursementType;
import com.revature.models.users.*;
import com.revature.repos.ReimbursementRequestsDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReimbursementRequestsServiceTest {

    //FIELDS
    private ReimbursementRequestsService testInstance;

    @Mock
    private ReimbursementRequestsDAO mockedDAO;

    //Going to need a few instances of ReimbursementRequests that are "already in" our DB
    private ReimbursementRequest dbRequestOne;
    private ReimbursementRequest dbRequestTwo;
    private ReimbursementRequest dbRequestThree;
    private ReimbursementRequest dbRequestFour;
    private ReimbursementRequest dbRequestFive;
    private ReimbursementRequest dbRequestSix;
    private ReimbursementRequest dbRequestSeven;

    //And going to need a few instances of ReimbursementRequests that we want to add to our DB
    private ReimbursementRequest nonDBRequestOne;
    private ReimbursementRequest nonDBRequestTwo;
    private ReimbursementRequest nonDBRequestThree;

    //We'll also need some users that will create and approve the above requests
    private FinanceManager manager;
    private NonFinanceManager nfManager;
    private FinanceAnalyst analyst;
    private Intern intern;

    //Finally we'll create a timestamp variable
    private Timestamp currentTime;

    @BeforeEach
    public void setUp() {

        currentTime = new Timestamp(System.currentTimeMillis());

        //First create a few users to use
        manager = new FinanceManager(1, "moneyManage2", "helloW0rld", "Dan", "Preuss", "daniel.preuss@company.com");
        nfManager = new NonFinanceManager(2, "Manage1", "helloW0rld", "Matt", "Damon", "matthew.damon@company.com");
        analyst = new FinanceAnalyst(3, "Capn", "helloW0rld", "Steve", "Rogers", "captain.america@company.com");
        intern = new Intern(4, "rfloyd01", "helloW0rld", "Bobby", "Floyd", "robert.floyd@company.com");

        /*
        Constructor for ReimbursementRequests:
        int reimbursementID, double reimbursementAmount, Timestamp reimbursementSubmitted, Timestamp reimbursementResolved, String reimbursementDescription, byte[] reimbursementReceipt, User reimbursementAuthor, User reimbursementResolver, ReimbursementStatus reimbursementStatus, ReimbursementType reimbursementType
         */

        //Requests with "created" status
        dbRequestOne   = new ReimbursementRequest(1, 150.12, null, null, "Lunch with a client", null, intern, null, new ReimbursementStatus(1, "Created"), new ReimbursementType(2, "Food"));
        dbRequestTwo   = new ReimbursementRequest(2, 1000.24, null, null, "Lodging for a week", null, analyst, null, new ReimbursementStatus(1, "Created"), new ReimbursementType(1, "Lodging"));

        //Requests with "submitted" status
        dbRequestThree = new ReimbursementRequest(3, 12.50, currentTime, null, "Highway toll", null, manager, null, new ReimbursementStatus(2, "Submitted"), new ReimbursementType(3, "Travel"));
        dbRequestFour = new ReimbursementRequest(4, 501.34, currentTime, null, "Entertaining clients", null, nfManager, null, new ReimbursementStatus(2, "Submitted"), new ReimbursementType(4, "Other"));

        //Requests with "approved" status
        dbRequestFive   = new ReimbursementRequest(1, 150.12, null, null, "Lunch with a client", null, intern, null, new ReimbursementStatus(1, "Created"), new ReimbursementType(2, "Food"));
        dbRequestSix   = new ReimbursementRequest(2, 1000.24, null, null, "Lodging for a week", null, analyst, null, new ReimbursementStatus(1, "Created"), new ReimbursementType(1, "Lodging"));


        //set up Mockito instance of DAO
        MockitoAnnotations.openMocks(this);
        testInstance = new ReimbursementRequestsService(mockedDAO);
    }

    //Choosing not to test out the GetRequest Functions here, these are really more DAO layer methods

    @Test
    public void testCreateRequestSuccess() {
        assertTrue(testInstance.createReimbursementRequestService(dbRequestOne)); //this is a valid creation request
    }

    @Test
    public void testCreateRequestFailure() {
        /*
        Tests to carry out:
        1. Test that requests can't be created with approved status (can be handled in the front end)
        2. Test that requests can't be created with denied status (can probably be handled in the front end)
        3. Test that requests can't be created with the reimbursement ID filled out (this should be created on entering into the DB, probably can be handled in the front end)
        4. Test that requests created with the "created" status don't have the submittedTimestamp filled out
        5. Test that the requestDesription is less than 250 characters in length
        6. Test that the reimbursementType is valid (can also probably be handled in the front end)
         */

        assertFalse(testInstance.createReimbursementRequestService(dbRequestOne)); //Test that a request can't be created with the approved status
    }
}
