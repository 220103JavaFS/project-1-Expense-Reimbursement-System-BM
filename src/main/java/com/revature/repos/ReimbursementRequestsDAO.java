package com.revature.repos;

import com.revature.models.reimbursement.ReimbursementRequest;
import com.revature.models.users.User;

import java.util.ArrayList;

public interface ReimbursementRequestsDAO {

    //GET METHODS
    public ArrayList<ReimbursementRequest> getUserCurrentReimbursementRequestsDAO(User user); //returns all reimbursement requests for the user that have a status of created, submitted or denied
    public ArrayList<ReimbursementRequest> getAllPendingReimbursementRequestsDAO(); //returns all reimbursement requests in the database with a status of submitted (for approving managers only)

    //POST METHODS
    public int createReimbursementRequestDAO(ReimbursementRequest RR);

    //PATCH METHODS
    public int editReimbursementRequestDAO(ReimbursementRequest RR); //incorporates functionality for normal users to

    //DELETE METHODS
    public int deleteReimbursementRequestDAO(ReimbursementRequest RR);

}
