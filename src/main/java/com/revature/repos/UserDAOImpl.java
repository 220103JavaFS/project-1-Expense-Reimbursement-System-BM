package com.revature.repos;

import com.revature.models.users.Employee;
import com.revature.models.users.User;
import com.revature.models.users.UserFactory;
import com.revature.util.ConnectionUtil;
import com.revature.util.NewUser;

import java.sql.*;
import java.util.ArrayList;

public class UserDAOImpl implements UsersDAO{
    @Override
    public User getUser(String username) {
        //used by admins to view all users in the database
        try (Connection conn = ConnectionUtil.getConnection()) {
            //log.info("UserDAO getAllUsersDAO() method was called");
            //Since each employee has a list of customers associated with them, we don't need to actually query the
            //customer table in our original call to the database.
            String sql = "SELECT * FROM ers_users WHERE ers_username = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);

            int statementCounter = 0;
            statement.setString(++statementCounter, username);

            ResultSet result = statement.executeQuery();

            while(result.next()) {
                UserFactory factory = UserFactory.getFactory();
                User newUser = factory.makeUser(result.getInt("user_role_id"));

                newUser.setUserID(result.getInt("ers_users_id"));
                newUser.setUsername(result.getString("ers_username"));
                newUser.setPassword(result.getBytes("ers_password"));
                newUser.setFirstName(result.getString("user_first_name"));
                newUser.setLastName(result.getString("user_last_name"));
                newUser.setEmailAddress(result.getString("user_email"));
                //Note: we don't need to set the userRoleID as this happens upon user creation in the UserFactory

                //TODO: add function to get the reimbursement requests for the specific user from the database
                //TODO: add function to get the all pending reimbursement requests if the user is a manager

                //System.out.println(newUser);
                return newUser;
            }

            //if no username match was found then return null
            System.out.println("Couldn't find employee");
            return null;
        } catch (SQLException e) {
            System.out.println("problem");
            return null;
        }
    }

    @Override
    public boolean availableUsernameEmail(String username, String email) {
        //used by admins to view all users in the database
        System.out.println("Made it down to DAO layer");
        try (Connection conn = ConnectionUtil.getConnection()) {
            //log.info("UserDAO getAllUsersDAO() method was called");
            //Since each employee has a list of customers associated with them, we don't need to actually query the
            //customer table in our original call to the database.
            String sql = "SELECT * FROM ers_users WHERE ers_username = ? OR user_email = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);

            int statementCounter = 0;
            statement.setString(++statementCounter, username);
            statement.setString(++statementCounter, email);

            ResultSet result = statement.executeQuery();

            System.out.println("About to look at matching usernames");
            while(result.next()) {
                return false;
            }

            //if no username match was found then return null
            System.out.println("Couldn't find employee");
            return true;
        } catch (SQLException e) {
            System.out.println("problem");
            return false;
        }
    }

    @Override
    public boolean hireEmployee(NewUser newEmployee) {
        //used by admins to view all users in the database

        try (Connection conn = ConnectionUtil.getConnection()) {
            //log.info("UserDAO getAllUsersDAO() method was called");
            //Since each employee has a list of customers associated with them, we don't need to actually query the
            //customer table in our original call to the database.
            String sql = "INSERT INTO ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = conn.prepareStatement(sql);

            //create an encrypted byte array for the NewUser's password
            byte[] encryptedPassword = User.encryptPassword(newEmployee.password);

            int statementCounter = 0;
            statement.setString(++statementCounter, newEmployee.username);
            statement.setBytes(++statementCounter, encryptedPassword);
            statement.setString(++statementCounter, newEmployee.firstName);
            statement.setString(++statementCounter, newEmployee.lastName);
            statement.setString(++statementCounter, newEmployee.emailAddress);
            statement.setInt(++statementCounter, newEmployee.userRoleID);

            statement.execute();


            //if no username match was found then return null
            System.out.println("Employee added to database");
            return true;
        } catch (SQLException e) {
            System.out.println("problem");
            return false;
        }

    }

    @Override
    public boolean fireEmployee(User exEmployee) {
        return false;
    }
}
