package com.virtualbank.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

import com.virtualbank.model.History;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualbank.model.account.Account;

class HistoryTest {

    private History history;
    private Account account;
    private ObjectMapper mapper;
    private Path path;

    @BeforeEach
    void setUp() throws Exception {
        // Mock the Account and Path dependencies
        account = mock(Account.class);
        mapper = mock(ObjectMapper.class);
        path = mock(Path.class);

        // Stubbing methods
        when(account.getUuid()).thenReturn(UUID.randomUUID());
        when(account.getAccountName()).thenReturn("Test Account");

        // Create a real History object with mocked Account
        history = new History(account);

        // Replace the ObjectMapper with a mocked one
        history.mapper = mapper;
    }

    @Test
    void testRecordTransaction() throws Exception {
        String type = "deposit";
        boolean isCredit = true;
        double amount = 100.0;
        String description = "Test deposit";

        // Call the method under test
        history.recordTransaction(type, isCredit, amount, description);

        // Verify that the transaction list size is incremented
        assertEquals(1, history.transactions.size());

        // Verify the transaction details
        Map<String, Object> recordedTransaction = history.transactions.get(0);
        assertEquals(type, recordedTransaction.get("type"));
        assertEquals(isCredit, recordedTransaction.get("isCredit"));
        assertEquals(amount, recordedTransaction.get("amount"));
        assertEquals(description, recordedTransaction.get("description"));

        // Verify that saveTransactions was called
        verify(mapper).writeValue(any(File.class), eq(history.transactions));
    }
}
