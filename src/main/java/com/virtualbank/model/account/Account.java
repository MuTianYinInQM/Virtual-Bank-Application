package com.virtualbank.model.account;

import java.io.Serializable;
import java.util.UUID;
import java.time.LocalDateTime;

public abstract class Account implements Serializable {
    private final UUID uuid;
    protected String accountName;
    protected double balance;
    protected double interestRate;
    protected final LocalDateTime createDateTime;
    protected double timeLapseCoefficient;

    // Constructor that initializes all fields
    public Account(String accountName, double initialBalance, double interestRate, double timeLapseCoefficient) {
        if (accountName == null || accountName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account name cannot be null or empty.");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        if (interestRate < -1) {
            throw new IllegalArgumentException("Interest rate cannot be less than -100%.");
        }
        if (timeLapseCoefficient <= 0) {
            throw new IllegalArgumentException("Time lapse coefficient must be positive.");
        }

        this.uuid = UUID.randomUUID();
        this.accountName = accountName;
        this.balance = initialBalance;
        this.interestRate = interestRate;
        this.createDateTime = LocalDateTime.now(); // Set to current date and time at the moment of account creation
        this.timeLapseCoefficient = timeLapseCoefficient;
    }

    // Getters and setters for all the fields
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

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public double getTimeLapseCoefficient() {
        return timeLapseCoefficient;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public void setTimeLapseCoefficient(double timeLapseCoefficient) {
        this.timeLapseCoefficient = timeLapseCoefficient;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    // Standard methods for deposit and withdrawal
    // TODO 可能需要加上logger 和setter 或者解耦 让 Account Manager 再封装一层干这个事情
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be positive");
        }
        this.balance -= amount;
    }

    // Abstract method to update interest must be implemented by subclasses
    public abstract void updateInterest();

    @Override
    public String toString() {
        return "Account{" +
                "uuid=" + uuid +
                ", accountName='" + accountName + '\'' +
                ", balance=" + balance +
                ", interestRate=" + interestRate +
                ", createDateTime=" + createDateTime +
                ", timeLapseCoefficient=" + timeLapseCoefficient +
                '}';
    }
}