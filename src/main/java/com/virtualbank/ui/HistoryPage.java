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

public class HistoryPage {
    private JFrame window;
    private JPanel historyPanel;
//    private JPanel titlePanel;
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
//        titlePanel = new JPanel();
        exitButton = new JButton();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, 1260, 780);
        window.setLayout(null); // 使用null布局管理器以便自定义组件位置
        window.setResizable(false);

        // 设置背景Icon
        ImageIcon backgroundIcon = new ImageIcon("images/History_Background.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 1260, 780);
        window.setContentPane(backgroundLabel);

        // 配置exitButton
        exitButton.setBounds(16, 2, 135, 57);
        exitButton.addActionListener(e -> window.dispose());
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        window.add(exitButton);
    }

    private void configureUI() {
        // 已删去TitleLabel部分
//        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
//        scrollPane = new JScrollPane(historyPanel);
//        scrollPane.setBounds(300, 180, 640, 400);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setBorder(BorderFactory.createEmptyBorder());
//        scrollPane.getViewport().setOpaque(false);
//        window.add(scrollPane);

        // 配置history滚动面板
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setOpaque(false); // 确保历史记录面板是透明的

        scrollPane = new JScrollPane(historyPanel) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Custom painting code for JScrollPane
                g.setColor(new Color(0, 0, 0, 0)); // Transparent background
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        scrollPane.getViewport().setOpaque(false); // 确保视口是透明的
        scrollPane.setOpaque(false); // 确保滚动面板是透明的
        scrollPane.setBounds(247, 275, 775, 440);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

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
//        boolean isCredit = (amount > 0) ? true : false;
        String isCredit = (amount > 0) ? "+" : "-";
        String time = (String) transaction.get("time");
        String date = (String) transaction.get("date");
        String type = (String) transaction.get("type");
        String description = (String) transaction.get("description");
        ImageIcon cardIcon = new ImageIcon("images/transactionCardIcon.png");

        // 下面将信息填入card中
        TransactionCard transactionCard = new TransactionCard(cardIcon, isCredit + amount, type, date, description);

        historyPanel.add(transactionCard);
        historyPanel.add(Box.createVerticalStrut(10));
    }

    // 静态内部类TransactionCard
    static class TransactionCard extends JPanel {
        private static final int ARC_WIDTH = 10;
        private static final int ARC_HEIGHT = 10;
        private static final Color Background_Color = new Color(0xF6F6F6);
        private static final Color Border_Color = new Color(0x5C5C5C);
        private static final Font Amount_Font = new Font("Arial", Font.BOLD, 20);
        private static final Font Type_Font = new Font("Arial", Font.PLAIN, 20);
        private static final Font Description_Font = new Font("Arial", Font.PLAIN, 14);

        public TransactionCard(Icon icon, String amountText, String typeText, String dateText, String descriptionText) {
            // 设置卡片的整体布局
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setBackground(Color.WHITE);

            // 左侧图标部分
            JLabel iconLabel = new JLabel(icon);
            iconLabel.setPreferredSize(new Dimension(90, 125));
            add(iconLabel, BorderLayout.WEST);

            // 右侧内容部分
            JPanel rightPanel = new JPanel(new BorderLayout());
            rightPanel.setPreferredSize(new Dimension(550, 125));
            rightPanel.setOpaque(false);
            add(rightPanel, BorderLayout.CENTER);

            // 上行：三个标签
            JPanel topRowPanel = new JPanel(new GridLayout(1, 3, 10, 0));
            topRowPanel.setOpaque(false);

            JLabel amountLabel = new JLabel(amountText);
            amountLabel.setFont(Amount_Font);
            amountLabel.setPreferredSize(new Dimension(135, 30));
            amountLabel.setOpaque(false);
            topRowPanel.add(amountLabel);

            JLabel typeLabel = new JLabel(typeText);
            typeLabel.setFont(Type_Font);
            typeLabel.setPreferredSize(new Dimension(155, 30));
            typeLabel.setOpaque(false);
            topRowPanel.add(typeLabel);

            JLabel dateLabel = new JLabel(dateText);
            dateLabel.setFont(Type_Font);
            dateLabel.setPreferredSize(new Dimension(210, 30));
            dateLabel.setOpaque(false);
            topRowPanel.add(dateLabel);

            rightPanel.add(topRowPanel, BorderLayout.NORTH);

            // 下行：描述标签
            JLabel descriptionLabel = new JLabel(descriptionText);
            descriptionLabel.setFont(Description_Font);
            descriptionLabel.setPreferredSize(new Dimension(535, 30));
            descriptionLabel.setOpaque(false);
            rightPanel.add(descriptionLabel, BorderLayout.SOUTH);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 设置背景颜色和圆角矩形
            g2.setColor(Background_Color);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT));

            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 设置圆角矩形边框
            g2.setColor(Border_Color);
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, ARC_WIDTH, ARC_HEIGHT));

            g2.dispose();
        }

//        public static void main(String[] args){
//            JFrame frame = new JFrame("TransactionCard Test");
//            frame.setBounds(0, 0, 700, 140);
//            TransactionCard card = new TransactionCard(new ImageIcon("images/transactionCardIcon.png"),
//                     "amount", "type", "date", "description");
//            card.setSize(new Dimension(640, 125));
//            frame.add(card);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setVisible(true);
//        }
    }

    public static void main(String[] args) {
        HistoryPage historyPage = new HistoryPage(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));  // 假设账户ID已给出
        new HistoryController(historyPage);

    }
}
