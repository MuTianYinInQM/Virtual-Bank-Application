package com.virtualbank.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

import com.virtualbank.interfaces.DataUpdateListener;
import com.virtualbank.controller.SavingGoalController;
import com.virtualbank.model.AccountManager;
import com.virtualbank.model.SavingGoal;
import com.virtualbank.model.UIStack;
import com.virtualbank.service.SavingGoalService;
import com.virtualbank.repository.SavingGoalRepository;

/**
 * Page06_Goal - Goal Tracker
 * This class is a UI component responsible for displaying and managing user's saving goals.
 * It implements the DataUpdateListener interface for receiving data update notifications.
 */
public class Page06_Goal extends JFrame implements DataUpdateListener {
    private JLabel backgroundLabel; // Background label
    private JPanel goalsPanel; // Goals panel
    private JScrollPane scrollPane; // Scroll pane
    private JButton createButton; // Create button
    private JButton exitButton; // Exit button
    private SavingGoalService goalService; // Saving goal service
    private ImageIcon goalBackground; // Goal background image
    private List<JPanel> goalPanels = new ArrayList<>(); // List to store goal panels
    private SavingGoalController controller; // Saving goal controller
    private AccountManager accountManager; // Account manager
    private UIStack uiStack;
    private Boolean isPopped = false;

    /**
     * Constructor for Page06_Goal class.
     *
     * @param accountManager The account manager.
     */
    public Page06_Goal(AccountManager accountManager, UIStack uiStack) {
        this.accountManager = accountManager;
        this.goalService = new SavingGoalService(new SavingGoalRepository(), accountManager);
        initializeComponents();
        configureUI();
        loadGoals();
        this.setVisible(true);
        this.controller = new SavingGoalController(this, goalService); // Initialize controller
        this.uiStack = uiStack;
        this.uiStack.pushPage(controller);
    }

    /**
     * Get the create button.
     *
     * @return The create button.
     */
    public JButton getCreateButton() {
        return createButton;
    }

    /**
     * Get the list of goal panels.
     *
     * @return The list of goal panels.
     */
    public List<JPanel> getGoalPanels() {
        return goalPanels;
    }

    // Remove goal panel
    public void removeGoalPanel(JPanel panel) {
        goalsPanel.remove(panel);
        goalsPanel.revalidate();
        goalsPanel.repaint();
    }

    // Initialize components
    private void initializeComponents() {
        this.setTitle("Goal Tracker");
        backgroundLabel = new JLabel();
        goalsPanel = new JPanel();
        scrollPane = new JScrollPane(goalsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        createButton = new JButton();
        exitButton = new JButton();
        goalBackground = new ImageIcon(getClass().getResource("/images/Goal_Background.png")); // 加载背景图片

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(Page06_Goal.this,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
//                    uiStack.pop();
                    System.exit(0);
                }
            }
        });

        configureUI();  // 进行UI配置
    }

    // Configure UI
    private void configureUI() {
        this.setLayout(null); // 使用绝对布局
        this.setSize(1260, 780);
        this.setResizable(false);

        exitButton.setBounds(16, 2, 135, 57);
        exitButton.addActionListener(e -> {
            if (!isPopped) {
                uiStack.pop();
                isPopped = true;
            }
        });
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        this.add(exitButton);

        createButton.setBounds(85, 180, 350, 56);
        createButton.setOpaque(false);
        createButton.setContentAreaFilled(false);
        createButton.setBorderPainted(false);
        createButton.setFocusPainted(false);
        createButton.addActionListener(e -> createOrModifyGoal(null));  // 添加监听器以创建新目标
        this.add(createButton);

        scrollPane.setBounds(500, 130, 620, 575); // 设置滚动面板的位置和大小
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        goalsPanel.setOpaque(false);
        goalsPanel.setLayout(new BoxLayout(goalsPanel, BoxLayout.Y_AXIS));
        this.add(scrollPane);

        backgroundLabel.setIcon(goalBackground);
        backgroundLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.add(backgroundLabel);
    }

    // Load goals
    private void loadGoals() {
        List<SavingGoal> goals = goalService.getAllSavingGoals();
        goalsPanel.removeAll();
        goalPanels.clear(); // 清空存储goalCard的ArrayList

        // 遍历每个goal并为其创建、配置goalCard
        for (SavingGoal goal : goals) {
            // 提取每个goal信息
            String goalName = goal.getGoalName();
            double currentAmountNum = goal.getCurrentAmount();
            double targetAmountNum = goal.getTargetAmount();
            String currentAmount = String.format("%.2f", currentAmountNum);
            String targetAmount = String.format("%.2f", targetAmountNum);

            GoalCard goalCard = new GoalCard(goal, goalName, currentAmount, targetAmount);
            goalPanels.add(goalCard);    // 添加到ArrayList中
            goalsPanel.add(goalCard);    // 添加到滚动面板中
            goalsPanel.add(Box.createVerticalStrut(10));    // 卡片之间留有间隙

        }

        goalsPanel.revalidate();
        goalsPanel.repaint();
    }

    @Override
    public void onDataUpdated() {
        refreshData();
    }

    // Refresh data
    public void refreshData() {
        loadGoals();  // 重新加载目标
        controller.refreshListeners();  // 重新绑定按钮监听器
    }

    private void rebindButtonListeners() {
        for (JPanel goalPanel : getGoalPanels()) {
            JButton modifyButton = (JButton) goalPanel.getComponent(2);
            JButton deleteButton = (JButton) goalPanel.getComponent(3);

            // 移除旧的监听器
            for (ActionListener al : modifyButton.getActionListeners()) {
                modifyButton.removeActionListener(al);
            }
            for (ActionListener al : deleteButton.getActionListeners()) {
                deleteButton.removeActionListener(al);
            }

            // 重新添加监听器
            modifyButton.addActionListener(e -> {
                SavingGoal goal = (SavingGoal) modifyButton.getClientProperty("goal");
                openSetGoalWindow(goal, accountManager.getTotalBalance());
            });
            deleteButton.addActionListener(e -> {
                SavingGoal goal = (SavingGoal) deleteButton.getClientProperty("goal");
                if (JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete this goal?", "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    goalService.deleteSavingGoal(goal.getGoalId());
                    removeGoalPanel(goalPanel);
                }
            });
        }
    }

    private void openSetGoalWindow(SavingGoal goal, double currentBalance) {
        Window06_SetGoal setGoalWindow = new Window06_SetGoal(goal, goalService, this, currentBalance);
        setGoalWindow.display(); // 使用display方法来显示窗口
    }

    // Create or modify goal
    public void createOrModifyGoal(SavingGoal goal) {
        if (goal == null) {
            goal = new SavingGoal(); // Create a new SavingGoal instance
        }
        double currentBalance = accountManager.getTotalBalance();
        openSetGoalWindow(goal, currentBalance);
    }

    /**
     * Get the account manager.
     *
     * @return The account manager.
     */
    public AccountManager getAccountManager() {
        return accountManager;
    }

    /**
     * Static inner class GoalCard.
     * This class represents a goal card used to display information about a saving goal.
     */
    public static class GoalCard extends JPanel {
        private JLabel valueLabel; // Value label
        private JPanel upBarPanel; // Upper progress bar panel
        private JPanel downBarPanel; // Lower progress bar panel
        private JLabel progressLabel; // Progress label
        private JButton modifyButton; // Modify button
        private JButton deleteButton; // Delete button
        private static final Font valueLabelFont = new Font("Arial", Font.PLAIN, 24); // Value label font
        private static final Color Background_Color = new Color(0xBCCCDF); // Background color
        private static final Color Border_Color = new Color(0x5C5C5C); // Border color
        private static final int ARC_WIDTH = 10; // Arc width
        private static final int ARC_HEIGHT = 10; // Arc height

        /**
         * Constructor for GoalCard class.
         *
         * @param goal          The saving goal.
         * @param goalName      The goal name.
         * @param currentAmount The current amount.
         * @param targetAmount  The target amount.
         */
        public GoalCard(SavingGoal goal, String goalName, String currentAmount, String targetAmount) {
            setLayout(null);
            setPreferredSize(new Dimension(1200, 125));
            setMaximumSize(new Dimension(1200, 125));

            valueLabel = new JLabel();
            String valueLabelText = String.format(
                    "<html><font color='yellow'>%s : </font><font color='blue'>%s</font> / <font color='red'>%s</font></html>",
                    goalName, currentAmount, targetAmount);
            valueLabel.setText(valueLabelText);
            valueLabel.setFont(valueLabelFont);
            valueLabel.setHorizontalAlignment(SwingConstants.LEFT);
            valueLabel.setOpaque(false);
            valueLabel.setBorder(null);
            valueLabel.setBounds(75, 15, 500, 45);
            add(valueLabel);

            modifyButton = new JButton();
            modifyButton.putClientProperty("goal", goal);
            modifyButton.setIcon(new ImageIcon(getClass().getResource("/images/Modify_Button.png")));
            modifyButton.setBorderPainted(false);
            modifyButton.setContentAreaFilled(false);
            modifyButton.setFocusPainted(false);
            modifyButton.setBounds(480, 12, 113, 43);
            add(modifyButton);

            deleteButton = new JButton();
            deleteButton.putClientProperty("goal", goal);
            deleteButton.setIcon(new ImageIcon(getClass().getResource("/images/Delete_Button.png")));
            deleteButton.setBorderPainted(false);
            deleteButton.setContentAreaFilled(false);
            deleteButton.setFocusPainted(false);
            deleteButton.setBounds(480, 65, 113, 43);
            add(deleteButton);

            int progressValue = (int) ((goal.getCurrentAmount() / goal.getTargetAmount()) * 100);
            String progressValueText = progressValue + "%";
            progressLabel = new JLabel(progressValueText);
            progressLabel.setFont(valueLabelFont);
            progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
            progressLabel.setOpaque(false);
            progressLabel.setBorder(null);
            progressLabel.setBounds(300, 68, 60, 30);
            add(progressLabel);

            upBarPanel = new JPanel();
            upBarPanel.setBorder(null);

            Color upBarColor = null;
            int upBarWidth = 0;
            if (progressValue < 25) {
                upBarColor = new Color(0xB30000);
                upBarWidth = 50;
            } else if (progressValue < 50) {
                upBarColor = new Color(0xFFAB0A);
                upBarWidth = 105;
            } else if (progressValue < 75) {
                upBarColor = new Color(0xFBFF0A);
                upBarWidth = 165;
            } else if (progressValue < 100) {
                upBarColor = new Color(0x0AFF0F);
                upBarWidth = 240;
            } else {
                upBarColor = new Color(0x0AFF0F);
                upBarWidth = 270;
            }
            upBarPanel.setBackground(upBarColor);
            upBarPanel.setBounds(20, 80, upBarWidth, 8);

            downBarPanel = new JPanel();
            downBarPanel.setBackground(new Color(0x9EABBB));
            downBarPanel.setBorder(null);
            downBarPanel.setBounds(20, 80, 270, 8);
            add(upBarPanel);
            add(downBarPanel);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Background_Color);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));

            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Border_Color);
            g2.setStroke(new BasicStroke(2));
            g2.draw(new RoundRectangle2D.Double(1.5, 1.5, getWidth() - 3, getHeight() - 3, 20, 20));

            g2.dispose();
        }

    }


    // Main method for testing Page06_Goal class
//    public static void main(String[] args) {
//        AccountManager accountManager = new AccountManager();
//        new Page06_Goal(accountManager);
//    }
}
