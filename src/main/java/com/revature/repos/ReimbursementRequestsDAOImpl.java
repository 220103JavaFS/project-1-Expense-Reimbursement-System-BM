package com.revature.repos;

import com.revature.models.reimbursement.ReimbursementRequest;
import com.revature.models.users.Employee;
import com.revature.models.users.User;
import com.revature.models.users.UserFactory;
import com.revature.util.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class ReimbursementRequestsDAOImpl implements ReimbursementRequestsDAO {
    Logger log = LoggerFactory.getLogger(ReimbursementRequestsDAOImpl.class);

    @Override
    public ArrayList<ReimbursementRequest> getUserCurrentReimbursementRequestsDAO(int userId) {
        //create an array to store the results in. Create the Array outside of the below try block so that we can at least
        //return an empty array in the case that SQLException is encountered
        ArrayList<ReimbursementRequest> reimbursementRequests = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection()) {
            //The employee table is linked to the reimbursement table via their userID which is the primary key
            String sql = "SELECT * FROM ers_reimbursement WHERE reimb_author = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);

            int statementCounter = 0;
            statement.setInt(++statementCounter, userId);
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                ReimbursementRequest reimbursementRequest = new ReimbursementRequest();

                reimbursementRequest.setReimbursementID(result.getInt("reimb_id"));
                reimbursementRequest.setReimbursementAmount(result.getDouble("reimb_amount"));
                reimbursementRequest.setReimbursementSubmitted(result.getTimestamp("reimb_submitted"));
                reimbursementRequest.setReimbursementResolved(result.getTimestamp("reimb_resolved"));
                reimbursementRequest.setReimbursementDescription(result.getString("reimb_description"));
                reimbursementRequest.setReimbursementReceipt(result.getBytes("reimb_receipt"));
                reimbursementRequest.setReimbursementAuthor(userId); //we already passed this info to the function
                reimbursementRequest.setReimbursementResolver(result.getInt("reimb_resolver"));
                reimbursementRequest.setReimbursementStatusId(result.getInt("reimb_status_id"));
                reimbursementRequest.setReimbursementTypeId(result.getInt("reimb_type_id"));

                reimbursementRequests.add(reimbursementRequest);
            }

            return reimbursementRequests;
        } catch (SQLException e) {
            log.info("Encountered an issue accessing user requests and temporarily deleted sessionAttribute. Need to update session attribute.");
            return reimbursementRequests;
        }
    }
    public ArrayList<ReimbursementRequest> getUserCurrentReimbursementRequestsDAOConn(int userId, Connection conn) {
        //Same as the getUserCurrentReimbursementRequestsDAO() method, however, uses an existing connection instead of
        //creating a new one
        ArrayList<ReimbursementRequest> reimbursementRequests = new ArrayList<>();

        try  {
            //The employee table is linked to the reimbursement table via their userID which is the primary key
            String sql = "SELECT * FROM ers_reimbursement WHERE reimb_author = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);

            int statementCounter = 0;
            statement.setInt(++statementCounter, userId);
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                ReimbursementRequest reimbursementRequest = new ReimbursementRequest();

                reimbursementRequest.setReimbursementID(result.getInt("reimb_id"));
                reimbursementRequest.setReimbursementAmount(result.getDouble("reimb_amount"));
                reimbursementRequest.setReimbursementSubmitted(result.getTimestamp("reimb_submitted"));
                reimbursementRequest.setReimbursementResolved(result.getTimestamp("reimb_resolved"));
                reimbursementRequest.setReimbursementDescription(result.getString("reimb_description"));
                reimbursementRequest.setReimbursementReceipt(result.getBytes("reimb_receipt"));
                reimbursementRequest.setReimbursementAuthor(userId); //we already passed this info to the function
                reimbursementRequest.setReimbursementResolver(result.getInt("reimb_resolver"));
                reimbursementRequest.setReimbursementStatusId(result.getInt("reimb_status_id"));
                reimbursementRequest.setReimbursementTypeId(result.getInt("reimb_type_id"));

                reimbursementRequests.add(reimbursementRequest);
            }

            return reimbursementRequests;
        } catch (SQLException e) {
            log.info("Encountered an issue accessing user requests and temporarily deleted sessionAttribute. Need to update session attribute.");
            return reimbursementRequests;
        }
    }

    @Override
    public ArrayList<ReimbursementRequest> getAllPendingReimbursementRequestsDAO() {
        //create an array to store the results in. Create the Array outside of the below try block so that we can at least
        //return an empty array in the case that SQLException is encountered
        ArrayList<ReimbursementRequest> availableReimbursementRequests = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection()) {
            //The employee table is linked to the reimbursement table via their userID which is the primary key
            String sql = "SELECT * FROM ers_reimbursement WHERE reimb_status_id = 2;";
            Statement statement = conn.createStatement(); //no need to prepare our statement here

            ResultSet result = statement.executeQuery(sql);

            while(result.next()) {
                ReimbursementRequest reimbursementRequest = new ReimbursementRequest();

                reimbursementRequest.setReimbursementID(result.getInt("reimb_id"));
                reimbursementRequest.setReimbursementAmount(result.getDouble("reimb_amount"));
                reimbursementRequest.setReimbursementSubmitted(result.getTimestamp("reimb_submitted"));
                reimbursementRequest.setReimbursementResolved(result.getTimestamp("reimb_resolved"));
                reimbursementRequest.setReimbursementDescription(result.getString("reimb_description"));
                reimbursementRequest.setReimbursementReceipt(result.getBytes("reimb_receipt"));
                reimbursementRequest.setReimbursementAuthor(result.getInt("reimb_author")); //we already passed this info to the function
                reimbursementRequest.setReimbursementResolver(result.getInt("reimb_resolver"));
                reimbursementRequest.setReimbursementStatusId(result.getInt("reimb_status_id"));
                reimbursementRequest.setReimbursementTypeId(result.getInt("reimb_type_id"));

                availableReimbursementRequests.add(reimbursementRequest);
            }

            return availableReimbursementRequests;
        } catch (SQLException e) {
            log.info("Encountered an issue accessing user requests and temporarily deleted sessionAttribute. Need to update session attribute.");
            return availableReimbursementRequests;
        }
    }
    public ArrayList<ReimbursementRequest> getAllPendingReimbursementRequestsDAOConn(Connection conn) {
        //exact same as the above function, however, we use an existing connection to save on resources
        ArrayList<ReimbursementRequest> availableReimbursementRequests = new ArrayList<>();

        try {
            //The employee table is linked to the reimbursement table via their userID which is the primary key
            String sql = "SELECT * FROM ers_reimbursement WHERE reimb_status_id = 2;";
            Statement statement = conn.createStatement(); //no need to prepare our statement here

            ResultSet result = statement.executeQuery(sql);

            while(result.next()) {
                ReimbursementRequest reimbursementRequest = new ReimbursementRequest();

                reimbursementRequest.setReimbursementID(result.getInt("reimb_id"));
                reimbursementRequest.setReimbursementAmount(result.getDouble("reimb_amount"));
                reimbursementRequest.setReimbursementSubmitted(result.getTimestamp("reimb_submitted"));
                reimbursementRequest.setReimbursementResolved(result.getTimestamp("reimb_resolved"));
                reimbursementRequest.setReimbursementDescription(result.getString("reimb_description"));
                reimbursementRequest.setReimbursementReceipt(result.getBytes("reimb_receipt"));
                reimbursementRequest.setReimbursementAuthor(result.getInt("reimb_author")); //we already passed this info to the function
                reimbursementRequest.setReimbursementResolver(result.getInt("reimb_resolver"));
                reimbursementRequest.setReimbursementStatusId(result.getInt("reimb_status_id"));
                reimbursementRequest.setReimbursementTypeId(result.getInt("reimb_type_id"));

                availableReimbursementRequests.add(reimbursementRequest);
            }

            return availableReimbursementRequests;
        } catch (SQLException e) {
            log.info("Encountered an issue accessing user requests and temporarily deleted sessionAttribute. Need to update session attribute.");
            return availableReimbursementRequests;
        }
    }

    @Override
    public int createReimbursementRequestDAO(ReimbursementRequest RR) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            //Since each employee has a list of customers associated with them, we don't need to actually query the
            //customer table in our original call to the database.
            String sql = "INSERT INTO ers_reimbursement (reimb_amount, reimb_submitted, reimb_description, reimb_receipt, reimb_author, reimb_status_id, reimb_type_id) VALUES" +
                    " (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = conn.prepareStatement(sql);

            //note that it's not possible to create a new request with a resolved status so we leave out any resolved fields
            int currentLocation = 0;
            statement.setDouble(++currentLocation, RR.getReimbursementAmount());
            statement.setTimestamp(++currentLocation, RR.getReimbursementSubmitted());
            statement.setString(++currentLocation, RR.getReimbursementDescription());
            statement.setBytes(++currentLocation, RR.getReimbursementReceipt());
            statement.setInt(++currentLocation, RR.getReimbursementAuthor());
            statement.setInt(++currentLocation, RR.getReimbursementStatusId());
            statement.setInt(++currentLocation, RR.getReimbursementTypeId());

            statement.execute();


            //if no username match was found then return null
            log.info("Reimbursement Request added to the database");
            return 0;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return 0b1000; //code for database error
        }
    }

    @Override
    public int editReimbursementRequestDAO(ReimbursementRequest RR) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "UPDATE ers_reimbursement SET reimb_amount = ?, reimb_submitted = ?, reimb_resolved = ?, reimb_description = ?, reimb_receipt = ?, " +
                    "reimb_author = ?, reimb_resolver = ?, reimb_status_id = ?, reimb_type_id = ? WHERE reimb_id = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);
            int currentLocation = 0;
            statement.setDouble(++currentLocation, RR.getReimbursementAmount());
            statement.setTimestamp(++currentLocation, RR.getReimbursementSubmitted());
            statement.setTimestamp(++currentLocation, RR.getReimbursementResolved());
            statement.setString(++currentLocation, RR.getReimbursementDescription());
            statement.setBytes(++currentLocation, RR.getReimbursementReceipt());
            statement.setInt(++currentLocation, RR.getReimbursementAuthor());
            if(RR.getReimbursementResolver() != 0) statement.setInt(++currentLocation, RR.getReimbursementResolver());
            else statement.setNull(++currentLocation, Types.NULL);
            statement.setInt(++currentLocation, RR.getReimbursementStatusId());
            statement.setInt(++currentLocation, RR.getReimbursementTypeId());
            statement.setInt(++currentLocation, RR.getReimbursementID());

            statement.execute();
            return 0; //updated without issue
        } catch (SQLException e) {
            log.info(e.toString());
            return -1;
        }
    }

    @Override
    public int deleteReimbursementRequestDAO(ReimbursementRequest RR) {
        try (Connection conn = ConnectionUtil.getConnection()) {

            String sql = "DELETE FROM ers_reimbursement WHERE reimb_id = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, RR.getReimbursementID());

            statement.execute();
            return 0;
        } catch (SQLException e) {
            log.info(e.toString());
            return 0b10;
        }
    }
}
