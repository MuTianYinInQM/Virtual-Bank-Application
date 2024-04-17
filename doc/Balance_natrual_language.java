package com.virtualbank.service;

import com.virtualbank.model.Account;
import com.virtualbank.model.CurrentAccount;
import com.virtualbank.model.PiggyBank;
import com.virtualbank.model.SavingAccount;

// 负责余额更新，利息计算
public class BalanceService {
    public BalanceService() {}

    /**
     * 更新账户余额，区分不同类型的账户进行处理。
     * @param account 账户对象
     * @param amount 转账金额（正数为转入，负数为转出）
     * @param transactionType 转账类型（consume, save, transfer, interest, prize）
     * @param description 交易描述
     * @return 更新后的账户对象
     */
    public Account updateBalance(Account account, double amount, String transactionType, String description) {
        // 首先判断账户类型，根据不同的账户类型执行相应的方法

        // 1.活期账户

        //2. 存钱罐

        //3. 定期账户

    }

    public CurrentAccount updateCurrentAccountBalance() {
        // 传入账户对象，金额数，交易类型
        // currentaccount可有的交易类型：save, transfer, interest
        // 从存钱罐和其他活期账户转入的钱的交易，为save类型，从本账户转出的交易，为transfer，获得的利息，为interest
        // 金额数自己带有符号，不用根据类型判断，只管操作balance变量（简单的加减法）
        // 将金额数，交易类型，交易时间（系统时间）传给历史记录模块
        // 返回更新完余额的账户对象
    }

    public PiggyBank updatePiggyBankBalance() {
        // 传入账户对象，金额数，交易类型
        // 可有的交易类型：save, transfer, consume, prize
        // save为从其他账户（定期活期）里取出的钱存入存钱罐，transfer为把存钱罐里的钱转到其他账户
        // consume为消费，花钱，prize为任务奖励的钱直接进入存钱罐
        // 金额数自己带有符号，不用根据类型判断，只管操作balance变量（简单的加减法）
        // 将金额数，交易类型，交易时间（系统时间）传给历史记录模块
        // 返回更新完余额的存钱罐对象

    }

    public SavingAccount updateSavingAccountBalance() {
        // 传入账户对象，金额数，交易类型
        // 可有的交易类型：save, transfer
        // save为在创建账户时确定往里存钱的数额
        // transfer为定期账户到期后取出的本金+利息
        // 金额数自己带有符号，不用根据类型判断，只管操作balance变量（简单的加减法）
        // 不用与历史记录模块交互
        // 返回更新完余额的存钱罐对象
    }

    // 接下来是利息计算的方法
    // 1. 活期的利息计算，根据固定的公式，0.5%/day，传入账户对象，要返回计算出的利息数
    // 注意，要根据系统时间计算，如果时间不足一个周期（一天），那么直接返回0

    // 2. 定期的利息计算，传入账户对象，根据账户的balance变量，以及账户的存钱时间变量，计算全部的利息
    // 比如：我存了1000块钱，存3个月，总共有9%的利息，那么本方法就计算1000 * 0.09 = 90， 并返回90
    // 利息计算公式根据本金和存钱总时间来规定





}