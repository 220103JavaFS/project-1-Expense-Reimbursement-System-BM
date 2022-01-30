package com.revature.util;

import java.util.Objects;

public class NewUser {
    //This class is a DTO class for getting information to create a new user in the database

    //FIELDS
    public int userRoleID;
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public String emailAddress;

    public NewUser() {
    }

    public NewUser(int userRoleID, String username, String password, String firstName, String lastName, String emailAddress) {
        this.userRoleID = userRoleID;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewUser newUser = (NewUser) o;
        return userRoleID == newUser.userRoleID && Objects.equals(username, newUser.username) && Objects.equals(password, newUser.password) && Objects.equals(firstName, newUser.firstName) && Objects.equals(lastName, newUser.lastName) && Objects.equals(emailAddress, newUser.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRoleID, username, password, firstName, lastName, emailAddress);
    }

    @Override
    public String toString() {
        return "NewUser{" +
                "userRoleID=" + userRoleID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
