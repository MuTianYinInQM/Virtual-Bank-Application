package com.virtualbank.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;



/**
 * Manages the history of all account operations within the banking system.
 * This class is responsible for recording and storing transaction history into JSON files.
 */
public class History implements Serializable {
    private static final String DIRECTORY_PATH = "src/main/resources/historys";
    ObjectMapper mapper;


    /**
     * Constructs a new History object with a configured Jackson ObjectMapper for JSON processing.
     */
    public History() {
        this.mapper = new ObjectMapper();
        ensureDirectoryExists(Paths.get(DIRECTORY_PATH));
    }

    /**
     * Ensures that the directory for storing history files exists.
     * @param path The path to the directory where history files will be stored.
     * @throws RuntimeException if there is an issue creating the directory.
     */
    void ensureDirectoryExists(Path path) {
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create directory for history files: " + path, e);
        }
    }


    /**
     * Records a transaction operation into the history of the specified account.
     *
     * @param accountOperationType The type of operation performed (e.g., deposit, withdrawal).
     * @param id                   The UUID of the account the operation is associated with.
     * @param amount               The amount involved in the operation.
     * @param description          A description of the operation.
     */
    public void recordOperation(AccountOperationType accountOperationType, UUID id, double amount, String description) {
        Path filePath = Paths.get(DIRECTORY_PATH, id + "_history.json");
        File file = filePath.toFile();
        List<Map<String, Object>> transactions = new ArrayList<>();

        // load existing transactions if the file exists
        if (file.exists()) {
            try {
                transactions = mapper.readValue(file, new TypeReference<List<Map<String, Object>>>() {
                });
            } catch (IOException e) {
                System.err.println("Error reading history file: " + e.getMessage());
            }
        }

        // create a new UUID for the transaction
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // get the current time
        String timeString = LocalTime.now().format(formatter);

        // create a new transaction
        Map<String, Object> transaction = new HashMap<>();
        transaction.put("type", accountOperationType.getName());
        transaction.put("amount", amount);
        transaction.put("uuid", id.toString());
        transaction.put("time", timeString);  // 使用格式化后的时间
        transaction.put("date", LocalDate.now().toString());
        transaction.put("description", description);

        // add the new transaction to the list
        transactions.add(transaction);

        // write the updated list of transactions back to the file
        try {
            mapper.writeValue(file, transactions);
        } catch (IOException e) {
            System.err.println("Error writing to history file: " + e.getMessage());
        }
    }
}
