package com.virtualbank.ui;

import com.virtualbank.model.account.*;

import java.awt.*;
import java.time.Period;
import javax.swing.*;

public class Page07_Account extends JFrame {
    private Account currentAccount;
    private JButton exitButton;
    private JButton deleteButton;
    private JButton consumeButton;
    private JButton transferButton;
    private JButton saveButton;
    private JButton historyButton;
    // 页面上显示余额的
    private JLabel dataBalanceLabel;


    public Page07_Account(Account account) {
        this.currentAccount = account;
        initializeUI(account);
    }

    public JButton getConsumeButton() {
        return consumeButton;
    }

    public JButton getTransferButton() {
        return transferButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getHistoryButton() {
        return historyButton;
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

        // 根据账户类型设置各自的UI
        if (currentAccount instanceof CurrentAccount) {
            setupCurrentAccountUI((CurrentAccount) account);
        } else if (currentAccount instanceof SavingAccount) {
            setupSavingAccountUI((SavingAccount) account);
        } else if (currentAccount instanceof PiggyBank) {
            setupPiggyBankUI((PiggyBank) account);
        }
    }

    private void setupPiggyBankUI(PiggyBank account) {

        ImageIcon backgroundIcon = new ImageIcon("images/Piggy_Bank.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 1260, 780);
        this.setContentPane(backgroundLabel);

        setupCommonUIElements();

        // !!! DYNAMICS
        // Data Labels with dynamic values
        JLabel dataIdLabel = createDataLabel(account.getUuid().toString(), 470, 332);
        this.dataBalanceLabel = createDataLabel(String.format("%.2f", account.getBalance()), 470, 400);
        JLabel dataTypeLabel = createDataLabel("Piggy Bank", 470, 473);
        JLabel dataInterestLabel = createDataLabel(String.format("%.2f%%", account.getInterestRate() * 100), 470, 550);
        this.add(dataIdLabel);
        this.add(dataBalanceLabel);
        this.add(dataTypeLabel);
        this.add(dataInterestLabel);

        // Buttons for account operations
        this.historyButton = createButton("", 902, 506, 230, 57);
        this.transferButton = createButton("", 187, 150, 228, 60);
        this.saveButton = createButton("", 458, 150, 153, 57);
        this.consumeButton = createButton("", 638, 150, 160, 58);
        this.add(historyButton);
        this.add(transferButton);
        this.add(saveButton);
        this.add(consumeButton);
    }

    private void setupSavingAccountUI(SavingAccount account) {

        ImageIcon backgroundIcon = new ImageIcon("images/Saving_Account.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 1260, 780);
        this.setContentPane(backgroundLabel);

        setupCommonUIElements();

        // !!! DYNAMICS
        JLabel dataIdLabel = createDataLabel(account.getUuid().toString(), 470, 322);
        this.dataBalanceLabel = createDataLabel(String.format("%.2f", account.getBalance()), 470, 376);
        JLabel dataTypeLabel = createDataLabel("Saving Account", 470, 427);
        JLabel dataInterestLabel = createDataLabel(String.format("%.2f%%", account.getInterestRate() * 100), 470, 476);
        JLabel dataStartDateLabel = createDataLabel(account.getCreateDateTime().toString(), 470, 527);
        JLabel dataEndDateLabel = createDataLabel(account.getMaturityDateTime().toString(), 470, 579);
        JLabel dataDurationLabel = createDataLabel(String.format("%d Years", account.getTermPeriod().getYears()), 470, 630);
        this.add(dataIdLabel);
        this.add(dataBalanceLabel);
        this.add(dataTypeLabel);
        this.add(dataInterestLabel);
        this.add(dataStartDateLabel);
        this.add(dataEndDateLabel);
        this.add(dataDurationLabel);

        this.historyButton = createButton("", 902, 506, 230, 57);
        this.add(historyButton);
    }


    private void setupCurrentAccountUI(CurrentAccount account) {

        ImageIcon backgroundIcon = new ImageIcon("images/Current_Account.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 1260, 780);
        this.setContentPane(backgroundLabel);

        setupCommonUIElements();

        // !!! DYNAMICS
        JLabel dataIdLabel = createDataLabel(account.getUuid().toString(), 470, 332);
        this.dataBalanceLabel = createDataLabel(String.format("%.2f", account.getBalance()), 470, 400);
        JLabel dataTypeLabel = createDataLabel("Piggy Bank", 470, 473);
        JLabel dataInterestLabel = createDataLabel(String.format("%.2f%%", account.getInterestRate() * 100), 470, 550);
        this.add(dataIdLabel);
        this.add(dataBalanceLabel);
        this.add(dataTypeLabel);
        this.add(dataInterestLabel);

        this.historyButton = createButton("", 902, 506, 230, 57);
        this.transferButton = createButton("", 187, 150, 228, 60);
        this.add(historyButton);
        this.add(transferButton);
    }

    private void setupCommonUIElements() {

        // 设置退出按钮和删除按钮
        exitButton = createButton("", 16, 2, 135, 57);
//        if(exitButton != null)  System.out.println("exitButton created.");
        this.add(exitButton);
        deleteButton = createButton("", 902, 157, 230, 57);
        this.add(deleteButton);
    }

    // 创建JButton的统一方法
    private JButton createButton(String textOrIconPath, int x, int y, int width, int height) {
        JButton button = new JButton();
        button.setBounds(x, y, width, height);
        button.setBorderPainted(false);

        if (textOrIconPath.endsWith(".png") || textOrIconPath.endsWith(".jpg")) {
            button.setIcon(new ImageIcon(textOrIconPath));
        }
        else if(textOrIconPath.isEmpty()){
            button.setOpaque(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
        }
        else{
            button.setText(textOrIconPath);
            button.setFont(new Font("Arial", Font.PLAIN, 20));
        }

        return button;
    }

    private JLabel createDataLabel(String text, int x, int y) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setBounds(x, y, 280, 30);
        label.setFont(new Font("Arial", Font.PLAIN, 26));
        return label;
    }

    public void updatePage() {
        dataBalanceLabel.setText(String.format("%.2f", currentAccount.getBalance()));
    }

    public static void main(String[] args) {
//        Account exampleAccount = new SavingAccount("Example Account", 1000, 0.02, 24, Period.ofYears(1));
//        Account exampleAccount = new PiggyBank("Example Account", 1000);
        Account exampleAccount = new CurrentAccount("Example Account", 1000, 0.02, 24);
        Page07_Account window = new Page07_Account(exampleAccount);
        window.setVisible(true);
    }
}
