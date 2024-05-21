package com.virtualbank.model.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CurrentAccountTest {

    private CurrentAccount account;

    @BeforeEach
    void setUp() {
        account = new CurrentAccount("TestAccount", 1000.0, 0.05, 1.0);  // 5% annual interest rate
    }

    @Test
    void testInitialConditions() {
        assertEquals(1000.0, account.getBalance());
        assertEquals("TestAccount", account.getAccountName());
        assertNotNull(account.getLastSettlementDate());
        assertEquals(0.05, account.getInterestRate());
        assertTrue(account.checkDepositChangeability());
    }

    @Test
    void testCalculateDailyInterestRate() {
        // Directly using the static method to test its accuracy
        double dailyRate = CurrentAccount.calculateDailyInterestRate(0.05);  // Annual rate of 5%
        assertTrue(dailyRate > 0);
    }

    @Test
    void testDeposit() {
        account.deposit(500.0);
        assertEquals(1500.0, account.getBalance());
    }

    @Test
    void testWithdraw() {
        account.withdraw(500.0);
        assertEquals(500.0, account.getBalance());
    }

    @Test
    void testUpdateInterest() {
        // Assuming the account was created at the time of the test setup and 10 days have passed
        LocalDateTime tenDaysLater = account.getCreateDateTime().plusDays(10);
        // Mocking current time to 10 days later
        account.lastSettlementDate = account.getLastSettlementDate().minusDays(10);

        double interestGained = account.updateInterest();
        assertTrue(interestGained > 0);
        assertTrue(account.getBalance() > 1000.0);  // Check if the balance has increased due to interest
    }

    @Test
    void testInterestAccumulationOverTime() {
        // Simulate a year's passage to test the interest accumulation
        account.lastSettlementDate = account.getLastSettlementDate().minusYears(1);
        account.updateInterest();
        assertTrue(account.getBalance() > 1000.0);  // Balance should definitely be higher after a year at 5% interest
    }

    @Test
    void testToString() {
        String result = account.toString();
        assertNotNull(result);
        assertTrue(result.contains("CurrentAccount"));
    }
}
