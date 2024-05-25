package com.virtualbank.model.account;

import java.time.LocalDateTime;
import java.time.Duration;

/**
 * Represents a current account, which is a type of bank account.
 * Extends {@link Account}.
 */
public class CurrentAccount extends Account {
    // 上一次更新余额的时间
    public LocalDateTime lastSettlementDate;
    // interestRate 是年化利率；interestRatePerDay是每天的利率 需要开根号
    private double interestRatePerDay;

    /**
     * Calculates the daily interest rate based on the annual interest rate.
     *
     * @param interestRate_ the annual interest rate
     * @return the daily interest rate
     */
    public static double calculateDailyInterestRate(double interestRate_) {
        return Math.pow(1 + interestRate_, 1.0 / 365) - 1;
    }

    /**
     * Constructs a new CurrentAccount object with the specified parameters.
     *
     * @param accountName          the name of the account, must not be null or empty
     * @param initialBalance       the initial balance of the account, must be non-negative
     * @param interestRate         the annual interest rate of the account, must be >= -1
     * @param timeLapseCoefficient the time lapse coefficient, must be positive
     */
    public CurrentAccount(String accountName, double initialBalance, double interestRate, double timeLapseCoefficient) {
        super(accountName, initialBalance, interestRate, timeLapseCoefficient);
        this.interestRatePerDay = calculateDailyInterestRate(interestRate);  // Calculate the daily interest rate
        this.lastSettlementDate = createDateTime;  // Assume last settlement is at creation time
    }

    @Override
    public boolean checkDepositChangeability() {
        // 活期账户一直可以取钱
        return true;
    }

    /**
     * Gets the last settlement date of the account.
     *
     * @return the last settlement date
     */
    public LocalDateTime getLastSettlementDate() {
        return lastSettlementDate;
    }

    /**
     * Gets the daily interest rate of the account.
     *
     * @return the daily interest rate
     */
    public double getInterestRatePerDay() {
        return interestRatePerDay;
    }

    /**
     * Updates the interest of the account and returns the added interest amount.
     *
     * @return the added interest amount
     */
    public double updateInterest() {
        double currentBalance = this.balance;
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
        return balance - currentBalance;
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

