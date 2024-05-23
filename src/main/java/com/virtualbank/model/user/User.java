package com.virtualbank.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * Represents a user in the virtual bank system.
 */
public class User {
    private String username;
    private String password;
    private boolean isParent;
    /**
     * Default constructor for creating a user.
     */
    public User() {}

    /**
     * Constructs a user with the specified username, password, and parent status.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param isParent whether the user is a parent
     */
    public User(String username, String password, boolean isParent) {
        this.username = username;
        this.password = password;
        this.isParent = isParent;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the new username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the new password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns whether the user is a parent.
     *
     * @return true if the user is a parent, false otherwise
     */
    public boolean isParent() {
        return isParent;
    }

    /**
     * Sets the parent status of the user.
     *
     * @param isParent the new parent status of the user
     */
    public void setParent(boolean isParent) {
        this.isParent = isParent;
    }

    /**
     * Returns a string representation of the user.
     *
     * @return a string representation of the user
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isParent=" + isParent +
                '}';
    }
}
