package com.virtualbank.ui;

import com.virtualbank.controller.ParentHomeController;
import com.virtualbank.service.TaskService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class Window3_CreateNewTask extends JFrame {


    private JTextField taskName_textField; // 任务名称/标题输入
    private JTextField content_textField; // 任务内容输入
    private JTextField money_textField; // 任务奖励金额输入
    private JTextField childName_textField; // 任务对象/小孩输入
    private JTextField start_textField; // 开始时间输入
    private JTextField due_textField; // 结束时间输入
    private JButton confirmButton; // 确认按钮

    public JTextField getTaskName_textField() {
        return taskName_textField;
    }

    public JTextField getContent_textField() {
        return content_textField;
    }

    public JTextField getMoney_textField() {
        return money_textField;
    }

    public JTextField getChildName_textField() {
        return childName_textField;
    }

    public JTextField getStart_textField() {
        return start_textField;
    }

    public JTextField getDue_textField() {
        return due_textField;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public Window3_CreateNewTask() {
        final int window_width = 800;
        final int window_height = 701;
        setBounds(0, 0, window_width, window_height);
        setResizable(false);
        setLayout(null);
        setTitle("JoyBank - Creating a New Task");
        ImageIcon loginBackgroundImageIcon = new ImageIcon("images/CreateTaskBackground.png"); // 添加背景图
        JLabel backgroundLabel = new JLabel(loginBackgroundImageIcon);
        backgroundLabel.setBounds(0, 0, window_width, window_height);
        getContentPane().add(backgroundLabel);


        // 任务名称/标题输入框
        taskName_textField = new JTextField();
        taskName_textField.setBounds(380, 120, 200, 50);
        Font font = new Font(taskName_textField.getFont().getName(), Font.PLAIN, 20);
        taskName_textField.setFont(font);
        backgroundLabel.add(taskName_textField);

        // 任务内容输入框
        content_textField = new JTextField();
        content_textField.setBounds(380, 180, 200, 50);
        content_textField.setFont(font);
        backgroundLabel.add(content_textField);

        // 金额输入框
        money_textField = new JTextField();
        money_textField.setBounds(380, 240, 200, 50);
        money_textField.setFont(font);
        backgroundLabel.add(money_textField);

        // 任务对象/小孩输入框
        childName_textField = new JTextField();
        childName_textField.setBounds(380, 305, 200, 50);
        childName_textField.setFont(font);
        backgroundLabel.add(childName_textField);

        // 开始时间输入框
        start_textField = new JTextField();
        start_textField.setBounds(380, 360, 200, 50);
        start_textField.setFont(font);
        backgroundLabel.add(start_textField);

        // 结束时间输入框
        due_textField = new JTextField();
        due_textField.setBounds(380, 430, 200, 50);
        due_textField.setFont(font);
        backgroundLabel.add(due_textField);

        // 确认按钮
        confirmButton = new JButton();
        confirmButton.setBounds(310, 595, 200, 55);
        ImageIcon confirmButtonIcon = new ImageIcon("images/ConfirmButton.png");
        confirmButton.setIcon(confirmButtonIcon);
        backgroundLabel.add(confirmButton);

        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                // 页面2关闭时重新加载页面1
                Page04_ParentHome parentHomeUI = new Page04_ParentHome();
                TaskService taskService = new TaskService();
                new ParentHomeController(taskService, parentHomeUI);
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        Window3_CreateNewTask window3CreateNewTask = new Window3_CreateNewTask();
    }
}
