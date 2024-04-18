package com.virtualbank.service;

import com.virtualbank.model.account.Account;
import com.virtualbank.model.account.CurrentAccount;
import com.virtualbank.model.account.PiggyBank;
import com.virtualbank.model.account.SavingAccount;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class BalanceService {

    private HistoryService historyService;
    // 构造函数
    public BalanceService(HistoryService historyService) {
        this.historyService = historyService;
    }

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

    // 更新活期账户余额并记录交易历史
    private CurrentAccount updateCurrentAccountBalance(CurrentAccount account, double amount, String transactionType, String description) {
        if (amount < 0 && account.getBalance() + amount < 0) {
            throw new IllegalArgumentException("Insufficient funds for withdrawal.");
        }
        account.setBalance(account.getBalance() + amount);
        recordTransaction(account, amount, transactionType, description);
        return account;
    }

    // 更新存钱罐余额
    private PiggyBank updatePiggyBankBalance(PiggyBank account, double amount, String transactionType, String description) {
        // 检查取款操作是否会导致余额为负
        if (amount < 0 && account.getBalance() + amount < 0) {
            throw new IllegalArgumentException("Insufficient funds for withdrawal.");
        }
        account.setBalance(account.getBalance() + amount);
        // 记录交易和添加到历史记录
        recordTransaction(account, amount, transactionType, description);
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
    // 每小时结算一次
    public double calculateInterest(CurrentAccount account) {
        LocalDateTime now = LocalDateTime.now();
        long hoursBetween = ChronoUnit.HOURS.between(account.getLastInterestDate(), now);

        if (hoursBetween >= 1) {
            double hourlyInterestRate = 0.005;  // 每小时的利息率
            double interest = account.getBalance() * hourlyInterestRate * hoursBetween;  // 计算多小时的利息
            account.setLastInterestDate(now);  // 更新利息计算的最后日期
            return interest;
        }

        return 0;  // 如果没有足够的小时过去，不计算利息
    }

    // 计算定期账户到期时的总利息
    public double calculateMaturityInterest(SavingAccount account) {
        double interestRate = account.getTerm() * 0.03; // 日利率3% （虚拟）
        double interest = account.getBalance() * interestRate;
        return interest;
    }

    // 记录交易到历史记录并输出到控制台
    private void recordTransaction(Account account, double amount, String transactionType, String description) {
        LocalDateTime now = LocalDateTime.now();
        // 调用HistoryService来添加新的交易记录
        historyService.addTransaction(account.getId(), amount, transactionType, description);
        // 日志输出交易详情
        logTransaction(account, amount, transactionType, description, now);
    }

    // 现有的 logTransaction 方法需要接收一个 LocalDateTime 参数
    private void logTransaction(Account account, double amount, String transactionType, String description, LocalDateTime transactionTime) {
        System.out.println("Transaction Log: Account ID=" + account.getId() +
                ", Type=" + transactionType +
                ", Amount=" + amount +
                ", New Balance=" + account.getBalance() +
                ", Description=" + description +
                ", Time=" + transactionTime);
    }
}
