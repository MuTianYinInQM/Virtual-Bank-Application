package com.virtualbank.model.account;

import java.io.Serializable;
import java.util.UUID;

public abstract class Account implements Serializable {
    private UUID uuid;
    protected String accountName;
    protected double balance;
    protected double interestRate;

    public Account(String accountId, double balance) {
        this.accountName = accountId;
        this.balance = balance;
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getAccountName() {
        return accountName;
    }

    public double getBalance() {
        return balance;
    }

    public double getInterestRate() {
        return interestRate;
    }


    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        this.balance -= amount;
    }


    @Override
    public String toString() {
        return "Account{" +
                "uuid=" + uuid +
                ", accountName='" + accountName + '\'' +
                ", balance=" + balance +
                ", interestRate=" + interestRate +
                '}';
    }
}
