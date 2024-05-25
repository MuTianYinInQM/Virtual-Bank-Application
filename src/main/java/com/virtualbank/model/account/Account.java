package com.virtualbank.model.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
import java.time.LocalDateTime;

/**
 * Abstract class representing a bank account.
 * Implements {@link Serializable} interface for object serialization.
 */
public abstract class Account implements Serializable {
    private final UUID uuid;
    protected String accountName;
    protected double balance;
    protected double interestRate;
    protected final LocalDateTime createDateTime;
    protected double timeLapseCoefficient;

    // Constructor that initializes all fields

    /**
     * Constructor that initializes all fields of the Account.
     *
     * @param accountName          the name of the account, must not be null or empty
     * @param initialBalance       the initial balance of the account, must be non-negative
     * @param interestRate         the interest rate of the account, must be >= -1
     * @param timeLapseCoefficient the time lapse coefficient, must be positive
     * @throws IllegalArgumentException if any of the conditions on the parameters are violated
     */
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

    /**
     * Gets the unique identifier of the account.
     *
     * @return the UUID of the account
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Gets the name of the account.
     *
     * @return the account name
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Gets the balance of the account.
     *
     * @return the balance
     */
    public double getBalance() {
        return balance;
//        BigDecimal bd = new BigDecimal(balance);
//        bd = bd.setScale(2, RoundingMode.HALF_UP);
//        return bd.doubleValue();
    }

    /**
     * Gets the interest rate of the account.
     *
     * @return the interest rate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Gets the creation date and time of the account.
     *
     * @return the creation date and time
     */
    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    /**
     * Gets the time lapse coefficient of the account.
     *
     * @return the time lapse coefficient
     */
    public double getTimeLapseCoefficient() {
        return timeLapseCoefficient;
    }

    /**
     * Sets the interest rate of the account.
     *
     * @param interestRate the new interest rate
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Sets the time lapse coefficient of the account.
     *
     * @param timeLapseCoefficient the new time lapse coefficient
     */
    public void setTimeLapseCoefficient(double timeLapseCoefficient) {
        this.timeLapseCoefficient = timeLapseCoefficient;
    }

    /**
     * Sets the name of the account.
     *
     * @param accountName the new account name
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    // Standard methods for deposit and withdrawal

    /**
     * Deposits a specified amount into the account.
     *
     * @param amount the amount to deposit, must be positive
     * @throws IllegalArgumentException if the amount is not positive
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance += amount;

    }

    // 得到每个函数的是否可以存取钱

    /**
     * Checks if deposits can be made to the account.
     *
     * @return true if deposits can be made, false otherwise
     */
    public abstract boolean checkDepositChangeability();

    // 仅仅检查账户的钱是不是够多 需要配合上面的 checkDepositChangeability 使用

    /**
     * Checks if the account has sufficient balance for a specified amount.
     *
     * @param amount the amount to check
     * @return true if the account has sufficient balance, false otherwise
     */
    public boolean checkDepositSufficiency(double amount) {
        return balance >= amount;
    }

    /**
     * Withdraws a specified amount from the account.
     *
     * @param amount the amount to withdraw, must be positive and less than or equal to the balance
     * @throws IllegalArgumentException if the amount is not positive or exceeds the balance
     */
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
    // 返回 增加的利息的金额

    /**
     * Updates the interest for the account. Must be implemented by subclasses.
     *
     * @return the amount of interest added to the account
     */
    public abstract double updateInterest();

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
