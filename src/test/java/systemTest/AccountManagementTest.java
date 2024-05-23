package systemTest;

import com.virtualbank.model.AccountManager;
import com.virtualbank.model.History;
import com.virtualbank.model.account.Account;
import com.virtualbank.model.account.CurrentAccount;
import com.virtualbank.model.account.SavingAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Period;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class AccountManagerTests {
    private AccountManager manager;
    private History history;

    @BeforeEach
    void setup() {
        manager = new AccountManager();
        history = new History();
    }

    @Test
    void testCreateCurrentAccount() {
        UUID accountId = manager.addCurrentAccount("Test Current Account", 0.01, 1.0);
        assertNotNull(manager.getAccounts().get(accountId));
        assertTrue(manager.getAccounts().get(accountId) instanceof CurrentAccount);
    }

    @Test
    void testCreateSavingAccount() {
        // 确保存钱罐账户有足够的资金
        double initialBalance = 100.0; // 假设存钱罐的初始资金
        manager.getAccounts().get(manager.getPiggyUuid()).deposit(initialBalance);

        // 尝试创建定期账户
        UUID savingAccountId = manager.addSavingAccount("Test Saving Account", initialBalance, 0.03, 1.0, Period.ofYears(1));
        assertNotNull(manager.getAccounts().get(savingAccountId));
        assertTrue(manager.getAccounts().get(savingAccountId) instanceof SavingAccount);
    }

    @Test
    void testDeleteAccount() {
        UUID accountId = manager.addCurrentAccount("Test Account for Deletion", 0.01, 1.0);
        manager.getAccounts().get(accountId).deposit(100);
        assertThrows(IllegalArgumentException.class, () -> manager.removeAccount(accountId)); // Attempt to remove non-empty account
        manager.getAccounts().get(accountId).withdraw(100); // Clear the account balance
        assertDoesNotThrow(() -> manager.removeAccount(accountId)); // Now should succeed
    }

    @Test
    void testAccessHistory() {
        UUID accountId = manager.addCurrentAccount("Test Account History", 0.01, 1.0);
        manager.save(accountId, 50.0, "Deposit for testing");
        manager.consume(accountId, 20.0, "Withdrawal for testing");
        // We need to check the history, assuming History class has a method to retrieve records

        // Check if the history is correct
    }
}
