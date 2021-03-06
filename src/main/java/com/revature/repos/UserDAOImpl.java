package com.revature.repos;

import com.revature.models.users.Employee;
import com.revature.models.users.User;
import com.revature.models.users.UserFactory;
import com.revature.util.ConnectionUtil;
import com.revature.util.NewUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class UserDAOImpl implements UsersDAO{
    private Logger log = LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public User getUser(String username) {
        //used by admins to view all users in the database
        try (Connection conn = ConnectionUtil.getConnection()) {
            //Since each employee has a list of customers associated with them, we don't need to actually query the
            //customer table in our original call to the database.
            String sql = "SELECT * FROM ers_users WHERE ers_username = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);

            int statementCounter = 0;
            statement.setString(++statementCounter, username);

            ResultSet result = statement.executeQuery();

            while(result.next()) {
                UserFactory factory = UserFactory.getFactory();
                User newUser = factory.makeUser(result.getInt("user_role_id"), result.getInt("ers_users_id"), conn);

                newUser.setUserID(result.getInt("ers_users_id"));
                newUser.setUsername(result.getString("ers_username"));
                newUser.setPassword(result.getBytes("ers_password"));
                newUser.setFirstName(result.getString("user_first_name"));
                newUser.setLastName(result.getString("user_last_name"));
                newUser.setEmailAddress(result.getString("user_email"));
                //Note: we don't need to set the userRoleID as this happens upon user creation in the UserFactory

                return newUser;
            }

            //if no username match was found then return null
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public int availableUsernameEmail(String username, String email) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            //Since each employee has a list of customers associated with them, we don't need to actually query the
            //customer table in our original call to the database.
            String sql = "SELECT * FROM ers_users WHERE ers_username = ? OR user_email = ?;";
            PreparedStatement statement = conn.prepareStatement(sql); //SonarLint wants me to close this in a Finally clause, is that necessary when connection is opened in the try?

            int statementCounter = 0;
            statement.setString(++statementCounter, username);
            statement.setString(++statementCounter, email);

            ResultSet result = statement.executeQuery();
            int errorCode = 0;

            while(result.next()) {
                if (result.getString("ers_username").equals(username)) errorCode |= 0b100;
                if (result.getString("user_email").equals(email)) errorCode |= 0b1000;
            }

            //if no issues arose then the error code will be 0
            return errorCode;
        } catch (SQLException e) {
            return 0b10000000000; //code for database error
        }
    }

    @Override
    public int hireEmployee(NewUser newEmployee) {
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
            log.info("Employee added to database");
            return 0;
        } catch (SQLException e) {
            return 0b10000000000; //code for database error
        }

    }

    @Override
    public int fireEmployee(User exEmployee) {
        //used by admins to view all users in the database

        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "DELETE FROM ers_users WHERE ers_username = ?";
            PreparedStatement statement = conn.prepareStatement(sql);

            int statementCounter = 0;
            statement.setString(++statementCounter, exEmployee.getUsername());

            statement.execute();
            return 0;
        } catch (SQLException e) {
            return 3; //3 will represent a 500 http status in the controller layer
        }
    }
}
