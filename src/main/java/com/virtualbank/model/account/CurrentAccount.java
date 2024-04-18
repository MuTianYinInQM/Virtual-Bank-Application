package com.virtualbank.model.account;


import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;

public class CurrentAccount extends Account {
    private double interestRate;

    public CurrentAccount(String id, String type, double balance) {
        super(id, type, balance);
    }


    public double getInterestRate() {
        return interestRate;
    }

    public ChronoLocalDateTime<?> getLastInterestDate() {
        // 交给后人
        return null;
    }

    public void setLastInterestDate(LocalDateTime now) {
    }
}
