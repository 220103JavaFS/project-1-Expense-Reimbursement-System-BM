package com.revature.util;

public class LoginAttempt {
    private String username;
    private String password;

    public LoginAttempt(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginAttempt{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
