package com.revature.repos;

import com.revature.models.users.User;

public class UserDAOImpl implements UsersDAO{
    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public boolean availableUsernameEmail(String username, String email) {
        return false;
    }

    @Override
    public boolean hireEmployee(User newEmployee) {
        return false;
    }

    @Override
    public boolean fireEmployee(User exEmployee) {
        return false;
    }
}
