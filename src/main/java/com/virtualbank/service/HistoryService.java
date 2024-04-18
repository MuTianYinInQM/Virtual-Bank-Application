package com.virtualbank.service;

import com.virtualbank.model.History;
import com.virtualbank.repository.HistoryRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryService {
    private HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    // 根据账户ID返回历史交易的描述信息列表
    public List<String> getTransactions(String accountID) {
        try {
            // 使用传递的账户ID从仓库中获取历史记录，并将每条记录格式化为字符串
            return historyRepository.getHistories(accountID).stream()
                    .map(this::formatHistory)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // 如果发生错误，打印错误信息并返回一个空列表
            System.err.println("Error retrieving histories for account " + accountID + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // 添加一条新的交易历史到指定账户
    public void addTransaction(String accountID, double amount, String transactionType, String description) {
        History newHistory = new History(LocalDateTime.now(), accountID, amount, transactionType, description);
        historyRepository.addHistory(accountID, newHistory);
    }

    // 格式化历史记录为字符串描述
    private String formatHistory(History history) {
        return String.format("%s - %s: $%.2f, %s (%s)",
                history.getTimestamp(),
                history.getTransactionType(),
                history.getAmount(),
                history.getDescription(),
                history.getAccountID());
    }
}
