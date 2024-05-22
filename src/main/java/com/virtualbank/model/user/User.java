package com.virtualbank.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
    private String username;
    private String password;
    private boolean isParent;

    public User() {}

    public User(String username, String password, boolean isParent) {
        this.username = username;
        this.password = password;
        this.isParent = isParent;
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

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean isParent) {
        this.isParent = isParent;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isParent=" + isParent +
                '}';
    }
}
