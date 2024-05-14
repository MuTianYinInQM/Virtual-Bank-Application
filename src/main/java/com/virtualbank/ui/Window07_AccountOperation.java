package com.virtualbank.ui;

import com.virtualbank.model.AccountOperationType;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class Window07_AccountOperation extends JFrame {
    private JTextField amountBox;
    private JTextField descriptionBox;
    private JButton confirmButton;

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public Window07_AccountOperation(AccountOperationType type) {
        super();
        this.amountBox = new JTextField();
        this.descriptionBox = new JTextField();
        this.confirmButton = new JButton();

        final int this_width = 600;
        final int this_height = 378;
        this.setBounds(330, 200, this_width, this_height);
        this.setResizable(false);
        this.setLayout(null);

        String title = null;
        if (type == AccountOperationType.TRANSFER) {
            title = "JoyBank - Transfer to PiggyBank";
        } else if (type == AccountOperationType.CONSUME) {
            title = "JoyBank - Consume from PiggyBank";
        } else if (type == AccountOperationType.SAVE) {
            title = "JoyBank - Save into PiggyBank";
        }
        this.setTitle(title);


        ImageIcon thisBackground = new ImageIcon("images/this_Background.png");
        JLabel thisBackgroundLabel = new JLabel(thisBackground);
        thisBackgroundLabel.setBounds(0, 0, this_width, this_height);
        this.setContentPane(thisBackgroundLabel);

        Font plainLabelFont = new Font("Arial", Font.PLAIN, 20);

        // amountBox
        String labelText = null;
        if (type == AccountOperationType.TRANSFER) {
            labelText = "Transfer Amount";
        } else if (type == AccountOperationType.CONSUME) {
            labelText = "CONSUME Amount";
        } else if (type == AccountOperationType.SAVE) {
            labelText = "SAVE Amount";
        }
        JLabel transferLabel = new JLabel(labelText);

        this.add(transferLabel);
        transferLabel.setBounds(60, 80, 120, 40);
        transferLabel.setFont(plainLabelFont);
        transferLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // transferAmountField
        this.add(this.amountBox);
        this.amountBox.setBounds(200, 80, 320, 40);
        this.amountBox.setFont(plainLabelFont);

        // sumLabel
        JLabel sumLabel = new JLabel("Description");
        this.add(sumLabel);
        sumLabel.setBounds(60, 140, 120, 40);
        sumLabel.setFont(plainLabelFont);
        sumLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // descriptionBox
        this.add(this.descriptionBox);
        this.descriptionBox.setBounds(200, 140, 320, 40);
        this.descriptionBox.setFont(plainLabelFont);

        // confirmButton
        this.add(this.confirmButton);
        this.confirmButton.setBounds(240, 220, 120, 40);
        this.confirmButton.setFont(plainLabelFont);
        this.confirmButton.setHorizontalAlignment(SwingConstants.CENTER);
        this.confirmButton.setText("OK");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public double getTransferAmountFieldValue() {
        String text = this.amountBox.getText();
        if (text.matches("\\d+(\\.\\d+)?")) {
            return Double.parseDouble(text);
        } else {
            throw new NumberFormatException("The transfer amount is not a valid number.");
        }
    }

    public String getDescriptionBoxText() {
        return this.descriptionBox.getText();
    }

    public static void main(String[] args) {
        Window07_AccountOperation transfer = new Window07_AccountOperation(AccountOperationType.SAVE);
    }
}
