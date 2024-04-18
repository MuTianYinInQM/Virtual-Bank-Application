package com.virtualbank.ui;


import javax.swing.*;
import java.awt.*;

public class AccountUI extends JFrame {
    private JLabel accountLabel;
    private JTextField accountField;
    private JButton saveButton;
    private JButton returnButton;

    public AccountUI() {
        createUI();
        setTitle("Joy Bank - Account Management");
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
        // Account Label
        accountLabel = new JLabel("Account ID:");
        accountLabel.setBounds(50, 100, 120, 30);
        add(accountLabel);

        // Account Text Field
        accountField = new JTextField();
        accountField.setBounds(180, 100, 200, 30);
        add(accountField);

        // Save Button
        saveButton = new JButton("Save Changes");
        saveButton.setBounds(50, 150, 150, 30);
        add(saveButton);

        // Return Button
        returnButton = new JButton("Return");
        returnButton.setBounds(50, 200, 150, 30);
        add(returnButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AccountUI frame = new AccountUI();
            frame.setVisible(true);
        });
    }
}

