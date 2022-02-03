package com.revature.models.users;

import com.revature.models.reimbursement.ReimbursementRequest;

import java.util.ArrayList;

public class UserFactory {

    //Singleton class for creation of users
    private static UserFactory factory;

    private UserFactory() { super(); }

    public User makeUser(int userRoleId) {
        ArrayList<ReimbursementRequest> currentRequests = new ArrayList<>();
        ArrayList<ReimbursementRequest> availableRequests = new ArrayList<>(); //only finance manager gets this

        if (userRoleId == 1) return new User();
        else if (userRoleId == 2) return new ExEmployee();
        else if (userRoleId == 3) {
            FinanceManager fm = new FinanceManager();
            fm.setCurrentReimbursementRequests(currentRequests);
            fm.setAvailableReimbursementRequests(availableRequests);
            return fm;
        }
        else if (userRoleId == 4) {
            NonFinanceManager nfm = new NonFinanceManager();
            nfm.setCurrentReimbursementRequests(currentRequests);
            return nfm;
        }
        else if (userRoleId == 5) {
            FinanceAnalyst fa = new FinanceAnalyst();
            fa.setCurrentReimbursementRequests(currentRequests);
            return fa;
        }
        else if (userRoleId == 6) {
            Engineer eng = new Engineer();
            eng.setCurrentReimbursementRequests(currentRequests);
            return eng;
        }
        else {
            Intern in = new Intern();
            in.setCurrentReimbursementRequests(currentRequests);
            return in;
        }
    }

    public static UserFactory getFactory() {
        if (factory == null) factory = new UserFactory();
        return factory;
    }
}
