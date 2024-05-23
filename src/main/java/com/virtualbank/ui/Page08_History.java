package com.virtualbank.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.awt.geom.RoundRectangle2D;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.virtualbank.controller.HistoryController;

/**
 * Page08_History
 * This class represents the transaction history page in the banking UI.
 */
public class Page08_History extends JFrame{
    private JPanel historyPanel; // Container for displaying history records
    //    private JPanel titlePanel;
    private JScrollPane scrollPane; // Scroll pane for history panel
    private JButton exitButton; // Button to exit the page
    private ObjectMapper mapper = new ObjectMapper(); // ObjectMapper for JSON handling
    private String accountID; // Member variable to store account ID


    /**
     * Get the exit button.
     * @return The exit button.
     */
    public JButton getExitButton() {
        return exitButton;
    }

    /**
     * Get the scroll pane.
     * @return The scroll pane.
     */
    public JScrollPane getScrollPane() {
        return scrollPane;
    }


    /**
     * Constructor for Page08_History class.
     * @param accountID The account ID to display history for.
     */
    public Page08_History(UUID accountID) {
        this.accountID = accountID.toString(); // Save accountID
        initializeComponents();
        configureUI();
        loadHistory();
        this.setVisible(true);
    }

    // Initialize UI components
    private void initializeComponents() {
        this.setTitle("Transaction History for Account " + accountID);
        historyPanel = new JPanel();
//        titlePanel = new JPanel();
        exitButton = new JButton();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0, 0, 1260, 780);
        this.setLayout(null); // 使用null布局管理器以便自定义组件位置
        this.setResizable(false);

        // 设置背景Icon
        ImageIcon backgroundIcon = new ImageIcon("images/History_Background.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 1260, 780);
        this.setContentPane(backgroundLabel);

        // 配置exitButton
        exitButton.setBounds(16, 2, 135, 57);
        exitButton.addActionListener(e -> this.dispose());
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        this.add(exitButton);
    }

    // Configure UI layout and appearance
    private void configureUI() {
//        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
//        scrollPane = new JScrollPane(historyPanel);
//        scrollPane.setBounds(300, 180, 640, 400);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setBorder(BorderFactory.createEmptyBorder());
//        scrollPane.getViewport().setOpaque(false);
//        this.add(scrollPane);

        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setOpaque(false);

        scrollPane = new JScrollPane(historyPanel) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Custom painting code for JScrollPane
                g.setColor(new Color(0, 0, 0, 0)); // Transparent background
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.setBounds(247, 300, 775, 420);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        this.add(scrollPane);

    }

    // Load transaction history from JSON file
    private void loadHistory() {
        String filePath = "src/main/resources/historys/" + accountID + "_history.json";
        File file = new File(filePath);
        try {
            List<Map<String, Object>> transactions = mapper.readValue(file, new TypeReference<List<Map<String, Object>>>(){});
            for (Map<String, Object> transaction : transactions) {
                addHistoryLabel(transaction);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load history for account " + accountID,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Add a history label to the history panel
    private void addHistoryLabel(Map<String, Object> transaction) {

        double amount = (double) transaction.get("amount");
//        boolean isCredit = (amount > 0) ? true : false;
        String time = (String) transaction.get("time");
        String date = (String) transaction.get("date");
        String type = (String) transaction.get("type");
        System.out.println("type:"+type);
        String isCredit = (type.equals("转账") || type.equals("消费")) ? "-" : "+";
        System.out.println("isCredit:"+isCredit);
        String description = (String) transaction.get("description");
        ImageIcon cardIcon = new ImageIcon("images/transactionCardIcon.png");

        TransactionCard transactionCard = new TransactionCard(cardIcon, isCredit + amount, type, date, time, description);

        historyPanel.add(transactionCard);
        historyPanel.add(Box.createVerticalStrut(10));
    }

    // Static inner class representing a transaction card
    static class TransactionCard extends JPanel {
        private static final int ARC_WIDTH = 10;
        private static final int ARC_HEIGHT = 10;
        private static final Color Background_Color = new Color(0xF6F6F6);
        private static final Color Border_Color = new Color(0x5C5C5C);
        private static final Font Amount_Font = new Font("Arial", Font.BOLD, 28);
        private static final Font Type_Font = new Font("Arial", Font.PLAIN, 18);
        private static final Font Date_Font = new Font("Arial", Font.PLAIN, 18);
        private static final Font Description_Font = new Font("Arial", Font.PLAIN, 16);

        // Constructor for creating a transaction card
        public TransactionCard(Icon icon, String amountText, String typeText, String dateText, String timeText, String descriptionText) {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setBackground(new Color(0xF6F6F6));
            setPreferredSize(new Dimension(680, 120));
            setMaximumSize(new Dimension(680, 120));

            JLabel iconLabel = new JLabel(icon);
            iconLabel.setPreferredSize(new Dimension(90, 120));
            add(iconLabel, BorderLayout.WEST);

            JPanel rightPanel = new JPanel(new BorderLayout());
            rightPanel.setPreferredSize(new Dimension(550, 120));
            rightPanel.setOpaque(false);
            add(rightPanel, BorderLayout.CENTER);

            JPanel topRowPanel = new JPanel();
            topRowPanel.setLayout(null);
            topRowPanel.setPreferredSize(new Dimension(550, 50));
            topRowPanel.setOpaque(false);

            JLabel amountLabel = new JLabel(amountText);
            amountLabel.setBounds(5, 5, 100, 40);
            amountLabel.setFont(Amount_Font);
//            amountLabel.setOpaque(true);
            amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
            topRowPanel.add(amountLabel);

            JLabel typeLabel = new JLabel(typeText);
            typeLabel.setBounds(110, 5, 150, 40);
            typeLabel.setFont(Type_Font);
//            typeLabel.setOpaque(true);
            typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
            topRowPanel.add(typeLabel);

            JLabel dateLabel = new JLabel(dateText+ " "+ timeText);
            dateLabel.setBounds(275, 5, 250, 40);
            dateLabel.setFont(Date_Font);
//            dateLabel.setOpaque(true);
            dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
            topRowPanel.add(dateLabel);

            rightPanel.add(topRowPanel, BorderLayout.NORTH);

            JLabel descriptionLabel = new JLabel(descriptionText);
            descriptionLabel.setFont(Description_Font);
            descriptionLabel.setPreferredSize(new Dimension(500, 50));
            descriptionLabel.setOpaque(false);
            descriptionLabel.setHorizontalAlignment(SwingConstants.LEFT);
            rightPanel.add(descriptionLabel, BorderLayout.SOUTH);
        }

        // Custom painting for transaction card
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Background_Color);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT));

            g2.dispose();
        }

        // Custom painting for transaction card border
        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Border_Color);
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, ARC_WIDTH, ARC_HEIGHT));

            g2.dispose();
        }


    }

    // Main method for testing Page08_History class
    public static void main(String[] args) {
        Page08_History historyPage = new Page08_History(UUID.fromString("0b805b57-819f-4c65-88ec-357d89009f5c"));  // 假设账户ID已给出
    }
}
