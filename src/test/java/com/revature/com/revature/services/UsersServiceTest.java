package com.revature.com.revature.services;

import com.revature.models.users.*;
import com.revature.repos.UsersDAO;
import com.revature.services.UsersService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

public class UsersServiceTest {

    //FIELDS
    private UsersService testInstance;

    @Mock
    private UsersDAO mockedDAO;

    private String username;
    private FinanceManager manager;
    private NonFinanceManager nfManager;
    private FinanceAnalyst analyst;
    private Intern intern;

    //BEFORE AND AFTER ANNOTATIONS
    @BeforeEach
    public void setUp() {
        //use BeforeEach because Mockito doesn't like BeforeAll

        //create users for tests
        username = "rfloyd01";
        manager = new FinanceManager(1, "moneyManage2", "helloW0rld", "Dan", "Preuss", "daniel.preuss@company.com");
    }
}
