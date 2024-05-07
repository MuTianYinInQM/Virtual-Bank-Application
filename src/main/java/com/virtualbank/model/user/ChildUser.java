package com.virtualbank.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.virtualbank.model.AccountManager;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildUser extends User {
    private AccountManager accountsManager;

    public ChildUser() {
        super();
        this.accountsManager = new AccountManager(); // 在创建ChildUser时初始化AccountManager
    }

    public ChildUser(String username, String password, AccountManager accounts) {
        super(username, password);
        this.accountsManager = accounts;
    }


    @JsonProperty("accountsManager")
    public AccountManager getAccountsManager() {
        return accountsManager;
    }

    @JsonProperty("accountsManager")
    public void setAccountsManager(AccountManager accountsManager) {
        this.accountsManager = accountsManager;
    }
}