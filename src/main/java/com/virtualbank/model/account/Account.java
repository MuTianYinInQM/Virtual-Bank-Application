package com.virtualbank.model.account;

public class Account {
    private String id;
    private String type;
    private double balance;

    public Account(String id, String type, double balance) {
        this.id = id;
        this.type = type;
        this.balance = balance;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
