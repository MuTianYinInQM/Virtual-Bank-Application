package com.virtualbank.model.account;

import java.time.LocalDateTime;
import java.time.Duration;

public class CurrentAccount extends Account {
    // 上一次更新余额的时间
    private LocalDateTime lastSettlementDate;
    // interestRate 是年化利率；interestRatePerDay是每天的利率 需要开根号
    private double interestRatePerDay;

    private static double calculateDailyInterestRate(double interestRate_) {
        return Math.pow(1 + interestRate_, 1.0 / 365) - 1;
    }

    public CurrentAccount(String accountName, double initialBalance, double interestRate, double timeLapseCoefficient) {
        super(accountName, initialBalance, interestRate, timeLapseCoefficient);
        this.interestRatePerDay = calculateDailyInterestRate(interestRate);  // Calculate the daily interest rate
        this.lastSettlementDate = createDateTime;  // Assume last settlement is at creation time
    }

    public LocalDateTime getLastSettlementDate() {
        return lastSettlementDate;
    }

    public double getInterestRatePerDay() {
        return interestRatePerDay;
    }

    public void updateInterest() {
        LocalDateTime now = LocalDateTime.now();
        // Calculate the real time difference using Duration
        Duration duration = Duration.between(lastSettlementDate, now);
        long seconds = duration.getSeconds();

        // Adjust seconds based on the time lapse coefficient and calculate full days
        long adjustedSeconds = (long) (seconds * timeLapseCoefficient);
        long days = adjustedSeconds / (24 * 60 * 60);  // convert seconds to days

        if (days > 0) {
            // Update balance with compound interest for the full days
            this.balance *= Math.pow(1 + interestRatePerDay, days);

            // Update last settlement date by adding the calculated days
            lastSettlementDate = lastSettlementDate.plusSeconds((long) ((days * 24 * 60 * 60) / timeLapseCoefficient));
        }
    }

    @Override
    public String toString() {
        return "CurrentAccount{" +
                "lastSettlementDate=" + lastSettlementDate +
                ", interestRatePerDay=" + interestRatePerDay +
                ", accountName='" + accountName + '\'' +
                ", balance=" + balance +
                ", interestRate=" + interestRate +
                ", createDateTime=" + createDateTime +
                ", timeLapseCoefficient=" + timeLapseCoefficient +
                '}';
    }
}

