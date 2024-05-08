package com.virtualbank.ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

public class Page02_Register extends JFrame{
    private JTextField username_textField = new JTextField();
    private JPasswordField password_textField = new JPasswordField();
    private JCheckBox childButton = new JCheckBox();
    private JCheckBox parentButton = new JCheckBox();
    private JButton confirmButton = new JButton();
    private JButton exitButton = new JButton();

    public JTextField getUsername_textField() {
        return username_textField;
    }

    public JPasswordField getPassword_textField() {
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

    public Page02_Register() {
        // 页面 window
        final int window_width = 1260;
        final int window_height = 780;
        setBounds(0, 0, window_width, window_height);
        setResizable(false);
        setLayout(null);
        setTitle("JoyBank - Register Page");
        ImageIcon loginBackgroundImageIcon = new ImageIcon("images/RegisterPage.png"); // 添加背景图
        JLabel backgroundLabel = new JLabel(loginBackgroundImageIcon);
        backgroundLabel.setBounds(0, 0, window_width, window_height);
        getContentPane().add(backgroundLabel);

        // 输入框
        Border border = BorderFactory.createLineBorder(new Color(0x3d6de1), 2);
        username_textField.setBounds(676, 245, 420, 50);
        Font font = new Font(username_textField.getFont().getName(), Font.PLAIN, 20); // 创建一个新字体对象，设置大小为20
        username_textField.setFont(font);
        username_textField.setBorder(border);
        backgroundLabel.add(username_textField);

        password_textField.setBounds(676, 390, 420, 50);
        password_textField.setBorder(border);
        password_textField.setFont(font);
        backgroundLabel.add(password_textField);

        // 选择父母或孩子的按钮
        childButton.setBounds(92, 280, 40, 40);
        parentButton.setBounds(92, 450, 40, 40);
        // 创建一个按钮组
        ButtonGroup group = new ButtonGroup();
        group.add(childButton);
        group.add(parentButton);
        backgroundLabel.add(childButton);
        backgroundLabel.add(parentButton);

        // 确认按钮
        confirmButton.setBounds(530, 510, 200, 55);
        ImageIcon confirmButtonIcon = new ImageIcon("images/ConfirmButton.png");
        confirmButton.setIcon(confirmButtonIcon);
        confirmButton.setBorder(null);
        backgroundLabel.add(confirmButton);

        // 返回上一级，LoginPage的按钮Exit
        exitButton.setBounds(20, 20, 114, 50);
        exitButton.setFont(font);
        ImageIcon exitButtonIcon = new ImageIcon("images/ExitButtonImage.png");
        exitButton.setIcon(exitButtonIcon);
        exitButton.setBorder(null);
        backgroundLabel.add(exitButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        Page02_Register page02Register = new Page02_Register();
    }
}
