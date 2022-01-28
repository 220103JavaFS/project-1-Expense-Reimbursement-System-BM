package com.revature.repos;

import com.revature.models.reimbursement.ReimbursementRequest;
import com.revature.models.users.User;

import java.util.ArrayList;

public class ReimbursementRequestsDAOImpl implements ReimbursementRequestsDAO {
    @Override
    public ArrayList<ReimbursementRequest> getUserCurrentReimbursementRequestsDAO(User user) {
        return null;
    }

    @Override
    public ArrayList<ReimbursementRequest> getAllPendingReimbursementRequestsDAO() {
        return null;
    }

    @Override
    public int createReimbursementRequestDAO(ReimbursementRequest RR) {
        return 0;
    }

    @Override
    public int editReimbursementRequestDAO(ReimbursementRequest RR) {
        return 0;
    }

    @Override
    public int deleteReimbursementRequestDAO(ReimbursementRequest RR) {
        return 0;
    }
}
