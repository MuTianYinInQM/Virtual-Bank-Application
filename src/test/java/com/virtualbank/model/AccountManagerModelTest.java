package com.virtualbank.model;

import java.util.UUID;
import java.time.Period;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountManagerModelTest {
    private AccountManager accountManager;
    private UUID currentAccountId;
    private UUID savingAccountId;

    @BeforeEach
    void setUp() {
        // initial setup
        accountManager = new AccountManager();
        accountManager.prize(accountManager.getPiggyUuid(), 100, "Initial money added to Piggy Bank");
        currentAccountId = accountManager.addCurrentAccount("CurrentAccount", 0.01, 24);
        savingAccountId = accountManager.addSavingAccount("SavingAccount",
                50, 0.05, 24, Period.ofYears(1));
        accountManager.save(currentAccountId, 1000, "Initial deposit to Current Account");
    }

    @Test
    void testAddMoneyToPiggyBank() {
        // test adding money to Piggy Bank
        double initialBalance = accountManager.getAccounts().get(accountManager.getPiggyUuid()).getBalance();
        accountManager.save(accountManager.getPiggyUuid(), 50, "Adding money to Piggy Bank");
        assertEquals(initialBalance + 50, accountManager.getAccounts().get(accountManager.getPiggyUuid()).getBalance());
    }

    @Test
    void testTransferBetweenAccounts() {
        // test transferring money between accounts
        double piggyInitial = accountManager.getAccounts().get(accountManager.getPiggyUuid()).getBalance();
        double currentInitial = accountManager.getAccounts().get(currentAccountId).getBalance();
        accountManager.transfer(accountManager.getPiggyUuid(), currentAccountId, 50, "Transfer from Piggy to Current");
        assertEquals(piggyInitial - 50, accountManager.getAccounts().get(accountManager.getPiggyUuid()).getBalance());
        assertEquals(currentInitial + 50, accountManager.getAccounts().get(currentAccountId).getBalance());
    }

    @Test
    void testAddMoneyToCurrentAccount() {
        // test adding money to Current Account
        double currentInitial = accountManager.getAccounts().get(currentAccountId).getBalance();
        accountManager.save(currentAccountId, 500, "Deposit to Current Account");
        assertEquals(currentInitial + 500, accountManager.getAccounts().get(currentAccountId).getBalance());
    }

    @Test
    void testWithdrawFromCurrentAccount() {
        // test withdrawing money from Current Account
        double currentInitial = accountManager.getAccounts().get(currentAccountId).getBalance();
        accountManager.consume(currentAccountId, 200, "Withdraw from Current Account");
        assertEquals(currentInitial - 200, accountManager.getAccounts().get(currentAccountId).getBalance());
    }

    @Test
    void testAttemptToWithdrawFromSavingAccount() {
        // test attempting to withdraw from Saving Account
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accountManager.consume(savingAccountId, 10, "Attempt to withdraw from Saving Account");
        });
        assertTrue(exception.getMessage().contains("does not permit transfers"));
    }

}
