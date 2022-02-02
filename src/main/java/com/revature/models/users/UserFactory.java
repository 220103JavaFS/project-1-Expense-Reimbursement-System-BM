package com.revature.models.users;

import com.revature.models.reimbursement.ReimbursementRequest;
import com.revature.repos.ReimbursementRequestsDAOImpl;

import java.sql.Connection;
import java.util.ArrayList;

public class UserFactory {

    private ReimbursementRequestsDAOImpl requestsDAO = new ReimbursementRequestsDAOImpl();
    //Singleton class for creation of users
    private static UserFactory factory;

    private UserFactory() { super(); }

    public User makeUser(int userRoleId, int userId, Connection conn) {
        //The user factory is invoked during new user creation in the user DAO.
        //It's also useful to get the new user's list existing reimbursement requests at this time. Because of this we
        //pass the connection object created in the User DAO and use it to also grab reimbursement information.

        //ArrayList<ReimbursementRequest> currentRequests = new ArrayList<>();
        //ArrayList<ReimbursementRequest> availableRequests = new ArrayList<>(); //only finance manager gets this

        if (userRoleId == 1) return new User();
        else if (userRoleId == 2) return new ExEmployee();
        else if (userRoleId == 3) {
            FinanceManager fm = new FinanceManager();
            fm.setCurrentReimbursementRequests(requestsDAO.getUserCurrentReimbursementRequestsDAOConn(userId, conn));
            fm.setAvailableReimbursementRequests(requestsDAO.getAllPendingReimbursementRequestsDAOConn(conn));
            return fm;
        }
        else if (userRoleId == 4) {
            NonFinanceManager nfm = new NonFinanceManager();
            nfm.setCurrentReimbursementRequests(requestsDAO.getUserCurrentReimbursementRequestsDAOConn(userId, conn));
            return nfm;
        }
        else if (userRoleId == 5) {
            FinanceAnalyst fa = new FinanceAnalyst();
            fa.setCurrentReimbursementRequests(requestsDAO.getUserCurrentReimbursementRequestsDAOConn(userId, conn));
            return fa;
        }
        else if (userRoleId == 6) {
            Engineer eng = new Engineer();
            eng.setCurrentReimbursementRequests(requestsDAO.getUserCurrentReimbursementRequestsDAOConn(userId, conn));
            return eng;
        }
        else {
            Intern in = new Intern();
            in.setCurrentReimbursementRequests(requestsDAO.getUserCurrentReimbursementRequestsDAOConn(userId, conn));
            return in;
        }
    }

    public static UserFactory getFactory() {
        if (factory == null) factory = new UserFactory();
        return factory;
    }
}
