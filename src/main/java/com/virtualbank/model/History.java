package com.virtualbank.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.virtualbank.model.account.Account;

public class History {
    private String accountID;
    private Account account; // 添加一个成员变量来存储Account对象
    public List<Map<String, Object>> transactions; // 为了单元测试改为public
    private File file;
    public ObjectMapper mapper; // 为了单元测试改为public

    public History(Account account) {
        this.account = account; // 保存传入的Account对象
        this.accountID = account.getUuid().toString();
        // 文件路径，将其放在resource/historys目录下
        Path path = Paths.get("src/main/resources/historys", accountID + "_history.json");
        this.file = path.toFile();
        ensureDirectoryExists(path.getParent()); // 确保目录存在
        this.transactions = new ArrayList<>();
        this.mapper = new ObjectMapper();
        loadTransactions();
    }

    // 确保存储目录存在
    private void ensureDirectoryExists(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to create directory for history files.", e);
            }
        }
    }

    private void loadTransactions() {
        if (file.exists()) {
            try {
                transactions = mapper.readValue(file, new TypeReference<List<Map<String, Object>>>(){});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void recordTransaction(String type, boolean isCredit, double amount, String description) {
        Map<String, Object> transaction = new HashMap<>();
        transaction.put("type", type);
        transaction.put("isCredit", isCredit);
        transaction.put("amount", amount);
        transaction.put("date", java.time.LocalDate.now().toString());
        transaction.put("time", java.time.LocalTime.now().toString());
        transaction.put("description", description);
        transaction.put("accountUUID", accountID);
        transaction.put("accountName", account.getAccountName());

        transactions.add(0, transaction);  // 将新交易添加到列表的最前面
        saveTransactions();
    }

    private void saveTransactions() {
        try {
            mapper.writeValue(file, transactions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
