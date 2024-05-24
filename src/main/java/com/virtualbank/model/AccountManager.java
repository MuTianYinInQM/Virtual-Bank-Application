package com.virtualbank.model;

import com.virtualbank.model.account.*; // all account

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


import java.io.Serializable;
import java.time.Period;
import java.util.*;

/**
 * Manages bank accounts and provides operations such as transfer, save, consume, and prize.
 * Implements {@link Serializable} interface for object serialization.
 */
public class AccountManager implements Serializable {
    private Map<UUID, Account> accounts;
    private UUID piggyUuid;
    private History logger;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * Constructs a new AccountManager with initial configurations.
     * Creates a piggy bank account and initializes the account map and logger.
     */
    public AccountManager() {
        // 创建存钱罐账户
        Account piggyBank = new PiggyBank("Piggy Bank", 0);
        this.piggyUuid = piggyBank.getUuid();
        this.accounts = new HashMap<>();
        this.accounts.put(piggyBank.getUuid(), piggyBank);
        this.logger = new History(); // 假设 History 类可接受 Account 参数
    }

    /**
     * Adds a property change listener to this AccountManager.
     *
     * @param listener the property change listener to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener from this AccountManager.
     *
     * @param listener the property change listener to be removed
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    /**
     * Gets all accounts managed by this AccountManager.
     *
     * @return a map of UUID to Account objects
     */
    public Map<UUID, Account> getAccounts() {
        return accounts;
    }

    /**
     * Gets the UUID of the piggy bank account.
     *
     * @return the UUID of the piggy bank account
     */
    public UUID getPiggyUuid() {
        return piggyUuid;
    }

    /**
     * Checks the existence and changeability of an account.
     *
     * @param account the account to check
     * @throws IllegalArgumentException if the account is null or does not permit transfers
     */
    private void checkAccountExistenceChangeability(Account account) {
        // 检查账户是否存在
        if (account == null) {
            throw new IllegalArgumentException("Account with UUID " + account + " not found.");
        }

        // 检查账户是否允许转账操作
        if (!account.checkDepositChangeability()) {
            throw new IllegalArgumentException("Account with UUID " + account + " does not permit transfers.");
        }
    }

    /**
     * Checks if the given amount is positive.
     *
     * @param amount the amount to check
     * @throws IllegalArgumentException if the amount is not positive
     */
    private void checkAmountPositive(double amount) {
        // 检查转账金额是否合法
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }
    }


    // 以下是所有账户的功能 都可以拆分成一个账户的取钱和一个账户的存钱的组合
    // 所以我们先写一个抽象的存钱和取钱的方式
    // 后面不同函数的名称只是调用这个抽象函数 (通过传入不同的OperationType)

    /**
     * Withdraws money from an account with the specified operation type, amount, and description.
     *
     * @param accountOperationType the type of account operation
     * @param id                   the UUID of the account
     * @param amount               the amount to withdraw
     * @param description          the description of the withdrawal
     * @throws IllegalArgumentException if the account does not exist, does not permit transfers, or does not have sufficient funds
     */
    private void withdrawWithType(AccountOperationType accountOperationType, UUID id, double amount, String description) {
        Account account = accounts.get(id);
        double oldBalance = account.getBalance();

        checkAccountExistenceChangeability(account);
        checkAmountPositive(amount);
        // 检查发送账户余额是否足够
        if (!account.checkDepositSufficiency(amount)) {
            throw new IllegalArgumentException("Account with UUID " + id + " does not have sufficient funds.");
        }

        account.withdraw(amount);

        logger.recordOperation(accountOperationType, id, amount, description);

        // notify balance have changed
        pcs.firePropertyChange("balance", oldBalance, account.getBalance());
    }

    /**
     * Deposits money into an account with the specified operation type, amount, and description.
     *
     * @param accountOperationType the type of account operation
     * @param id                   the UUID of the account
     * @param amount               the amount to deposit
     * @param description          the description of the deposit
     * @throws IllegalArgumentException if the account does not exist or does not permit transfers
     */
    private void depositWithType(AccountOperationType accountOperationType, UUID id, double amount, String description) {
        Account account = accounts.get(id);
        double oldBalance = account.getBalance();

        checkAccountExistenceChangeability(account);
        checkAmountPositive(amount);

        account.deposit(amount);

        logger.recordOperation(accountOperationType, id, amount, description);

        // notify balance have changed
        pcs.firePropertyChange("balance", oldBalance, account.getBalance());
    }


    // 转账功能

    /**
     * Transfers money from one account to another.
     *
     * @param from        the UUID of the account to transfer from
     * @param to          the UUID of the account to transfer to
     * @param amount      the amount to transfer
     * @param description the description of the transfer
     * @throws IllegalArgumentException if attempting to transfer to the same account or if there are insufficient funds
     */
    public void transfer(UUID from, UUID to, double amount, String description) {
        // 检查是否试图自我转账
        if (from.equals(to)) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }

        withdrawWithType(AccountOperationType.TRANSFER_FROM, from, amount, description);
        depositWithType(AccountOperationType.TRANSFER_TO, to, amount, description);

    }


    // 存钱

    /**
     * Deposits money into the specified account.
     *
     * @param to          the UUID of the account to deposit into
     * @param amount      the amount to deposit
     * @param description the description of the deposit
     */
    public void save(UUID to, double amount, String description) {
        depositWithType(AccountOperationType.SAVE, to, amount, description);
    }


    // 取钱

    /**
     * Withdraws money from the specified account.
     *
     * @param from        the UUID of the account to withdraw from
     * @param amount      the amount to withdraw
     * @param description the description of the withdrawal
     * @throws IllegalArgumentException if there are insufficient funds
     */
    public void consume(UUID from, double amount, String description) {
        withdrawWithType(AccountOperationType.CONSUME, from, amount, description);
    }

    // 奖励钱 prize

    /**
     * Deposits money into the specified account as a prize.
     *
     * @param to          the UUID of the account to deposit into
     * @param amount      the amount to deposit
     * @param description the description of the prize
     */
    public void prize(UUID to, double amount, String description) {
        depositWithType(AccountOperationType.PRIZE, to, amount, description);
    }


    // 更新利息，适用于所有账户类型

    /**
     * Updates the interest for all accounts.
     * This method is applicable to all account types.
     */
    public void updateAllInterests() {
        for (Account account : accounts.values()) {
            double interestAmount = account.updateInterest();
            logger.recordOperation(AccountOperationType.INTEREST, account.getUuid(), interestAmount, "auto calculated");
        }
    }


    // 创建新的卡的时候没有转账记录

    /**
     * Adds a new current account with the specified name, interest rate, and time lapse coefficient.
     *
     * @param accountName          the name of the account
     * @param interestRate         the annual interest rate of the account
     * @param timeLapseCoefficient the time lapse coefficient of the account
     * @return the UUID of the newly created account
     */
    public UUID addCurrentAccount(String accountName, double interestRate, double timeLapseCoefficient) {
        Account account;
        account = new CurrentAccount(accountName, 0, interestRate, timeLapseCoefficient);
        accounts.put(account.getUuid(), account);
        pcs.firePropertyChange("current_accounts", null, account);
        return account.getUuid();
    }

    /**
     * Adds a new saving account with the specified name, initial balance, interest rate, time lapse coefficient, and term period.
     * The initial balance is transferred from the piggy bank account.
     *
     * @param accountName          the name of the account
     * @param initialBalance       the initial balance of the account
     * @param interestRate         the annual interest rate of the account
     * @param timeLapseCoefficient the time lapse coefficient of the account
     * @param termPeriod           the term period of the account
     * @return the UUID of the newly created account
     */

    public UUID addSavingAccount(String accountName, double initialBalance,
                                 double interestRate, double timeLapseCoefficient, Period termPeriod) {
        // 要求先从存钱罐账户里面拿出来 initialBalance 这么多的钱
        // 然后检查是否可行后在执行
        Account account;
        account = new SavingAccount(accountName, initialBalance, interestRate, timeLapseCoefficient, termPeriod);
        withdrawWithType(AccountOperationType.TRANSFER_FROM, piggyUuid, initialBalance,
                "transfer to a Saving Account with uuid is account.getUuid()");
        accounts.put(account.getUuid(), account);
        logger.recordOperation(AccountOperationType.INITIAL_SAVE, account.getUuid(), initialBalance,
                "create saving account");
        pcs.firePropertyChange("saving_accounts", null, account);
        return account.getUuid();
    }

    /**
     * Removes the account with the specified UUID.
     *
     * @param uuid the UUID of the account to remove
     * @throws IllegalArgumentException if the account does not exist, is a piggy bank account, or has a non-zero balance
     */
    public void removeAccount(UUID uuid) {
        Account account = accounts.get(uuid);

        // 检查账户是否存在
        if (account == null) {
            throw new IllegalArgumentException("Account with UUID " + uuid + " does not exist.");
        }

        // 检查是否是存钱罐账户
        if (account instanceof PiggyBank) {
            throw new IllegalArgumentException("PiggyBank account cannot be removed.");
        }

        // 检查账户余额是否为0
        if (account.getBalance() != 0) {
            throw new IllegalArgumentException("Account with UUID " + uuid + " cannot be removed because it has a non-zero balance.");
        }

        // 移除账户
        accounts.remove(uuid);

        // notify
        pcs.firePropertyChange("accounts", account, null);
    }

    /**
     * Calculates and returns the total balance of all accounts managed by this AccountManager.
     *
     * @return the total balance of all accounts
     */
    public double getTotalBalance() {
        double totalBalance = 0.0;
        for (Account account : accounts.values()) {
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }


}
