package com.virtualbank.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class HistoryPage {
    private JFrame window;
    private JPanel historyPanel;
    private JScrollPane scrollPane;
    private JButton exitButton;
    private ObjectMapper mapper = new ObjectMapper();
    private String accountID; // 新增一个成员变量用于存储accountID

    public HistoryPage(String accountID) { // 修改构造函数以接收accountID
        this.accountID = accountID; // 保存accountID
        initializeComponents();
        configureUI();
        loadHistory();
        window.setVisible(true);
    }

    private void initializeComponents() {
        window = new JFrame("Transaction History for Account " + accountID);
        historyPanel = new JPanel();
        exitButton = new JButton("Exit");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, 1260, 780);
        window.setResizable(false);
        window.setLayout(null);

        ImageIcon backgroundIcon = new ImageIcon("images/History_Background.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 1260, 780);
        window.setContentPane(backgroundLabel);

        exitButton.setBounds(20, 20, 100, 50);
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.addActionListener(e -> window.dispose());
        window.add(exitButton);
    }

    private void configureUI() {
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setOpaque(false);

        scrollPane = new JScrollPane(historyPanel);
        scrollPane.setBounds(300, 180, 640, 400);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setOpaque(false);
        window.add(scrollPane);
    }

    private void loadHistory() {
        // 修改文件路径以使用accountID
        String filePath = "src/main/resources/historys/" + accountID + "_history.json";
        File file = new File(filePath);
        try {
            List<Map<String, Object>> transactions = mapper.readValue(file, new TypeReference<List<Map<String, Object>>>(){});
            for (Map<String, Object> transaction : transactions) {
                addHistoryLabel(transaction);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(window, "Failed to load history for account " + accountID,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addHistoryLabel(Map<String, Object> transaction) {
        boolean isCredit = (boolean) transaction.get("isCredit");
        double amount = (double) transaction.get("amount");
        String time = (String) transaction.get("time");
        String date = (String) transaction.get("date");
        String type = (String) transaction.get("type");
        String description = (String) transaction.get("description");
        HistoryLabel historyLabel = new HistoryLabel(isCredit, amount, time, date, type, description);
        historyPanel.add(historyLabel);
        historyPanel.add(Box.createVerticalStrut(10));
    }

    public static void main(String[] args) {
        // Example usage, the accountID should be passed here
        new HistoryPage("123456789");
    }
}
