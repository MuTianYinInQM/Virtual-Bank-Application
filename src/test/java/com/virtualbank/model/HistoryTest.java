package com.virtualbank.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.mockito.Mockito.*;

class HistoryTest {

    @Mock
    private ObjectMapper mockMapper;
    private History history;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        history = new History();
        history.mapper = mockMapper; // Replace the ObjectMapper with a mock
    }


    @Test
    void recordOperation() throws IOException {
        UUID uuid = UUID.randomUUID();
        File file = new File("src/main/resources/historys", uuid + "_history.json");

        // Mock the file existence check and writing process
        when(mockMapper.readValue((File) any(File.class), (Class<Object>) any())).thenReturn(null);
        doNothing().when(mockMapper).writeValue(any(File.class), any());

        history.recordOperation(AccountOperationType.SAVE, uuid, 100.0, "Deposit");

        // Verify that the ObjectMapper's writeValue method was called
        verify(mockMapper).writeValue(eq(file), any());
    }
}
