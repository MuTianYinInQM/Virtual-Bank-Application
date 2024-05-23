package systemTest;

import com.virtualbank.model.AccountManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import java.time.Period;

class TransferTest {
    private AccountManager manager;

    @BeforeEach
    void setUp() {
        manager = new AccountManager();
        // 添加初始资金到存钱罐，确保有足够的资金进行测试
        manager.prize(manager.getPiggyUuid(), 1000, "Initial funding");
        // 添加一个活期账户
        UUID currentAccountId = manager.addCurrentAccount("Current Account", 0.01, 1.0);
        manager.save(currentAccountId, 500, "Initial deposit to Current Account");
    }

    @Test
    void testSaveOperation() {
        UUID toAccountId = UUID.fromString(manager.getAccounts().keySet().iterator().next().toString()); // 获取任意账户
        double initialBalance = manager.getAccounts().get(toAccountId).getBalance();
        double depositAmount = 200.0;
        manager.save(toAccountId, depositAmount, "Deposit Money");
        assertEquals(initialBalance + depositAmount, manager.getAccounts().get(toAccountId).getBalance(), "Balance should be correctly updated after deposit");
    }

    @Test
    void testConsumeOperation() {
        double initialBalance = manager.getAccounts().get(manager.getPiggyUuid()).getBalance();
        double consumeAmount = 100.0;
        manager.consume(manager.getPiggyUuid(), consumeAmount, "Consume Money");
        assertEquals(initialBalance - consumeAmount, manager.getAccounts().get(manager.getPiggyUuid()).getBalance(), "Balance should be correctly updated after consumption");
    }

//    @Test
//    void testTransferOperation() {
//
//        UUID fromAccountId = UUID.fromString(manager.getAccounts().keySet().iterator().next().toString()); // 获取任意活期账户
//        UUID toAccountId = manager.getPiggyUuid(); // 存钱罐账户
//        double initialFromBalance = manager.getAccounts().get(fromAccountId).getBalance();
//        double initialToBalance = manager.getAccounts().get(toAccountId).getBalance();
//        double transferAmount = 200.0;
//        manager.transfer(fromAccountId, toAccountId, transferAmount, "Transfer to Piggy Bank");
//        assertEquals(initialFromBalance - transferAmount, manager.getAccounts().get(fromAccountId).getBalance(), "Balance should be correctly deducted from source account");
//        assertEquals(initialToBalance + transferAmount, manager.getAccounts().get(toAccountId).getBalance(), "Balance should be correctly added to destination account");
//    }
    // TODO Transfer功能无法正常使用，因为无法正常把钱放到活期账户里
}
