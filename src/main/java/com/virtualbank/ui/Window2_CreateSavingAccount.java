package com.virtualbank.ui;

import com.virtualbank.model.Pair;
import com.virtualbank.parameters.Interest;

import java.util.ArrayList;
import java.util.List;
import javax.swing.border.Border;
import java.time.Period;

import javax.swing.*;
import java.awt.*;

public class Window2_CreateSavingAccount extends JFrame {

    private JTextField money_textField; // 金额输入框
    private JComboBox<String> comboBox; // 定期利率期限选项
    private JButton confirmButton; // 确认按钮

    public JTextField getMoney_textField() {
        return money_textField;
    }

    public JComboBox<String> getComboBox() {
        return comboBox;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public Window2_CreateSavingAccount() {
        // 页面 window
        final int window_width = 800;
        final int window_height = 504;
        this.setBounds(0, 0, window_width, window_height);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("JoyBank - Creating a New Saving Account");
        ImageIcon loginBackgroundImageIcon = new ImageIcon("images/CreateSavingAccount.png"); // 添加背景图
        JLabel backgroundLabel = new JLabel(loginBackgroundImageIcon);
        backgroundLabel.setBounds(0, 0, window_width, window_height);
        this.getContentPane().add(backgroundLabel);

        // 金额输入框
        Border border = BorderFactory.createLineBorder(new Color(0x3d6de1), 2);
        money_textField = new JTextField();
        money_textField.setBounds(380, 120, 200, 50);
        Font font = new Font(money_textField.getFont().getName(), Font.PLAIN, 20);
        money_textField.setFont(font);
        money_textField.setBorder(border);
        backgroundLabel.add(money_textField);

        // 初始化下拉选择框
        comboBox = new JComboBox<>();
        for (Pair<Double, Period> option : Interest.savingInterest) {
            comboBox.addItem(formatOption(option));
        }
        comboBox.setBounds(380, 190, 200, 50);
        backgroundLabel.add(comboBox);

        // 确认按钮
        confirmButton = new JButton();
        confirmButton.setBounds(290, 410, 200, 55);
        ImageIcon confirmButtonIcon = new ImageIcon("images/ConfirmButton.png");
        confirmButton.setIcon(confirmButtonIcon);
        confirmButton.setBorder(null);
        confirmButton.setContentAreaFilled(false);
        confirmButton.setBorderPainted(false);
        backgroundLabel.add(confirmButton);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public double getEnteredAmount() {
        try {
            return Double.parseDouble(money_textField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid amount format", "Error", JOptionPane.ERROR_MESSAGE);
            return 0; // 返回0或其他适当的错误值
        }
    }

    private String formatOption(Pair<Double, Period> option) {
        double rate = option.getKey() * 100; // 将利率转换为百分比形式
        Period period = option.getValue();
        long years = period.getYears(); // 获取年份
        // 格式化字符串，显示利率和总年数
        return String.format("%.1f%% per year, in total %d years", rate, years);
    }

    public Pair<Double, Period> getSelectedInterestOption() {
        int selectedIndex = comboBox.getSelectedIndex();
        if (selectedIndex >= 0) {
            return Interest.savingInterest.get(selectedIndex);
        }
        return null;
    }

    public static void main(String[] args) {
        Window2_CreateSavingAccount window2CreateSavingAccount = new Window2_CreateSavingAccount();
    }
}
