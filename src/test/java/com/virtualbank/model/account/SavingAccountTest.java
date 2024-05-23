package com.virtualbank.model.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

class SavingAccountTest {
    private SavingAccount savingAccount;

    @BeforeEach
    void setUp() {
        savingAccount = new SavingAccount("Savings", 1000.0, 0.05, 1, Period.ofYears(1));
    }

    @Test
    void testInitialConditions() {
        assertEquals(1000.0, savingAccount.getBalance());
        assertEquals("Savings", savingAccount.getAccountName());
        assertEquals(0.05, savingAccount.getInterestRate());
        assertFalse(savingAccount.isMatured());
        assertNotNull(savingAccount.getMaturityDateTime());
    }

    @Test
    void testCheckMaturityBeforeMaturity() {
        assertFalse(savingAccount.checkMaturity(), "Account should not be matured yet.");
    }


    @Test
    void testUpdateInterestPostMaturity() {
        // test for the case where the account is already matured
        savingAccount.simulateTimePassage(savingAccount.getTermPeriod());
        double interestGained = savingAccount.updateInterest();
        assertTrue(savingAccount.isMatured(), "Account should be marked as matured.");
        assertFalse(interestGained > 0, "Interest should be added to the account after maturity.");
    }

    @Test
    void testWithdrawPostMaturity() {
        // test for the case where the account is already matured
        savingAccount.simulateTimePassage(savingAccount.getTermPeriod());
        savingAccount.updateInterest(); // 确保计算利息
        assertDoesNotThrow(() -> savingAccount.withdraw(950.0), "Should allow withdrawal after maturity.");
    }

    @Test
    void testToString() {
        String result = savingAccount.toString();
        assertTrue(result.contains("SavingAccount"));
        assertTrue(result.contains("balance=1000.0"));
    }
}
