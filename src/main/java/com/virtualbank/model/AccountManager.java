package com.virtualbank.model;

import com.virtualbank.model.account.*; // all account

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


import java.io.Serializable;
import java.time.Period;
import java.util.*;

public class AccountManager implements Serializable {
    private Map<UUID, Account> accounts;
    private UUID piggyUuid;
    private History logger;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);


    public AccountManager() {
        // 创建存钱罐账户
        Account piggyBank = new PiggyBank("Piggy Bank", 0);
        this.piggyUuid = piggyBank.getUuid();
        this.accounts = new HashMap<>();
        this.accounts.put(piggyBank.getUuid(), piggyBank);
        this.logger = new History(); // 假设 History 类可接受 Account 参数
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }


    public Map<UUID, Account> getAccounts() {
        return accounts;
    }

    public UUID getPiggyUuid() {
        return piggyUuid;
    }

    private void checkAccountExistenceChangeability(Account account) {
        // 检查账户是否存在
        if (account == null) {
            throw new IllegalArgumentException("Receiving account with UUID " + account + " not found.");
        }

        // 检查账户是否允许转账操作
        if (!account.checkDepositChangeability()) {
            throw new IllegalArgumentException("Sending account with UUID " + account + " does not permit transfers.");
        }
    }

    private void checkAmountPositive(double amount) {
        // 检查转账金额是否合法
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }
    }


    // 以下是所有账户的功能 都可以拆分成一个账户的取钱和一个账户的存钱的组合
    // 所以我们先写一个抽象的存钱和取钱的方式
    // 后面不同函数的名称只是调用这个抽象函数 (通过传入不同的OperationType)
    private void withdrawWithType(AccountOperationType accountOperationType, UUID id, double amount, String description) {
        Account account = accounts.get(id);
        double oldBalance = account.getBalance();

        checkAccountExistenceChangeability(account);
        checkAmountPositive(amount);
        // 检查发送账户余额是否足够
        if (!account.checkDepositSufficiency(amount)) {
            throw new IllegalArgumentException("Sending account with UUID " + id + " does not have sufficient funds.");
        }

        account.withdraw(amount);

        logger.recordOperation(accountOperationType, id, amount, description);

        // notify balance have changed
        pcs.firePropertyChange("balance", oldBalance, account.getBalance());
    }

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
    public void transfer(UUID from, UUID to, double amount, String description) {
        // 检查是否试图自我转账
        if (from.equals(to)) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }

        withdrawWithType(AccountOperationType.TRANSFER_FROM, from, amount, description);
        depositWithType(AccountOperationType.TRANSFER_TO, to, amount, description);

    }


    // 存钱
    public void save(UUID to, double amount, String description) {
        depositWithType(AccountOperationType.SAVE, to, amount, description);
    }


    // 取钱
    public void consume(UUID from, double amount, String description) {
        withdrawWithType(AccountOperationType.CONSUME, from, amount, description);
    }

    // 奖励钱 prize
    public void prize(UUID to, double amount, String description) {
        depositWithType(AccountOperationType.PRIZE, to, amount, description);
    }


    // 更新利息，适用于所有账户类型
    public void updateAllInterests() {
        for (Account account : accounts.values()) {
            double interestAmount = account.updateInterest();
            logger.recordOperation(AccountOperationType.INTEREST, account.getUuid(), interestAmount, "auto calculated");
        }
    }


    // 创建新的卡的时候没有转账记录
    public UUID addCurrentAccount(String accountName, double interestRate, double timeLapseCoefficient) {
        Account account;
        account = new CurrentAccount(accountName, 0, interestRate, timeLapseCoefficient);
        accounts.put(account.getUuid(), account);
        pcs.firePropertyChange("current_accounts", null, account);
        return account.getUuid();
    }

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

    public double getTotalBalance() {
        double totalBalance = 0.0;
        for (Account account : accounts.values()) {
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }


}
