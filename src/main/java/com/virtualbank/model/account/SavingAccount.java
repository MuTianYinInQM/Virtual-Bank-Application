package com.virtualbank.model.account;

public class SavingAccount extends Account {
    public SavingAccount(String id, String type, double balance) {
        super(id, type, balance);
    }

    public double getTerm() {
        return 0.0;
    }
}
