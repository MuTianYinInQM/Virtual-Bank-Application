package com.virtualbank.ui;

import com.virtualbank.model.account.*;

import java.awt.*;
import java.time.Period;
import javax.swing.*;

/**
 * Page07_Account
 * This class represents the account details page in the banking UI.
 */
public class Page07_Account extends JFrame {
    private Account currentAccount; // Current account being displayed
    private JButton exitButton; // Button to exit the page
    private JButton deleteButton; // Button to delete the account
    private JButton consumeButton; // Button for consuming money
    private JButton transferButton; // Button for transferring money
    private JButton saveButton; // Button for saving money
    private JButton historyButton; // Button to view transaction history
    private JLabel dataBalanceLabel; // Label to display account balance

    /**
     * Constructor for Page07_Account class.
     * @param account The account to display details for.
     */
    public Page07_Account(Account account) {
        this.currentAccount = account;
        initializeUI(account);
    }

    /**
     * Get the consume button.
     * @return The consume button.
     */
    public JButton getConsumeButton() {
        return consumeButton;
    }

    /**
     * Get the transfer button.
     * @return The transfer button.
     */
    public JButton getTransferButton() {
        return transferButton;
    }

    /**
     * Get the save button.
     * @return The save button.
     */
    public JButton getSaveButton() {
        return saveButton;
    }

    /**
     * Get the history button.
     * @return The history button.
     */
    public JButton getHistoryButton() {
        return historyButton;
    }

    /**
     * Get the current account being displayed.
     * @return The current account.
     */
    public Account getCurrentAccount() {
        return currentAccount;
    }

    /**
     * Get the exit button.
     * @return The exit button.
     */
    public JButton getExitButton() {
        return exitButton;
    }

    /**
     * Get the delete button.
     * @return The delete button.
     */
    public JButton getDeleteButton() {
        return deleteButton;
    }

    // Initialize UI components based on the account type
    private void initializeUI(Account account) {
        setTitle("Account Details");
        setSize(1260, 780);  // Set the size to match the Current Account window
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);  // Use null layout for absolute positioning

        // Set up UI based on account type
        if (currentAccount instanceof CurrentAccount) {
            setupCurrentAccountUI((CurrentAccount) account);
        } else if (currentAccount instanceof SavingAccount) {
            setupSavingAccountUI((SavingAccount) account);
        } else if (currentAccount instanceof PiggyBank) {
            setupPiggyBankUI((PiggyBank) account);
        }
    }

    // Set up UI for PiggyBank account
    private void setupPiggyBankUI(PiggyBank account) {

        ImageIcon backgroundIcon = new ImageIcon("src/main/resources/images/Piggy_Bank.png");
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

    // Set up UI for SavingAccount account
    private void setupSavingAccountUI(SavingAccount account) {

        ImageIcon backgroundIcon = new ImageIcon("src/main/resources/images/Saving_Account.png");
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

    // Set up UI for CurrentAccount account
    private void setupCurrentAccountUI(CurrentAccount account) {

        ImageIcon backgroundIcon = new ImageIcon("src/main/resources/images/Current_Account.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 1260, 780);
        this.setContentPane(backgroundLabel);

        setupCommonUIElements();

        // !!! DYNAMICS
        JLabel dataIdLabel = createDataLabel(account.getUuid().toString(), 470, 332);
        this.dataBalanceLabel = createDataLabel(String.format("%.2f", account.getBalance()), 470, 400);
        JLabel dataTypeLabel = createDataLabel("Current Account", 470, 473);
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

    // Set up common UI elements like buttons
    private void setupCommonUIElements() {

        // 设置退出按钮和删除按钮
        exitButton = createButton("", 16, 2, 135, 57);
//        if(exitButton != null)  System.out.println("exitButton created.");
        this.add(exitButton);
        deleteButton = createButton("", 902, 157, 230, 57);
        this.add(deleteButton);
    }

    // Create a JButton with specified properties
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

    // Create a JLabel with specified text and position
    private JLabel createDataLabel(String text, int x, int y) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setBounds(x, y, 280, 30);
        label.setFont(new Font("Arial", Font.PLAIN, 26));
        return label;
    }

    // Update page with current account data
    public void updatePage() {
        dataBalanceLabel.setText(String.format("%.2f", currentAccount.getBalance()));
    }


    // Show a message dialog
    public void showMessage(String accountNotFound, String error) {
    }

    // Show an error message dialog
    public void showError(String accountNotFound, String error) {
    }

    // Show a message dialog
    public void showMessageDialog(Page07_Account any, String accountNotFound, String s, int i) {

    }

    // Main method for testing Page07_Account class
    public static void main(String[] args) {
//        Account exampleAccount = new SavingAccount("Example Account", 1000, 0.02, 24, Period.ofYears(1));
//        Account exampleAccount = new PiggyBank("Example Account", 1000);
        Account exampleAccount = new CurrentAccount("Example Account", 1000, 0.02, 24);
        Page07_Account window = new Page07_Account(exampleAccount);
        window.setVisible(true);
    }

}
