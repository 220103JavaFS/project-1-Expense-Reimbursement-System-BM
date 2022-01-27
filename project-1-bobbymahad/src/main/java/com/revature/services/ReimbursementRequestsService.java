package com.revature.services;

import com.revature.models.reimbursement.ReimbursementRequest;
import com.revature.models.users.User;

import java.util.ArrayList;

public class ReimbursementRequestsService {

    //GET METHODS
    public ArrayList<ReimbursementRequest> getUserCurrentReimbursementRequestsService(User user) {
        return null;
    }
    public ArrayList<ReimbursementRequest> getUserLegacyReimbursementRequestsService(User user) {
        return null;
    }
    public ArrayList<ReimbursementRequest> getAllPendingReimbursementRequestsService(User user) {
        return null;
    }

    //POST METHODS
    public boolean createReimbursementRequestService(ReimbursementRequest RR) {
        return true;
    }

    //PATCH METHODS
    public boolean editReimbursementRequestService(ReimbursementRequest RR) {
        return true;
    }
    public boolean approveReimbursementRequestService(ReimbursementRequest RR) {
        return true;
    }
    public boolean denyReimbursementRequestService(ReimbursementRequest RR) {
        return true;
    }

    //DELETE METHODS
    public boolean deleteReimbursementRequestService(ReimbursementRequest RR) {
        return true;
    }
}
