package com.virtualbank.ui;

import javax.swing.*;

public class Window1_ChooseAccountType extends JFrame {

    private JCheckBox currentAccountButton = new JCheckBox();
    private JCheckBox savingAccountButton = new JCheckBox();
    private JButton confirmButton = new JButton();

    public JCheckBox getCurrentAccountButton() {
        return currentAccountButton;
    }

    public JCheckBox getSavingAccountButton() {
        return savingAccountButton;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public Window1_ChooseAccountType() {
        // 页面 window
        JFrame window = new JFrame(); // 页面窗口
        final int window_width = 800;
        final int window_height = 504;
        window.setBounds(0, 0, window_width, window_height);
        window.setResizable(false);
        window.setLayout(null);
        window.setTitle("JoyBank - Choose Your Account Type");
        ImageIcon loginBackgroundImageIcon = new ImageIcon("images/ChooseAccountType.png"); // 添加背景图
        JLabel backgroundLabel = new JLabel(loginBackgroundImageIcon);
        backgroundLabel.setBounds(0, 0, window_width, window_height);
        window.getContentPane().add(backgroundLabel);

        currentAccountButton.setBounds(175, 340, 60, 60);
        savingAccountButton.setBounds(560, 340, 60, 60);
        // 创建一个按钮组
        ButtonGroup group = new ButtonGroup();
        group.add(currentAccountButton);
        group.add(savingAccountButton);
        backgroundLabel.add(currentAccountButton);
        backgroundLabel.add(savingAccountButton);

        // confirm按钮
        confirmButton.setBounds(280, 400, 200, 55);
        ImageIcon confirmButtonIcon = new ImageIcon("images/ConfirmButton.png");
        confirmButton.setIcon(confirmButtonIcon);
        backgroundLabel.add(confirmButton);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        Window1_ChooseAccountType chooseAccountTypeWindow = new Window1_ChooseAccountType();
        chooseAccountTypeWindow.setVisible(false);
    }
}
