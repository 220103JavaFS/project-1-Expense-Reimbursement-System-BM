package com.revature.services;

import com.revature.models.reimbursement.ReimbursementRequest;
import com.revature.models.reimbursement.ReimbursementStatus;
import com.revature.models.reimbursement.ReimbursementType;
import com.revature.models.users.FinanceAnalyst;
import com.revature.models.users.FinanceManager;
import com.revature.models.users.Intern;
import com.revature.models.users.NonFinanceManager;
import com.revature.repos.ReimbursementRequestsDAO;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;

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

        //set up Mockito instance of DAO
        MockitoAnnotations.openMocks(this);
        testInstance = new ReimbursementRequestsService(mockedDAO);
    }
}
