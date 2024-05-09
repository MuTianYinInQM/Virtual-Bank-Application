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
        // 设置窗口大小和其他属性
        final int window_width = 800;
        final int window_height = 504;
        this.setBounds(0, 0, window_width, window_height);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("JoyBank - Choose Your Account Type");

        // 添加背景图
        ImageIcon loginBackgroundImageIcon = new ImageIcon("images/ChooseAccountType.png");
        JLabel backgroundLabel = new JLabel(loginBackgroundImageIcon);
        backgroundLabel.setBounds(0, 0, window_width, window_height);
        this.getContentPane().add(backgroundLabel);

        // 设置复选框和按钮
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

    public static void main(String[] args) {
        Window1_ChooseAccountType chooseAccountTypeWindow = new Window1_ChooseAccountType();
        chooseAccountTypeWindow.setVisible(true);
    }
}
