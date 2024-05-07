package com.virtualbank.model;

import com.virtualbank.model.account.*; // all account
import com.virtualbank.model.OperationType;
import com.virtualbank.model.History;

import java.io.Serializable;
import java.time.Period;
import java.util.*;

public class AccountManager implements Serializable {
    private Map<UUID, Account> accounts;
    private History logger;

    public AccountManager() {
        // 创建存钱罐账户
        Account piggyBank = new PiggyBank("Piggy Bank", 0);
        this.accounts = new HashMap<>();
        this.accounts.put(piggyBank.getUuid(), piggyBank);
        this.logger = new History(); // 假设 History 类可接受 Account 参数
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
    private void withdrawWithType(OperationType operationType, UUID id, double amount, String description) {
        Account account = accounts.get(id);

        checkAccountExistenceChangeability(account);
        checkAmountPositive(amount);
        // 检查发送账户余额是否足够
        if (!account.checkDepositSufficiency(amount)) {
            throw new IllegalArgumentException("Sending account with UUID " + id + " does not have sufficient funds.");
        }

        account.withdraw(amount);

        logger.recordOperation(operationType, id, amount, description);
    }

    private void depositWithType(OperationType operationType, UUID id, double amount, String description) {
        Account account = accounts.get(id);

        checkAccountExistenceChangeability(account);
        checkAmountPositive(amount);

        account.deposit(amount);

        logger.recordOperation(operationType, id, amount, description);
    }


    // 转账功能
    public void transfer(UUID from, UUID to, double amount, String description) {
        // 检查是否试图自我转账
        if (from.equals(to)) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }

        withdrawWithType(OperationType.TRANSFER_FROM, from, amount, description);
        depositWithType(OperationType.TRANSFER_TO, to, amount, description);

    }


    // 存钱
    public void save(UUID to, double amount, String description) {
        depositWithType(OperationType.SAVE, to, amount, description);
    }


    // 取钱
    public void consume(UUID from, double amount, String description) {
        withdrawWithType(OperationType.CONSUME, from, amount, description);
    }

    // 奖励钱 prize
    public void prize(UUID to, double amount, String description) {
        depositWithType(OperationType.PRIZE, to, amount, description);
    }


    // 更新利息，适用于所有账户类型
    public void updateAllInterests() {
        for (Account account : accounts.values()) {
            double interestAmount = account.updateInterest();
            logger.recordOperation(OperationType.INTEREST, account.getUuid(), interestAmount, "auto calculated");
        }
    }

    // TODO 应该需要可以指派利率 时间 流逝系数的方式 (更上层应该做选择)
    public UUID addCurrentAccount(String type, String name, Period termPeriod) {
        Account account;

        switch (type) {
            case "Current":
                // 创建活期账户实例
                account = new CurrentAccount(name, 0, 0.01, 24); // 假定活期利率为1%，时间流逝系数为24
                break;
            case "Saving":
                // 创建定期账户实例
                account = new SavingAccount(name, 0, 0.05, 365, termPeriod); // 定期利率为5%，时间流逝系数为365
                break;
            default:
                throw new IllegalArgumentException("Unsupported account type: " + type);
        }

        accounts.put(account.getUuid(), account);
        return account.getUuid();
    }

    public UUID addCurrentAccount(String accountName, double interestRate, double timeLapseCoefficient) {
        Account account;
        account = new CurrentAccount(accountName, 0, interestRate, timeLapseCoefficient);
        accounts.put(account.getUuid(), account);
        return account.getUuid();
    }


    public UUID addSavingAccount(String accountName, double interestRate, double timeLapseCoefficient, Period termPeriod) {
        Account account;
        account = new SavingAccount(accountName, 0, interestRate, timeLapseCoefficient, termPeriod);
        accounts.put(account.getUuid(), account);
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
    }


}
