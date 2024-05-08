package com.virtualbank.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Page01_Login extends JFrame {

    private JTextField username_textField; // 用户名输入框
    private JPasswordField password_textField;
    private JButton confirmButton;
    private JButton registerButton;

    public JTextField getUsername_textField() {
        return username_textField;
    }

    public JPasswordField getPassword_textField() {
        return password_textField;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public Page01_Login() {
        final int window_width = 1260;
        final int window_height = 780;
        setBounds(0, 0, window_width, window_height);
        setTitle("JoyBank - Login Page");
        setResizable(false);
        setLayout(null);
        ImageIcon loginBackgroundImageIcon = new ImageIcon("images/LoginBackground.png"); // 添加背景图
        JLabel backgroundLabel = new JLabel(loginBackgroundImageIcon);
        backgroundLabel.setBounds(0, 0, window_width, window_height);
        getContentPane().add(backgroundLabel);

        // 输入框
        Border border = BorderFactory.createLineBorder(new Color(0x3d6de1), 2);
        username_textField = new JTextField();
        username_textField.setBounds(450, 220, 360, 50);
        Font font = new Font(username_textField.getFont().getName(), Font.PLAIN, 20); // 创建一个新字体对象，设置大小为20
        username_textField.setFont(font);
        username_textField.setBorder(border);
        backgroundLabel.add(username_textField);

        password_textField = new JPasswordField();
        password_textField.setBounds(450, 300, 360, 50);
        password_textField.setFont(font);
        password_textField.setBorder(border);
        backgroundLabel.add(password_textField);

        // 确认按钮
        confirmButton = new JButton();
        confirmButton.setBounds(530, 375, 200, 55);
        ImageIcon confirmButtonIcon = new ImageIcon("images/ConfirmButton.png");
        confirmButton.setIcon(confirmButtonIcon);
        backgroundLabel.add(confirmButton);

        // 注册按钮
        registerButton = new JButton("Register");
        registerButton.setBounds(580, 435, 122, 44);
        ImageIcon registerButtonIcon = new ImageIcon("images/RegisterButton.png");
        registerButton.setIcon(registerButtonIcon);
        backgroundLabel.add(registerButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        Page01_Login page01Login = new Page01_Login();
    }
}
