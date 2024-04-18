package com.virtualbank.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class History {
    private String timestamp;  // 时间戳为字符串类型
    private String accountID;
    private double amount;
    private String transactionType;
    private String description;

    public History(LocalDateTime timestamp, String accountID, double amount, String transactionType, String description) {
        this.timestamp = formatDateTime(timestamp);  // 在构造函数中直接将 LocalDateTime 格式化为字符串
        this.accountID = accountID;
        this.amount = amount;
        this.transactionType = transactionType;
        this.description = description;
    }

    // Helper method to format LocalDateTime to String
    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }

    // Getters and setters
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = formatDateTime(timestamp); // 设置时将 LocalDateTime 转换为字符串
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
