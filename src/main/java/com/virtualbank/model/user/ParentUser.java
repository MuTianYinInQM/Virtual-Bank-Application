package com.virtualbank.model.user;

/**
 * Represents a parent user in the virtual bank system.
 */
public class ParentUser extends User {
    /**
     * Default constructor for creating a parent user.
     * Sets the parent status to true.
     */
    public ParentUser() {
        super();
        this.setParent(true);
    }

    /**
     * Constructs a parent user with the specified username and password.
     * Sets the parent status to true.
     *
     * @param username the username of the parent user
     * @param password the password of the parent user
     */
    public ParentUser(String username, String password) {
        super(username, password, true);
    }
}
