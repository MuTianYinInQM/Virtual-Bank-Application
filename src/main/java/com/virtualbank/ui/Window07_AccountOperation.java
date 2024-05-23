package com.virtualbank.ui;

import com.virtualbank.model.AccountOperationType;
import com.virtualbank.model.account.Account;

import java.awt.*;
import java.util.UUID;
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
    private AccountOperationType operationType;

    private Account account;

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public AccountOperationType getOperationType() {
        return operationType;
    }

    public Account getAccount() {
        return account;
    }

    public Window07_AccountOperation(Account account, AccountOperationType type) {
        this.account = account;
        this.operationType = type;
        this.amountBox = new JTextField();
        this.descriptionBox = new JTextField();
        this.confirmButton = new JButton();

        final int this_width = 800;
        final int this_height = 480;
        this.setBounds(300, 200, this_width, this_height);
        this.setResizable(false);
        this.setLayout(null);
        this.setBackground(new Color(0xF8F6E9));

        String title = null;
        if (operationType == AccountOperationType.TRANSFER) {
            title = "JoyBank - Transfer to PiggyBank";
        } else if (operationType == AccountOperationType.CONSUME) {
            title = "JoyBank - Consume from PiggyBank";
        } else if (operationType == AccountOperationType.SAVE) {
            title = "JoyBank - Save into PiggyBank";
        }
        this.setTitle(title);


        ImageIcon thisBackground = new ImageIcon("images/this_Background.png");
        JLabel thisBackgroundLabel = new JLabel(thisBackground);
        thisBackgroundLabel.setBounds(0, 0, this_width, this_height);
        this.setContentPane(thisBackgroundLabel);

        Font plainLabelFont = new Font("Arial", Font.PLAIN, 20);

        // amountLabel
        String amountLabelText = null;
        if (operationType == AccountOperationType.TRANSFER) {
            amountLabelText = "*Transfer Amount*";
        }
        else if (operationType == AccountOperationType.CONSUME) {
            amountLabelText = "*CONSUME Amount*";
        }
        else if (operationType == AccountOperationType.SAVE) {
            amountLabelText = "*SAVE Amount*";
        }

        JLabel amountLabel = new JLabel(amountLabelText);
        amountLabel.setBounds(200, 60, 200, 40);
        amountLabel.setFont(plainLabelFont);
        amountLabel.setHorizontalAlignment(SwingConstants.LEFT);
        this.add(amountLabel);

        // amountBox
        this.amountBox.setBounds(200, 100, 400, 40);
        this.amountBox.setFont(plainLabelFont);
        this.add(this.amountBox);

        // descriptionLabel
        JLabel descriptionLabel = new JLabel("**Description**");
        this.add(descriptionLabel);
        descriptionLabel.setBounds(200, 180, 200, 40);
        descriptionLabel.setFont(plainLabelFont);
        descriptionLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // descriptionBox
        this.add(this.descriptionBox);
        this.descriptionBox.setBounds(200, 220, 400, 40);
        this.descriptionBox.setFont(plainLabelFont);

        // confirmButton
        this.add(this.confirmButton);
        this.confirmButton.setBounds(330, 300, 140, 55);
        ImageIcon confirmButtonIcon = new ImageIcon("images/confirm2.png");
        confirmButton.setIcon(confirmButtonIcon);
        confirmButton.setBorder(null);
        confirmButton.setContentAreaFilled(false);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public double getAmount() {
        String text = this.amountBox.getText();
        if (text.matches("-?\\d+(\\.\\d+)?")) {
            return Double.parseDouble(text);
        } else {
            throw new NumberFormatException("The transfer amount is not a valid number.");
        }
    }

    public String getDescription() {
        return this.descriptionBox.getText();
    }

    public static void main(String[] args) {
        Window07_AccountOperation transfer = new Window07_AccountOperation(null, AccountOperationType.TRANSFER);
    }
}
