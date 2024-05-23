package com.virtualbank.respository;

import com.virtualbank.model.AccountManager;
import com.virtualbank.model.account.Account;
import com.virtualbank.repository.AccountManagerSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Period;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountManagerSerializerTest {
    private AccountManager originalAccountManager;
    private String testUsername = "testUser";

    @BeforeEach
    void setUp() {
        originalAccountManager = new AccountManager();
        originalAccountManager.prize(originalAccountManager.getPiggyUuid(), 100, "initial money");
        UUID currentAccountId = originalAccountManager.
                addCurrentAccount("CurrentAccount", 0.01, 24);
        UUID savingAccountId = originalAccountManager.
                addSavingAccount("SavingAccount",
                        50, 0.05, 24, Period.ofYears(1));

        originalAccountManager.save(currentAccountId, 1000, "Initial deposit");


        AccountManagerSerializer.serializeAccountManager(originalAccountManager, testUsername);
    }

    @Test
    void testSerializationAndDeserialization() {
        try {
            AccountManager deserializedAccountManager =
                    AccountManagerSerializer.deserializeAccountManager(testUsername);

            assertNotNull(deserializedAccountManager);
            assertEquals(originalAccountManager.getAccounts().size(), deserializedAccountManager.getAccounts().size());

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
        String filePath = AccountManagerSerializer.DIRECTORY_PATH + File.separator + testUsername;
        File file = new File(filePath);
        if (file.exists()) {
            assertTrue(file.delete(), "Failed to delete test file");
        }
    }
}
