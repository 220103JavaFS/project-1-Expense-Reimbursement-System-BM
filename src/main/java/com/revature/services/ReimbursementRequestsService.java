package com.revature.services;

import com.revature.models.reimbursement.ReimbursementRequest;
import com.revature.models.users.User;
import com.revature.repos.ReimbursementRequestsDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ReimbursementRequestsService {

    private ReimbursementRequestsDAO reimbursementRequestsDAO;
    private Logger log = LoggerFactory.getLogger(ReimbursementRequestsService.class);

    //CONSTRUCTORS
    ReimbursementRequestsService() {}
    ReimbursementRequestsService(ReimbursementRequestsDAO reimbursementRequestsDAO) {
        this.reimbursementRequestsDAO = reimbursementRequestsDAO;
    }

    //GET METHODS
    public ArrayList<ReimbursementRequest> getCurrentUserReimbursementRequestsService(User user) {
        return null;
    }
    public ArrayList<ReimbursementRequest> getAllPendingReimbursementRequestsService() {
        return null;
    }

    //POST METHODS
    public boolean createReimbursementRequestService(ReimbursementRequest RR) {
        return true;
    }

    //PATCH METHODS
    public boolean editReimbursementRequestService(ReimbursementRequest RR) {
        return true;
    } //functionality for approving and denying requests is incorporated in here

    //DELETE METHODS
    public boolean deleteReimbursementRequestService(ReimbursementRequest RR) {
        return true;
    }
}
