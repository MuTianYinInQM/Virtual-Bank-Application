package com.virtualbank.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.virtualbank.model.AccountManager;
import java.util.ArrayList;
import java.util.List;

public class ChildUser extends User {


    public ChildUser() {
    }

    public ChildUser(String username, String password) {
        super(username, password);
    }


}