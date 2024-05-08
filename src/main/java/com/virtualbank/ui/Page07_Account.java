package com.virtualbank.ui;

import com.virtualbank.model.account.*;

import java.awt.*;
import java.time.Period;
import javax.swing.*;

public class Page07_Account extends JFrame {
    private Account currentAccount;
    private JButton exitButton;
    private JButton deleteButton;

    public Page07_Account(Account account) {
        this.currentAccount = account;
        initializeUI(account);
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    private void initializeUI(Account account) {
        setTitle("Account Details");
        setSize(1260, 780);  // Set the size to match the Current Account window
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);  // Use null layout for absolute positioning

        JButton exitButton = createButton("Exit", 20, 20, 100, 50);
        this.add(exitButton);
        this.exitButton = exitButton;
        JButton deleteButton = createButton("images/delete_Button.png", 500, 630, 233, 85);
        this.add(deleteButton);
        this.deleteButton = deleteButton;


        if (currentAccount instanceof CurrentAccount) {
            setupCurrentAccountUI((CurrentAccount) account);
        } else if (currentAccount instanceof SavingAccount) {
            setupSavingAccountUI((SavingAccount) account);
        } else if (currentAccount instanceof PiggyBank) {
            setupPiggyBankUI((PiggyBank) account);
        }
    }

    private void setupPiggyBankUI(PiggyBank account) {
        setupCommonUIElements("images/Piggy_Bank.png");


        // Labels for account details
        JLabel idLabel = createLabel("ID", 340, 140, 200, 50, Font.BOLD, 28);
        JLabel balanceLabel = createLabel("Balance", 340, 205, 200, 50, Font.BOLD, 28);
        JLabel typeLabel = createLabel("Type", 340, 270, 200, 50, Font.BOLD, 28);
        JLabel interestLabel = createLabel("Interest", 340, 335, 200, 50, Font.BOLD, 28);
        this.add(idLabel);
        this.add(balanceLabel);
        this.add(typeLabel);
        this.add(interestLabel);

        // !!! DYNAMICS
        // Data Labels with dynamic values
        JLabel dataIdLabel = createDataLabel(account.getUuid().toString(), 540, 140, Font.PLAIN, 28);
        JLabel dataBalanceLabel = createDataLabel(String.format("%.2f", account.getBalance()), 540, 205, Font.PLAIN, 28);
        JLabel dataTypeLabel = createDataLabel("Piggy Bank", 540, 270, Font.PLAIN, 28);
        JLabel dataInterestLabel = createDataLabel(String.format("%.2f%%", account.getInterestRate() * 100), 540, 335, Font.PLAIN, 28);
        this.add(dataIdLabel);
        this.add(dataBalanceLabel);
        this.add(dataTypeLabel);
        this.add(dataInterestLabel);

        // Buttons for account operations
        JButton historyButton = createButton("images/history_Button.png", 340, 420, 233, 85);
        JButton transferButton = createButton("images/transfer_Button.png", 660, 420, 233, 85);
        JButton saveButton = createButton("images/save_Button.png", 340, 520, 233, 85);
        JButton consumeButton = createButton("images/consume_Button.png", 660, 520, 233, 85);
        this.add(historyButton);
        this.add(transferButton);
        this.add(saveButton);
        this.add(consumeButton);
    }

    private void setupSavingAccountUI(SavingAccount account) {
        setupCommonUIElements("images/Current_Account.png");

        JLabel idLabel = createLabel("ID", 340, 140, 200, 50, Font.BOLD, 28);
        JLabel balanceLabel = createLabel("Balance", 340, 205, 200, 50, Font.BOLD, 28);
        JLabel typeLabel = createLabel("Type", 340, 270, 200, 50, Font.BOLD, 28);
        JLabel interestLabel = createLabel("Interest", 340, 335, 200, 50, Font.BOLD, 28);
        JLabel startDateLabel = createLabel("Start Date", 340, 400, 200, 50, Font.BOLD, 28);
        JLabel endDateLabel = createLabel("End Date", 340, 465, 200, 50, Font.BOLD, 28);
        JLabel durationLabel = createLabel("Duration", 340, 530, 200, 50, Font.BOLD, 28);
        this.add(idLabel);
        this.add(balanceLabel);
        this.add(typeLabel);
        this.add(interestLabel);
        this.add(startDateLabel);
        this.add(endDateLabel);
        this.add(durationLabel);

        // !!! DYNAMICS
        JLabel dataIdLabel = createDataLabel(account.getUuid().toString(), 540, 140, Font.PLAIN, 28);
        JLabel dataBalanceLabel = createDataLabel(String.format("%.2f", account.getBalance()), 540, 205, Font.PLAIN, 28);
        JLabel dataTypeLabel = createDataLabel("Saving Account", 540, 270, Font.PLAIN, 28);
        JLabel dataInterestLabel = createDataLabel(String.format("%.2f%%", account.getInterestRate() * 100), 540, 335, Font.PLAIN, 28);
        JLabel dataStartDateLabel = createDataLabel(account.getCreateDateTime().toString(), 540, 400, Font.PLAIN, 28);
        JLabel dataEndDateLabel = createDataLabel(account.getMaturityDateTime().toString(), 540, 465, Font.PLAIN, 28);
        JLabel dataDurationLabel = createDataLabel(String.format("%d Years", account.getTermPeriod().getYears()), 540, 530, Font.PLAIN, 28);
        this.add(dataIdLabel);
        this.add(dataBalanceLabel);
        this.add(dataTypeLabel);
        this.add(dataInterestLabel);
        this.add(dataStartDateLabel);
        this.add(dataEndDateLabel);
        this.add(dataDurationLabel);


    }

    private void setupCurrentAccountUI(CurrentAccount account) {
        setupCommonUIElements("images/Current_Account.png");

        JLabel idLabel = createLabel("ID", 340, 140, 200, 50, Font.BOLD, 28);
        JLabel balanceLabel = createLabel("Balance", 340, 205, 200, 50, Font.BOLD, 28);
        JLabel typeLabel = createLabel("Type", 340, 270, 200, 50, Font.BOLD, 28);
        JLabel interestLabel = createLabel("Interest", 340, 335, 200, 50, Font.BOLD, 28);
        this.add(idLabel);
        this.add(balanceLabel);
        this.add(typeLabel);
        this.add(interestLabel);

        // !!! DYNAMICS
        JLabel dataIdLabel = createDataLabel(account.getUuid().toString(), 540, 140, Font.PLAIN, 28);
        JLabel dataBalanceLabel = createDataLabel(String.format("%.2f", account.getBalance()), 540, 205, Font.PLAIN, 28);
        JLabel dataTypeLabel = createDataLabel("Current Account", 540, 270, Font.PLAIN, 28);
        JLabel dataInterestLabel = createDataLabel(String.format("%.2f%%", account.getInterestRate() * 100), 540, 335, Font.PLAIN, 28);
        this.add(dataIdLabel);
        this.add(dataBalanceLabel);
        this.add(dataTypeLabel);
        this.add(dataInterestLabel);

        JButton historyButton = createButton("images/history_Button.png", 340, 420, 233, 85);
        JButton transferButton = createButton("images/transfer_Button.png", 660, 420, 233, 85);
        this.add(historyButton);
        this.add(transferButton);

        this.setVisible(true);
    }

    private void setupCommonUIElements(String backgroundImage) {
        // 设置背景
        ImageIcon goalBackground = new ImageIcon(backgroundImage);
        JLabel backgroundLabel = new JLabel(goalBackground);
        backgroundLabel.setBounds(0, 0, 1260, 780);
        this.setContentPane(backgroundLabel);

        // 创建和添加退出按钮
        JButton exitButton = createButton("Exit", 20, 20, 100, 50);
        this.add(exitButton);
        this.exitButton = exitButton;  // 设置类变量以便于其他操作可能需要引用

        // 创建和添加删除按钮
        JButton deleteButton = createButton("images/delete_Button.png", 500, 630, 233, 85);
        this.add(deleteButton);
        this.deleteButton = deleteButton;  // 同样设置类变量
    }

    private JButton createButton(String textOrIconPath, int x, int y, int width, int height) {
        JButton button = new JButton();
        button.setBounds(x, y, width, height);
        button.setBorderPainted(false);

        if (textOrIconPath.endsWith(".png") || textOrIconPath.endsWith(".jpg")) {
            button.setIcon(new ImageIcon(textOrIconPath));
        } else {
            button.setText(textOrIconPath);
            button.setFont(new Font("Arial", Font.PLAIN, 20));
        }

        return button;
    }

    private JLabel createLabel(String text, int x, int y, int width, int height, int style, int fontSize) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Arial", style, fontSize));
        return label;
    }

    private JLabel createDataLabel(String text, int x, int y, int style, int fontSize) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setBounds(x, y, 300, 50);
        label.setFont(new Font("Arial", style, fontSize));
        return label;
    }


    public static void main(String[] args) {
        Account exampleAccount = new SavingAccount("Example Account", 1000, 0.02, 24, Period.ofYears(1));
//        Account exampleAccount = new PiggyBank("Example Account", 1000);
//        Account exampleAccount = new CurrentAccount("Example Account", 1000, 0.02, 24);
        Page07_Account window = new Page07_Account(exampleAccount);
        window.setVisible(true);
    }
}
