/**
 * The window for a child user to create a new saving account.
 * @author Yunbo Jia, Yuanzhe Ma
 * @version 3.0 2024-5-21
 */
package com.virtualbank.ui;

import com.virtualbank.model.Pair;
import com.virtualbank.parameters.Interest;

import java.util.ArrayList;
import java.util.List;
import javax.swing.border.Border;
import java.time.Period;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the window for creating a new saving account in the JoyBank application.
 * This class extends {@link JFrame} and includes fields for entering the initial deposit amount,
 * selecting the interest rate option, and confirming the creation of the account.
 *
 * @see JFrame
 */
public class Window2_CreateSavingAccount extends JFrame {

    private JTextField money_textField;
    private JComboBox<String> comboBox;
    private JButton confirmButton;

    /**
     * Gets the text field for entering the initial deposit amount.
     *
     * @return the text field for entering the initial deposit amount
     */
    public JTextField getMoney_textField() {
        return money_textField;
    }

    /**
     * Gets the combo box for selecting the interest rate option.
     *
     * @return the combo box for selecting the interest rate option
     */
    public JComboBox<String> getComboBox() {
        return comboBox;
    }

    /**
     * Gets the confirmation button.
     *
     * @return the confirmation button
     */
    public JButton getConfirmButton() {
        return confirmButton;
    }

    /**
     * Constructs a new window for creating a saving account.
     */
    public Window2_CreateSavingAccount() {
        final int window_width = 800;
        final int window_height = 504;
        this.setBounds(0, 0, window_width, window_height);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("JoyBank - Creating a New Saving Account");
        ImageIcon loginBackgroundImageIcon = new ImageIcon(getClass().getResource("src/main/resources/images/CreateSavingAccount.png"));
        JLabel backgroundLabel = new JLabel(loginBackgroundImageIcon);
        backgroundLabel.setBounds(0, 0, window_width, window_height);
        this.getContentPane().add(backgroundLabel);

        Border border = BorderFactory.createLineBorder(new Color(0x3d6de1), 2);
        money_textField = new JTextField();
        money_textField.setBounds(380, 120, 200, 50);
        Font font = new Font(money_textField.getFont().getName(), Font.PLAIN, 20);
        money_textField.setFont(font);
        money_textField.setBorder(border);
        backgroundLabel.add(money_textField);

        comboBox = new JComboBox<>();
        for (Pair<Double, Period> option : Interest.savingInterest) {
            comboBox.addItem(formatOption(option));
        }
        comboBox.setBounds(380, 190, 200, 50);
        backgroundLabel.add(comboBox);

        confirmButton = new JButton();
        confirmButton.setBounds(290, 410, 200, 55);
        ImageIcon confirmButtonIcon = new ImageIcon(getClass().getResource("src/main/resources/images/confirm2.png"));
        confirmButton.setIcon(confirmButtonIcon);
        confirmButton.setBorder(null);
        confirmButton.setContentAreaFilled(false);
        confirmButton.setBorderPainted(false);
        backgroundLabel.add(confirmButton);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Retrieves the entered amount from the text field.
     *
     * @return the entered amount, or 0 if the format is invalid
     */
    public double getEnteredAmount() {
        try {
            return Double.parseDouble(money_textField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid amount format", "Error", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    /**
     * Formats the interest rate option for display in the combo box.
     *
     * @param option the interest rate option
     * @return the formatted string for the interest rate option
     */
    private String formatOption(Pair<Double, Period> option) {
        double rate = option.getKey() * 100;
        Period period = option.getValue();
        long years = period.getYears();
        return String.format("%.1f%% per year, in total %d years", rate, years);
    }

    /**
     * Retrieves the selected interest rate option from the combo box.
     *
     * @return the selected interest rate option, or null if none is selected
     */
    public Pair<Double, Period> getSelectedInterestOption() {
        int selectedIndex = comboBox.getSelectedIndex();
        if (selectedIndex >= 0) {
            return Interest.savingInterest.get(selectedIndex);
        }
        return null;
    }

    /**
     * The main method to create and display the window.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Window2_CreateSavingAccount window2CreateSavingAccount = new Window2_CreateSavingAccount();
    }
}
