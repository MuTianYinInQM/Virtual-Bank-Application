package com.virtualbank.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.virtualbank.controller.HistoryController;

public class HistoryPage {
    private JFrame window;
    private JPanel historyPanel;
    private JPanel titlePanel;
    private JScrollPane scrollPane;
    private JButton exitButton;
    private ObjectMapper mapper = new ObjectMapper();
    private String accountID; // 新增一个成员变量用于存储accountID

    public HistoryPage(UUID accountID) { // 修改构造函数以接收accountID
        this.accountID = accountID.toString(); // 保存accountID
        initializeComponents();
        configureUI();
        loadHistory();
        window.setVisible(true);
    }

    private void initializeComponents() {
        window = new JFrame("Transaction History for Account " + accountID);
        historyPanel = new JPanel();
        titlePanel = new JPanel();
        exitButton = new JButton("Exit");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, 1260, 780);
        window.setLayout(null); // 使用null布局管理器以便自定义组件位置

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
        titlePanel.setLayout(new GridLayout(1, 4)); // 1行4列
        titlePanel.setBackground(new Color(200, 221, 242)); // 浅蓝色背景
        titlePanel.setBounds(300, 140, 620, 30); // 调整位置和尺寸

        String[] titles = {"Account", "Time", "Type", "Description"};
        for (String title : titles) {
            JLabel label = new JLabel(title, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            titlePanel.add(label);
        }

        window.add(titlePanel); // 添加标题面板到窗口

        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(historyPanel);
        scrollPane.setBounds(300, 180, 640, 400);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setOpaque(false);
        window.add(scrollPane);
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void closeWindow() {
        window.dispose();
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

        double amount = (double) transaction.get("amount");
        boolean isCredit = (amount > 0) ? true : false;
        String time = (String) transaction.get("time");
        String date = (String) transaction.get("date");
        String type = (String) transaction.get("type");
        String description = (String) transaction.get("description");
        HistoryLabel historyLabel = new HistoryLabel(isCredit, amount, time, date, type, description);
        historyPanel.add(historyLabel);
        historyPanel.add(Box.createVerticalStrut(10));
    }

    public static void main(String[] args) {
        HistoryPage historyPage = new HistoryPage(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));  // 假设账户ID已给出
        new HistoryController(historyPage);

    }
}
