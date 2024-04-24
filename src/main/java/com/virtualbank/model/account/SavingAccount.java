package com.virtualbank.model.account;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public class SavingAccount extends Account {
    private boolean isMatured; // 标记账户是否已到期
    private final LocalDateTime maturityDateTime;
    private final Period termPeriod; // 使用Period来表示存款期限

    public SavingAccount(String accountName, double initialBalance, double interestRate,
                         double timeLapseCoefficient, Period termPeriod) {
        super(accountName, initialBalance, interestRate, timeLapseCoefficient);
        this.termPeriod = termPeriod;
        this.maturityDateTime = createDateTime.plus(termPeriod);
        this.isMatured = false;
    }


    public boolean isMatured() {
        return isMatured;
    }

    public LocalDateTime getMaturityDateTime() {
        return maturityDateTime;
    }

    public Period getTermPeriod() {
        return termPeriod;
    }

    // 获取当前时间然后和到期的时间比较，考虑时间流逝系数
    public boolean checkMaturity() {
        LocalDateTime now = LocalDateTime.now();
        // 计算自账户创建以来的实际时间差
        Duration realTimeElapsed = Duration.between(createDateTime, now);
        // 将实际时间差乘以时间流逝系数
        long simulatedSeconds = (long) (realTimeElapsed.getSeconds() * timeLapseCoefficient);
        // 创建模拟的当前时间
        LocalDateTime simulatedCurrentTime = createDateTime.plusSeconds(simulatedSeconds);

        // 检查模拟的当前时间是否在到期时间之后
        return simulatedCurrentTime.isAfter(maturityDateTime);
    }

    private double calculateMaturityAmount(double balance, double interestRate, Period termPeriod) {
        // 假设利率以年为单位
        int years = termPeriod.getYears();
        // 复利
        return balance * Math.pow((1 + interestRate), years);
        // 简单利率
//        return balance * (1 + interestRate *  years);
    }

    @Override
    public void updateInterest() {
        // 检查账户是否已经到期并相应更新
        if (checkMaturity() && !isMatured) {
            this.balance = calculateMaturityAmount(balance, interestRate, termPeriod);
            this.isMatured = true;
        }
    }


    @Override
    public String toString() {
        return "SavingAccount{" +
                "isMatured=" + isMatured +
                ", maturityDateTime=" + maturityDateTime +
                ", termPeriod=" + termPeriod +
                ", accountName='" + accountName + '\'' +
                ", balance=" + balance +
                ", interestRate=" + interestRate +
                ", createDateTime=" + createDateTime +
                ", timeLapseCoefficient=" + timeLapseCoefficient +
                '}';
    }
}
