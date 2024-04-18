package com.virtualbank.controller;

import com.virtualbank.service.HistoryService;
import com.virtualbank.ui.AccountUI;
import com.virtualbank.ui.HistoryUI;

import javax.swing.*;
import java.util.List;

public class HistoryController {
    private HistoryUI historyUI;
    private HistoryService historyService;
    private String accountID; // 账户ID，用于区分不同账户的历史记录

    public HistoryController(HistoryUI historyUI, HistoryService historyService, String accountID) {
        this.historyUI = historyUI;
        this.historyService = historyService;
        this.accountID = accountID;
        initController();
    }

    private void initController() {
        // Set up action listener for the return button
        this.historyUI.getReturnButton().addActionListener(e -> returnToAccountUI());

        // Update history whenever the UI is shown
        this.historyUI.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent windowEvent) {
                updateHistory();
            }
        });
    }

    private void returnToAccountUI() {
        // Make sure UI changes are done on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            historyUI.dispose();
            new AccountUI().setVisible(true);
        });
    }

    private void updateHistory() {
        SwingUtilities.invokeLater(() -> {
            try {
                List<String> transactions = historyService.getTransactions(accountID); // 使用账户ID获取交易记录
                historyUI.setTransactionList(transactions);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(historyUI, "Failed to load transactions for account " + accountID + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}
