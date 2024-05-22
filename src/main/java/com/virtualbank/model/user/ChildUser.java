package com.virtualbank.model.user;

public class ChildUser extends User {
    public ChildUser() {
        super();
        this.setParent(false);
    }

    public ChildUser(String username, String password) {
        super(username, password, false);
    }
}
