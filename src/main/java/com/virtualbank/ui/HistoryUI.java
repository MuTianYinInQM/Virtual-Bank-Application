package com.virtualbank.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class HistoryUI extends JFrame {
    private JList<String> transactionList;
    private JButton returnButton;

    public HistoryUI() {
        createUI();
        setTitle("Joy Bank - Transaction History");
        final int window_width = 1260;
        final int window_height = 841;
        setSize(window_width, window_height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(255, 255, 230)); // Peach color background
    }

    private void createUI() {
        // Initialize the transaction list
        transactionList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(transactionList);
        scrollPane.setBounds(30, 50, 1200, 650);  // Adjust the size and position
        add(scrollPane);

        // Initialize the return button
        returnButton = new JButton("Return");
        returnButton.setBounds(1050, 720, 200, 40); // Adjust the size and position
        add(returnButton);
    }

    /**
     * Method to set the transaction list data, allows the controller to update the list dynamically.
     * @param transactions List of transaction details, each transaction is a formatted string.
     */
    public void setTransactionList(List<String> transactions) {
        transactionList.setListData(transactions.toArray(new String[0]));
    }

    /**
     * Getter for the return button, allows the controller to attach event handlers.
     * @return the instance of the return button.
     */
    public JButton getReturnButton() {
        return returnButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HistoryUI().setVisible(true);
        });
    }
}
