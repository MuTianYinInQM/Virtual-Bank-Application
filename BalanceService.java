package com.virtualbank.service;

import com.virtualbank.model.Account;
import com.virtualbank.model.CurrentAccount;
import com.virtualbank.model.PiggyBank;
import com.virtualbank.model.SavingAccount;
import java.time.LocalDateTime;

public class BalanceService {
    // 构造函数
    public BalanceService() {}

    /**
     * 根据账户类型更新账户余额。
     * @param account 账户对象，可以是 CurrentAccount, PiggyBank, 或 SavingAccount
     * @param amount 金额，正数表示存入，负数表示取出
     * @param transactionType 交易类型（如 "consume", "save", "transfer", "interest", "prize"）
     * @param description 交易描述
     * @return 更新后的账户对象
     */
    public Account updateBalance(Account account, double amount, String transactionType, String description) {
        switch (account.getClass().getSimpleName()) {
            case "CurrentAccount":
                return updateCurrentAccountBalance((CurrentAccount) account, amount, transactionType, description);
            case "PiggyBank":
                return updatePiggyBankBalance((PiggyBank) account, amount, transactionType, description);
            case "SavingAccount":
                return updateSavingAccountBalance((SavingAccount) account, amount, transactionType, description);
            default:
                throw new IllegalArgumentException("Unsupported account type");
        }
    }

    // 更新活期账户余额
    private CurrentAccount updateCurrentAccountBalance(CurrentAccount account, double amount, String transactionType, String description) {
        // 检查取款操作是否会导致余额为负
        if (amount < 0 && account.getBalance() + amount < 0) {
            throw new IllegalArgumentException("Insufficient funds for withdrawal.");
        }
        account.setBalance(account.getBalance() + amount);
        logTransaction(account, amount, transactionType, description);
        return account;
    }

    // 更新存钱罐余额
    private PiggyBank updatePiggyBankBalance(PiggyBank account, double amount, String transactionType, String description) {
        // 检查取款操作是否会导致余额为负
        if (amount < 0 && account.getBalance() + amount < 0) {
            throw new IllegalArgumentException("Insufficient funds for withdrawal.");
        }
        account.setBalance(account.getBalance() + amount);
        logTransaction(account, amount, transactionType, description);
        return account;
    }

    // 更新定期账户余额
    private SavingAccount updateSavingAccountBalance(SavingAccount account, double amount, String transactionType, String description) {
        if (transactionType.equals("transfer") && amount < 0 && account.getBalance() + amount < 0) {
            throw new IllegalArgumentException("Insufficient funds for withdrawal");
        }
        account.setBalance(account.getBalance() + amount);
        return account;
    }



    // 计算活期账户的利息
    public double calculateInterest(CurrentAccount account) {
        LocalDateTime now = LocalDateTime.now();
        // 判断是否已经足够一天来计算利息
        if (now.minusDays(1).isBefore(account.getLastInterestDate())) {
            return 0;  // 如果时间不足一天，则返回0
        }
        double interest = account.getBalance() * 0.005; // 计算一个小时0.5%的利息（虚拟）
        account.setLastInterestDate(now);
        return interest;
    }

    // 计算定期账户到期时的总利息
    public double calculateMaturityInterest(SavingAccount account) {
        double interestRate = account.getTerm() * 0.03; // 日利率3% （虚拟）
        double interest = account.getBalance() * interestRate;
        return interest;
    }

    // 记录交易日志 之后完成交易历史记录模块再补充
    private void logTransaction(Account account, double amount, String transactionType, String description) {
        LocalDateTime transactionTime = LocalDateTime.now();
        System.out.println("Transaction Log: Account ID=" + account.getId() +
                ", Type=" + transactionType +
                ", Amount=" + amount +
                ", New Balance=" + account.getBalance() +
                ", Description=" + description +
                ", Time=" + transactionTime);
    }
}
