package com.virtualbank.model.user;

public class ParentUser extends User {
    public ParentUser() {
        super();
        this.setParent(true);
    }

    public ParentUser(String username, String password) {
        super(username, password, true);
    }
}
