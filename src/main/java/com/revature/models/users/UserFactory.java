package com.revature.models.users;

import com.revature.models.reimbursement.ReimbursementRequest;

import java.util.ArrayList;

public class UserFactory {

    //Singleton class for creation of users
    private static UserFactory factory;

    private UserFactory() { super(); }

    public User makeUser(int userRoleId) {
        if (userRoleId == 1) return new User();
        else if (userRoleId == 2) return new ExEmployee();
        else if (userRoleId == 3) return new FinanceManager();
        else if (userRoleId == 4) return new NonFinanceManager();
        else if (userRoleId == 5) return new FinanceAnalyst();
        else if (userRoleId == 6) return new Engineer();
        else return new Intern();
    }

    public static UserFactory getFactory() {
        if (factory == null) factory = new UserFactory();
        return factory;
    }
}
