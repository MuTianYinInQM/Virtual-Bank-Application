package com.virtualbank.ui;

import java.awt.Font;
import javax.swing.*;

public class Page07b_PiggyBank extends JFrame {
    private JButton exitButton;
    private JLabel id1;
    private JLabel idLabel;
    private JLabel balance1;
    private JLabel balanceLabel;
    private JLabel type1;
    private JLabel typeLabel;
    private JLabel interest1;
    private JLabel interestLabel;
    private JButton historyButton;
    private JButton transferButton;
    private JButton saveButton;
    private JButton consumeButton;
    private JButton deleteButton;

    public Page07b_PiggyBank() {
        super("Piggy Bank Page"); // Set the title of the JFrame

        // window settings
        final int window_width = 1260;
        final int window_height = 780;
        this.setBounds(0, 0, window_width, window_height);
        this.setResizable(false);
        this.setLayout(null);
        ImageIcon goalBackground = new ImageIcon("images/Piggy_Bank.png");
        JLabel backgroundLabel = new JLabel(goalBackground);
        backgroundLabel.setBounds(0, 0, window_width, window_height);
        this.setContentPane(backgroundLabel);

        // exit_button
        this.exitButton = new JButton("Exit");
        this.exitButton.setBounds(20, 20, 100, 50);
        Font exitButton_font = new Font("Arial", Font.PLAIN, 20);
        this.exitButton.setFont(exitButton_font);
        this.exitButton.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(this.exitButton);

        // Labels and Text Fields
        Font boldLabelFont = new Font("Arial", Font.BOLD, 28);
        Font plainLabelFont = new Font("Arial", Font.PLAIN, 28);

        createLabel(this.id1 = new JLabel("ID"), 340, 140, boldLabelFont);
        createLabel(this.balance1 = new JLabel("Balance"), 340, 205, boldLabelFont);
        createLabel(this.type1 = new JLabel("Type"), 340, 270, boldLabelFont);
        createLabel(this.interest1 = new JLabel("Interest"), 340, 335, boldLabelFont);

        createDataLabel(this.idLabel = new JLabel(), 540, 140, plainLabelFont);
        createDataLabel(this.balanceLabel = new JLabel(), 540, 205, plainLabelFont);
        createDataLabel(this.typeLabel = new JLabel("Piggy Bank"), 540, 270, plainLabelFont);
        createDataLabel(this.interestLabel = new JLabel(), 540, 335, plainLabelFont);

        // Buttons
        this.historyButton = createButton("images/history_Button.png", 340, 420);
        this.transferButton = createButton("images/transfer_Button.png", 660, 420);
        this.saveButton = createButton("images/save_Button.png", 340, 520);
        this.consumeButton = createButton("images/consume_Button.png", 660, 520);
        this.deleteButton = createButton("images/delete_Button.png", 500, 630);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void createLabel(JLabel label, int x, int y, Font font) {
        label.setBounds(x, y, 200, 50);
        label.setFont(font);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label);
    }

    private void createDataLabel(JLabel label, int x, int y, Font font) {
        label.setBounds(x, y, 300, 50);
        label.setFont(font);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label);
    }

    private JButton createButton(String iconPath, int x, int y) {
        JButton button = new JButton(new ImageIcon(iconPath));
        button.setBounds(x, y, 233, 85);
        button.setBorderPainted(false);
        this.add(button);
        return button;
    }

    public static void main(String[] args) {
        new Page07b_PiggyBank();
    }
}
