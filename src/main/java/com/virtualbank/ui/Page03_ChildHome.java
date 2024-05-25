/**
 * The home page for a child user to go to his goal and task pages
 * as well as check his bank accounts and create new bank accounts
 * @author Yunbo Jia, Liyang Qian
 * @version 3.0 2024-5-21
 */
package com.virtualbank.ui;

import com.virtualbank.model.AccountManager;
import com.virtualbank.model.account.*;

import javax.swing.*;
import java.awt.*;
import java.time.Period;
import java.util.UUID;

/**
 * Page03_ChildHome is a JFrame subclass representing the child home page.
 * This page includes buttons for exiting, viewing goals, tasks, and creating a new account.
 * It also features a scroll panel to display various account information.
 *
 * @see JFrame
 */
public class Page03_ChildHome extends JFrame {

    private JButton exitButton = new JButton();
    private JButton goalButton = new JButton();
    private JButton taskButton = new JButton();
    private JButton createAccountButton = new JButton();
    private JPanel scrollPanel;
    private JLabel goalButtonLabel;

    /**
     * Gets the exit button.
     *
     * @return the JButton object for the exit button
     */
    public JButton getExitButton() {
        return exitButton;
    }

    /**
     * Gets the goal button.
     *
     * @return the JButton object for the goal button
     */
    public JButton getGoalButton() {
        return goalButton;
    }

    /**
     * Gets the task button.
     *
     * @return the JButton object for the task button
     */
    public JButton getTaskButton() {
        return taskButton;
    }

    /**
     * Gets the create account button.
     *
     * @return the JButton object for the create account button
     */
    public JButton getCreateAccountButton() {
        return createAccountButton;
    }

    /**
     * Gets the scroll panel.
     *
     * @return the JPanel object for the scroll panel
     */
    public JPanel getScrollPanel() {
        return scrollPanel;
    }

    /**
     * Constructs a new Page03_ChildHome frame.
     * Sets up the frame size, title, layout, initializes components,
     * and adds them to the frame.
     */
    public Page03_ChildHome() {
        final int window_width = 1260;
        final int window_height = 780;
        setBounds(0, 0, window_width, window_height);
        setResizable(false);
        setLayout(null);
        setTitle("JoyBank - Child Home Page");
        ImageIcon background = new ImageIcon(getClass().getResource("/images/ChildHomePage.png"));
        JLabel backgroundPanel = new JLabel(background);
        backgroundPanel.setBounds(0, 0, window_width, window_height);
        getContentPane().add(backgroundPanel);
//        JPanel backgroundPanel = new JPanel();
//        backgroundPanel.setBackground(new Color(0xf8f6ea));
//        backgroundPanel.setLayout(null);
//        backgroundPanel.setBounds(0, 0, window_width, window_height);
//        add(backgroundPanel);

        // Exit button to return to LoginPage
        exitButton.setBounds(20, 20, 134, 50);
        ImageIcon exitButtonIcon = new ImageIcon(getClass().getResource("/images/ExitButtonImage.png"));
        exitButton.setIcon(exitButtonIcon);
        exitButton.setBorder(null);
        exitButton.setContentAreaFilled(false);
        backgroundPanel.add(exitButton);

        // Goal button to enter child's goal page
        goalButton.setBounds(34, 150, 238, 113);
        ImageIcon goalButtonIcon = new ImageIcon(getClass().getResource("/images/GoalButton.png"));
        goalButton.setIcon(goalButtonIcon);

        // Goal and its completion progress
        // TODO 现在这部分还不能变化 目标不归我管 怎么处理
        Double currentAmount = 2544.00;
        Double goal = 500.00;
        String goalText = String.format("%.2f/%.2f", currentAmount, goal);
        JLabel initialGoalButtonLabel = new JLabel(goalText);
        initialGoalButtonLabel.setBounds(42, 20, 220, 109);
        initialGoalButtonLabel.setHorizontalAlignment(SwingConstants.CENTER);
        goalButton.setLayout(null);
        goalButton.add(initialGoalButtonLabel);
        goalButton.setBorder(null);
        backgroundPanel.add(goalButton);
        this.goalButtonLabel = initialGoalButtonLabel;

        // Task button to enter child's task page
        taskButton.setBounds(320, 150, 238, 113);
        ImageIcon taskButtonIcon = new ImageIcon(getClass().getResource("/images/TaskButton.png"));
        taskButton.setIcon(taskButtonIcon);
        taskButton.setBorder(null);
        backgroundPanel.add(taskButton);

        // Button to create a new account
        createAccountButton.setBounds(34, 300, 525, 100);
        ImageIcon createAccountButtonIcon = new ImageIcon(getClass().getResource("/images/CreateAccountButton.png"));
        createAccountButton.setIcon(createAccountButtonIcon);
        createAccountButton.setBorder(null);
        backgroundPanel.add(createAccountButton);

        // Create scrollable area
        JPanel initialScrollPanel = new JPanel();
        initialScrollPanel.setLayout(new BoxLayout(initialScrollPanel, BoxLayout.Y_AXIS));
        initialScrollPanel.setBackground(new Color(0xfcfcf7));
        JScrollPane scrollPane = new JScrollPane(initialScrollPanel);
        scrollPane.setBounds(655, 230, 550, 480);
        scrollPane.setBackground(new Color(0xf8f6ea));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        backgroundPanel.add(scrollPane);

        this.scrollPanel = initialScrollPanel;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    /**
     * Updates the total balance displayed on the goal button.
     *
     * @param totalBalance the new total balance to display
     */
    public void totalBalanceUpdate(double totalBalance) {
        //重新绘制总金额
        // TODO goal 应该可以更改 相信后人的智慧
        final double goal = 500;
        String goalText = String.format("%.2f/%.2f", totalBalance, goal);
        goalButtonLabel.setText(goalText);
        goalButtonLabel.repaint();
    }


    /**
     * A label representing a current account, a subclass of AccountLabel
     */
    public static class CurrentAccountLabel extends AccountLabel {
        public CurrentAccountLabel(String ID, Double money) {
            super(ID, money, "Current Account");
            // 图标
            ImageIcon savingAccountIcon = new ImageIcon(getClass().getResource("/images/CurrentAccountFrame.png"));
            this.setIcon(savingAccountIcon);
        }
    }

    /**
     * A label representing a savings account, a subclass of AccountLabel
     */
    public static class SavingAccountLabel extends AccountLabel {
        public SavingAccountLabel(String ID, Double money) {
            super(ID, money, "Saving Account");
            // 图标
            ImageIcon currentAccountIcon = new ImageIcon(getClass().getResource("/images/SavingAccountFrame.png"));
            this.setIcon(currentAccountIcon);
        }
    }

    /**
     * A label representing a piggy bank account, a subclass of AccountLabel.
     */
    public static class PiggyBankLabel extends AccountLabel {
        public PiggyBankLabel(String ID, Double money) {
            super(ID, money, "Piggy Bank");
            // 图标
            ImageIcon currentAccountIcon = new ImageIcon(getClass().getResource("/images/PiggybankFrame.png"));
            this.setIcon(currentAccountIcon);
        }
    }

    /**
     * A base label for displaying account information, a subclass of JLabel
     *
     * @see JLabel
     */
    public static class AccountLabel extends JLabel {

        private JButton enterButton;

        /**
         * Gets the enter button.
         *
         * @return the JButton object for the enter button
         */
        public JButton getEnterButton() {
            return enterButton;
        }

        /**
         * Constructs a new AccountLabel.
         *
         * @param ID the account ID
         * @param money the account balance
         * @param type the type of account
         */
        public AccountLabel(String ID, Double money, String type) {
            this.setSize(523, 117);
            Font font = new Font(this.getFont().getName(), Font.PLAIN, 22);

            JLabel idLabel = new JLabel("ID:" + ID);
            idLabel.setBounds(133, -20, 220, 109);
            idLabel.setFont(font);
            this.add(idLabel);

            String moneyText = String.format("$ %.2f", money);
            JLabel moneyLabel = new JLabel(moneyText);
            moneyLabel.setBounds(350, -20, 220, 109);
            moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            moneyLabel.setFont(font);
            this.add(moneyLabel);

            Font nameFont = new Font(this.getFont().getName(), Font.PLAIN, 25);
            JLabel typeLabel = new JLabel(type);
            typeLabel.setBounds(133, 20, 220, 109);
            typeLabel.setFont(nameFont);
            this.add(typeLabel);

            // TODO 这个应该怎么回调？
            enterButton = new JButton();
            enterButton.setBounds(415, 40, 95, 60);
            ImageIcon enterButtonIcon = new ImageIcon(getClass().getResource("/images/EnterButtonImage.png"));
            enterButton.setIcon(enterButtonIcon);
            enterButton.setContentAreaFilled(false);
            enterButton.setBorderPainted(false);
            enterButton.setBorder(null);
            this.add(enterButton);
        }
    }

    /**
     * Creates and displays the Page03_ChildHome frame.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Page03_ChildHome page03ChildHome = new Page03_ChildHome();
        SavingAccountLabel savingAccountLabel = new SavingAccountLabel("001",12.5);
        CurrentAccountLabel currentAccountLabel = new CurrentAccountLabel("002", 12.9);
        PiggyBankLabel piggyBankLabel = new PiggyBankLabel("000",533.33);
        SavingAccountLabel savingAccountLabel1 = new SavingAccountLabel("009",1.0);
        page03ChildHome.scrollPanel.add(savingAccountLabel);
        page03ChildHome.scrollPanel.add(currentAccountLabel);
        page03ChildHome.scrollPanel.add(piggyBankLabel);
        page03ChildHome.scrollPanel.add(savingAccountLabel1);
        page03ChildHome.scrollPanel.add(new CurrentAccountLabel("008", 121.9));
        page03ChildHome.repaint();
    }
}