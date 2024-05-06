package com.virtualbank.model.account;

import java.time.LocalDateTime;

public class PiggyBank extends Account {
    public PiggyBank(String accountName, double initialBalance) {
        super(accountName, initialBalance, 0, 1);  // 设置利率为0
        // PiggyBank 不需要处理利率和到期时间，因此不设置 interestRate 和 maturityDateTime
    }

    @Override
    public boolean checkDepositChangeability() {
        // 存钱罐一直可以取钱
        return true;
    }

    @Override
    public double updateInterest() {
        // 存钱罐不计算利息，因此这个方法不执行任何操作
        return 0;
    }

    // toSting 用父类Account的
}
