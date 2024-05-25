/**
 * The UI page for a user to register for a new account
 * @author Yunbo Jia
 * @version 2.0 2024-5-21
 */
package com.virtualbank.ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

/**
 * Page02_Register is the UI page for users to register, a subclass of JFrame.
 * This UI page includes text fields for user's new account's username and password
 * as well as a confirm button
 * and check boxes for users to choose the new account belongs to either a parent or a child
 *
 * @see JFrame
 */
public class Page02_Register extends JFrame{

    /** Username input field */
    private JTextField username_textField = new JTextField();
    /** Password input field */
    private JPasswordField password_textField = new JPasswordField();
    /** Child check box*/
    private JCheckBox childButton = new JCheckBox();
    /** Parent check box*/
    private JCheckBox parentButton = new JCheckBox();
    /** Confirm button */
    private JButton confirmButton = new JButton();
    /** Exit button */
    private JButton exitButton = new JButton();

    /**
     * Gets the username text field.
     *
     * @return the username text field.
     */
    public JTextField getUsername_textField() {
        return username_textField;
    }

    /**
     * Gets the password text field.
     *
     * @return the password text field.
     */
    public JPasswordField getPassword_textField() {
        return password_textField;
    }

    /**
     * Gets the child button.
     *
     * @return the child button.
     */
    public JCheckBox getChildButton() {
        return childButton;
    }

    /**
     * Gets the parent button.
     *
     * @return the parent button.
     */
    public JCheckBox getParentButton() {
        return parentButton;
    }

    /**
     * Gets the confirm button.
     *
     * @return the confirm button.
     */
    public JButton getConfirmButton() {
        return confirmButton;
    }

    /**
     * Gets the exit button.
     *
     * @return the exit button.
     */
    public JButton getExitButton() {
        return exitButton;
    }

    /**
     * Constructs the registration page.
     * Sets up the JFrame properties, initializes UI components, and adds them to the frame.
     */
    public Page02_Register() {
        // window
        final int window_width = 1260;
        final int window_height = 780;
        setBounds(0, 0, window_width, window_height);
        setResizable(false);
        setLayout(null);
        setTitle("JoyBank - Register Page");
        ImageIcon loginBackgroundImageIcon = new ImageIcon("src/main/resources/images/RegisterPage.png");
        JLabel backgroundLabel = new JLabel(loginBackgroundImageIcon);
        backgroundLabel.setBounds(0, 0, window_width, window_height);
        getContentPane().add(backgroundLabel);

        // text fields
        Border border = BorderFactory.createLineBorder(new Color(0x3d6de1), 2);
        username_textField.setBounds(676, 245, 420, 50);
        Font font = new Font(username_textField.getFont().getName(), Font.PLAIN, 20);
        username_textField.setFont(font);
        username_textField.setBorder(border);
        backgroundLabel.add(username_textField);

        password_textField.setBounds(676, 390, 420, 50);
        password_textField.setBorder(border);
        password_textField.setFont(font);
        backgroundLabel.add(password_textField);

        // parent and child check boxes
        childButton.setBounds(92, 280, 40, 40);
        parentButton.setBounds(92, 450, 40, 40);
        childButton.setContentAreaFilled(false);
        childButton.setBorderPainted(false);
        parentButton.setContentAreaFilled(false);
        parentButton.setBorderPainted(false);
        // a button group
        ButtonGroup group = new ButtonGroup();
        group.add(childButton);
        group.add(parentButton);
        backgroundLabel.add(childButton);
        backgroundLabel.add(parentButton);

        // confirm button
        confirmButton.setBounds(530, 510, 200, 55);
        ImageIcon confirmButtonIcon = new ImageIcon("src/main/resources/images/ConfirmButton.png");
        confirmButton.setIcon(confirmButtonIcon);
        confirmButton.setBorder(null);
        confirmButton.setContentAreaFilled(false);
        confirmButton.setBorderPainted(false);
        backgroundLabel.add(confirmButton);

        // exit button
        exitButton.setBounds(20, 20, 114, 50);
        exitButton.setFont(font);
        ImageIcon exitButtonIcon = new ImageIcon("src/main/resources/images/ExitButtonImage.png");
        exitButton.setIcon(exitButtonIcon);
        exitButton.setBorder(null);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        backgroundLabel.add(exitButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Main method to launch the registration page.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Page02_Register page02Register = new Page02_Register();
    }
}
