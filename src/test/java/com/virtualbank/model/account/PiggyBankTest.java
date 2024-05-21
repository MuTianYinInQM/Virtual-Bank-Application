package com.virtualbank.model.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PiggyBankTest {
    private PiggyBank piggyBank;

    @BeforeEach
    void setUp() {
        piggyBank = new PiggyBank("ChildPiggyBank", 100.0);  // 初始余额设置为100
    }

    @Test
    void testInitialConditions() {
        assertEquals(100.0, piggyBank.getBalance());
        assertEquals("ChildPiggyBank", piggyBank.getAccountName());
        assertEquals(0, piggyBank.getInterestRate());
        assertTrue(piggyBank.checkDepositChangeability());
    }

    @Test
    void testDeposit() {
        piggyBank.deposit(50.0);
        assertEquals(150.0, piggyBank.getBalance(), "Balance should increase by the deposit amount.");
    }

    @Test
    void testWithdraw() {
        piggyBank.withdraw(30.0);
        assertEquals(70.0, piggyBank.getBalance(), "Balance should decrease by the withdrawal amount.");
    }

    @Test
    void testWithdrawBeyondBalance() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> piggyBank.withdraw(150.0));
        assertEquals("Insufficient funds", exception.getMessage());
    }

    @Test
    void testDepositZeroOrNegativeAmount() {
        Exception negative = assertThrows(IllegalArgumentException.class, () -> piggyBank.deposit(-10.0));
        assertEquals("Deposit amount must be positive", negative.getMessage());

        Exception zero = assertThrows(IllegalArgumentException.class, () -> piggyBank.deposit(0));
        assertEquals("Deposit amount must be positive", zero.getMessage());
    }

    @Test
    void testUpdateInterest() {
        double interest = piggyBank.updateInterest();
        assertEquals(0.0, interest, "Interest for PiggyBank should always be 0.");
    }

    @Test
    void testToString() {
        String result = piggyBank.toString();
        assertTrue(result.contains("PiggyBank"));
        assertTrue(result.contains("balance=100.0"));
    }
}
