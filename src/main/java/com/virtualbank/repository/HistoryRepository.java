package com.virtualbank.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualbank.model.History;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HistoryRepository {
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String BASE_DIRECTORY = "src/main/resources/histories"; // 假定所有历史记录文件存储在一个单独的目录

    public HistoryRepository() {
        ensureDirectoryExists();
    }

    public List<History> getHistories(String accountID) {
        File file = new File(BASE_DIRECTORY + File.separator + accountID + "historylist.json");
        if (file.exists()) {
            try {
                return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, History.class));
            } catch (IOException e) {
                System.err.println("Error reading histories from file: " + e.getMessage());
                return new ArrayList<>(); // On error, return an empty list
            }
        }
        return new ArrayList<>(); // If file doesn't exist, return an empty list
    }

    public void addHistory(String accountID, History history) {
        List<History> histories = getHistories(accountID);
        histories.add(0, history); // 最新的历史记录放在前面
        saveHistories(accountID, histories);
    }

    private void saveHistories(String accountID, List<History> histories) {
        File file = new File(BASE_DIRECTORY + File.separator + accountID + "historylist.json");
        try {
            objectMapper.writeValue(file, histories);
        } catch (IOException e) {
            System.err.println("Error writing histories to file: " + e.getMessage());
        }
    }

    private void ensureDirectoryExists() {
        try {
            Files.createDirectories(Paths.get(BASE_DIRECTORY));
        } catch (IOException e) {
            System.err.println("Could not create history directory: " + e.getMessage());
        }
    }
}
