package com.virtualbank.ui;

import javax.swing.*;
import java.awt.*;

public class Page02_Register {
    private JTextField username_textField = new JTextField();
    private JTextField password_textField = new JTextField();
    private JCheckBox childButton = new JCheckBox();
    private JCheckBox parentButton = new JCheckBox();
    private JButton confirmButton = new JButton();
    private JButton exitButton = new JButton("Exit");

    public Page02_Register() {
        // 页面 window
        JFrame window = new JFrame(); // 页面窗口
        final int window_width = 1260;
        final int window_height = 841;
        window.setBounds(0, 0, window_width, window_height);
        window.setResizable(false);
        window.setLayout(null);
        window.setTitle("JoyBank - Register Page");
        ImageIcon loginBackgroundImageIcon = new ImageIcon("Resources/RegisterPage.png"); // 添加背景图
        JLabel backgroundLabel = new JLabel(loginBackgroundImageIcon);
        backgroundLabel.setBounds(0, 0, window_width, window_height);
        window.getContentPane().add(backgroundLabel);

        // 输入框
        username_textField.setBounds(450, 320, 360, 50);
        Font font = new Font(username_textField.getFont().getName(), Font.PLAIN, 20); // 创建一个新字体对象，设置大小为20
        username_textField.setFont(font);
        backgroundLabel.add(username_textField);

        password_textField.setBounds(450, 400, 360, 50);
        password_textField.setFont(font);
        backgroundLabel.add(password_textField);

        // 选择父母或孩子的按钮
        childButton.setBounds(520, 260, 40, 40);
        parentButton.setBounds(710, 260, 40, 40);
        // 创建一个按钮组
        ButtonGroup group = new ButtonGroup();
        group.add(childButton);
        group.add(parentButton);
        backgroundLabel.add(childButton);
        backgroundLabel.add(parentButton);

        // 确认按钮
        confirmButton.setBounds(530, 470, 200, 55);
        ImageIcon confirmButtonIcon = new ImageIcon("Resources/ConfirmButton.png");
        confirmButton.setIcon(confirmButtonIcon);
        backgroundLabel.add(confirmButton);

        // 返回上一级，LoginPage的按钮Exit
        exitButton.setBounds(20, 20, 100, 50);
        exitButton.setFont(font);
        backgroundLabel.add(exitButton);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public JTextField getUsername_textField() {
        return username_textField;
    }

    public JTextField getPassword_textField() {
        return password_textField;
    }

    public JCheckBox getChildButton() {
        return childButton;
    }

    public JCheckBox getParentButton() {
        return parentButton;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public static void main(String[] args) {
        Page02_Register page02Register = new Page02_Register();
    }
}
