/**
 * The window for a child user to choose the type of the new account.
 * @author Yunbo Jia
 * @version 2.0 2024-5-21
 */
package com.virtualbank.ui;

import javax.swing.*;

/**
 * Represents the first window of the JoyBank application where users choose their account type.
 * This class extends {@link JFrame} and includes options for selecting a current account or a saving account,
 * as well as a confirmation button.
 *
 * @see JFrame
 */
public class Window1_ChooseAccountType extends JFrame {
    private JCheckBox currentAccountButton = new JCheckBox();
    private JCheckBox savingAccountButton = new JCheckBox();
    private JButton confirmButton = new JButton();

    /**
     * Gets the button for selecting the current account option.
     *
     * @return the button for the current account
     */
    public JCheckBox getCurrentAccountButton() {
        return currentAccountButton;
    }

    /**
     * Gets the button for selecting the saving account option.
     *
     * @return the button for the saving account
     */
    public JCheckBox getSavingAccountButton() {
        return savingAccountButton;
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
     * Constructs a new window for choosing the account type.
     */
    public Window1_ChooseAccountType() {
        final int window_width = 800;
        final int window_height = 504;
        this.setBounds(0, 0, window_width, window_height);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("JoyBank - Choose Your Account Type");

        ImageIcon loginBackgroundImageIcon = new ImageIcon("images/ChooseAccountType.png");
        JLabel backgroundLabel = new JLabel(loginBackgroundImageIcon);
        backgroundLabel.setBounds(0, 0, window_width, window_height);
        this.getContentPane().add(backgroundLabel);

        currentAccountButton.setBounds(175, 340, 60, 60);
        savingAccountButton.setBounds(560, 340, 60, 60);
        currentAccountButton.setContentAreaFilled(false);
        currentAccountButton.setBorderPainted(false);
        savingAccountButton.setContentAreaFilled(false);
        savingAccountButton.setBorderPainted(false);
        ButtonGroup group = new ButtonGroup();
        group.add(currentAccountButton);
        group.add(savingAccountButton);
        backgroundLabel.add(currentAccountButton);
        backgroundLabel.add(savingAccountButton);

        confirmButton.setBounds(320, 400, 140, 55);
        ImageIcon confirmButtonIcon = new ImageIcon("images/confirm2.png");
        confirmButton.setIcon(confirmButtonIcon);
        confirmButton.setBorder(null);
        confirmButton.setContentAreaFilled(false);
//        confirmButton.setBorderPainted(false);
        backgroundLabel.add(confirmButton);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * The main method to create and display the window.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Window1_ChooseAccountType chooseAccountTypeWindow = new Window1_ChooseAccountType();
        chooseAccountTypeWindow.setVisible(true);
    }
}
