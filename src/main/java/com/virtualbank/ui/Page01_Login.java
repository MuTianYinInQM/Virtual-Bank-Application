/**
 * The UI page for a user to log in or register for a new account
 * @author Yunbo Jia
 * @version 2.0 2024-5-21
 */
package com.virtualbank.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Page01_Login is the first UI page for users to either log in or register, a subclass of JFrame.
 * This UI page includes text fields for username and password
 * as well as a confirm button and a register button.
 *
 * @see JFrame
 */
public class Page01_Login extends JFrame {

    /** Username input field */
    private JTextField username_textField;
    /** Password input field */
    private JPasswordField password_textField;
    /** Confirm button */
    private JButton confirmButton;
    /** Register button */
    private JButton registerButton;

    /**
     * Gets the username input field.
     *
     * @return the JTextField object for the username input field
     */
    public JTextField getUsername_textField() {
        return username_textField;
    }

    /**
     * Gets the password input field.
     *
     * @return the JPasswordField object for the password input field
     */
    public JPasswordField getPassword_textField() {
        return password_textField;
    }

    /**
     * Gets the confirm button.
     *
     * @return the JButton object for the confirm button
     */
    public JButton getConfirmButton() {
        return confirmButton;
    }

    /**
     * Gets the register button.
     *
     * @return the JButton object for the register button
     */
    public JButton getRegisterButton() {
        return registerButton;
    }

    /**
     * Constructs a new Page01_Login frame.
     * Sets up the frame size, title, layout, initializes components,
     * and adds them to the frame.
     */
    public Page01_Login() {
        final int window_width = 1260;
        final int window_height = 780;
        setBounds(0, 0, window_width, window_height);
        setTitle("JoyBank - Login Page");
        setResizable(false);
        setLayout(null);
        ImageIcon loginBackgroundImageIcon = new ImageIcon(getClass().getResource("/images/LoginBackground.png"));
        JLabel backgroundLabel = new JLabel(loginBackgroundImageIcon);
        backgroundLabel.setBounds(0, 0, window_width, window_height);
        getContentPane().add(backgroundLabel);

        // text fields
        Border border = BorderFactory.createLineBorder(new Color(0x3d6de1), 2);
        username_textField = new JTextField();
        username_textField.setBounds(450, 310, 360, 50);
        Font font = new Font(username_textField.getFont().getName(), Font.PLAIN, 20);
        username_textField.setFont(font);
        username_textField.setBorder(border);
        backgroundLabel.add(username_textField);

        password_textField = new JPasswordField();
        password_textField.setBounds(450, 385, 360, 50);
        password_textField.setFont(font);
        password_textField.setBorder(border);
        backgroundLabel.add(password_textField);

        JLabel registerNote1 = new JLabel("If you haven't had an account,");
        JLabel registerNote2 = new JLabel("click on \"Register\" below to register one.");
        registerNote1.setForeground(new Color(0x959aa4));
        registerNote2.setForeground(new Color(0x959aa4));
        registerNote1.setBounds(505, 450, 1000, 20);
        registerNote2.setBounds(465, 468, 1000, 20);
        registerNote1.setFont(new Font(registerNote1.getFont().getName(), Font.PLAIN, 17));
        registerNote2.setFont(new Font(registerNote1.getFont().getName(), Font.PLAIN, 17));
        backgroundLabel.add(registerNote1);
        backgroundLabel.add(registerNote2);

        // confirm button
        confirmButton = new JButton();
        confirmButton.setBounds(440, 505, 200, 55);
        ImageIcon confirmButtonIcon = new ImageIcon(getClass().getResource("/images/ConfirmButton.png"));
        confirmButton.setIcon(confirmButtonIcon);
        confirmButton.setBorder(null);
        confirmButton.setContentAreaFilled(false);
        backgroundLabel.add(confirmButton);

        // register button
        registerButton = new JButton();
        registerButton.setBounds(630, 505, 200, 55);
        ImageIcon registerButtonIcon = new ImageIcon(getClass().getResource("/images/RegisterButton.png"));
        registerButton.setIcon(registerButtonIcon);
        registerButton.setBorder(null);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        backgroundLabel.add(registerButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Creates and displays the Page01_Login frame.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Page01_Login page01Login = new Page01_Login();
    }
}
