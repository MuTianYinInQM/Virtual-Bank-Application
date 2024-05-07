package com.virtualbank.respository;

import com.virtualbank.model.AccountManager;
import com.virtualbank.model.account.Account;
import com.virtualbank.repository.AccountManagerSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Period;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountManagerSerializerTest {
    private AccountManager originalAccountManager;
    private String testUsername = "testUser";

    @BeforeEach
    void setUp() {
        // 初始化AccountManager对象
        originalAccountManager = new AccountManager();
        originalAccountManager.prize(originalAccountManager.getPiggyUuid(), 100, "initial money");
        // 添加一个活期账户和一个定期账户，为了测试
        UUID currentAccountId = originalAccountManager.
                addCurrentAccount("CurrentAccount", 0.01, 24);
        UUID savingAccountId = originalAccountManager.
                addSavingAccount("SavingAccount",
                        50, 0.05, 24, Period.ofYears(1));

        // 对这些账户进行操作，存钱和奖励
        originalAccountManager.save(currentAccountId, 1000, "Initial deposit");


        // 序列化AccountManager
        AccountManagerSerializer.serializeAccountManager(originalAccountManager, testUsername);
    }

    @Test
    void testSerializationAndDeserialization() {
        try {
            // 反序列化AccountManager
            AccountManager deserializedAccountManager =
                    AccountManagerSerializer.deserializeAccountManager(testUsername);

            // 进行断言测试
            assertNotNull(deserializedAccountManager);
            assertEquals(originalAccountManager.getAccounts().size(), deserializedAccountManager.getAccounts().size());

            // 测试账户的具体操作结果是否一致
            originalAccountManager.getAccounts().forEach((id, originalAccount) -> {
                Account deserializedAccount = deserializedAccountManager.getAccounts().get(id);
                assertNotNull(deserializedAccount);
                assertEquals(originalAccount.getBalance(), deserializedAccount.getBalance());
            });
        } catch (IOException | ClassNotFoundException e) {
            fail("Deserialization failed with exception: " + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        // 删除测试生成的文件
        String filePath = AccountManagerSerializer.DIRECTORY_PATH + File.separator + testUsername;
        File file = new File(filePath);
        if (file.exists()) {
            assertTrue(file.delete(), "Failed to delete test file");
        }
    }
}
