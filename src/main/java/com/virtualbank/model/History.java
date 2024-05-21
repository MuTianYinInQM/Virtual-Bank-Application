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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// 这个是记录账户信息的组件
// 负责持久化 历史记录 (History Logger)
public class History implements Serializable {
    private static final String DIRECTORY_PATH = "src/main/resources/historys";
    ObjectMapper mapper;

    public History() {
        this.mapper = new ObjectMapper();
        ensureDirectoryExists(Paths.get(DIRECTORY_PATH));
    }

    // 确保存储目录存在
    void ensureDirectoryExists(Path path) {
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create directory for history files: " + path, e);
        }
    }

    public void recordOperation(AccountOperationType accountOperationType, UUID id, double amount, String description) {
        Path filePath = Paths.get(DIRECTORY_PATH, id + "_history.json");
        File file = filePath.toFile();
        List<Map<String, Object>> transactions = new ArrayList<>();

        // 加载现有的交易记录，如果有的话
        if (file.exists()) {
            try {
                transactions = mapper.readValue(file, new TypeReference<List<Map<String, Object>>>() {
                });
            } catch (IOException e) {
                System.err.println("Error reading history file: " + e.getMessage());
            }
        }

        // 创建新的交易记录
        Map<String, Object> transaction = new HashMap<>();
        transaction.put("type", accountOperationType.getName());
        transaction.put("amount", amount);
        transaction.put("uuid", id.toString());
        transaction.put("time", LocalTime.now().toString());
        transaction.put("date", LocalDate.now().toString());
        transaction.put("description", description);

        // 将新交易添加到交易列表
        transactions.add(transaction);

        // 写入文件
        try {
            mapper.writeValue(file, transactions);
        } catch (IOException e) {
            System.err.println("Error writing to history file: " + e.getMessage());
        }
    }
}
