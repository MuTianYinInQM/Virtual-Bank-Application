package com.virtualbank.model.user;
/**
 * Represents a child user in the virtual bank system.
 */
public class ChildUser extends User {
    /**
     * Default constructor for creating a child user.
     * Sets the parent status to false.
     */
    public ChildUser() {
        super();
        this.setParent(false);
    }

    /**
     * Constructs a child user with the specified username and password.
     * Sets the parent status to false.
     *
     * @param username the username of the child user
     * @param password the password of the child user
     */
    public ChildUser(String username, String password) {
        super(username, password, false);
    }
}
