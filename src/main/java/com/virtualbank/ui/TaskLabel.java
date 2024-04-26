package com.virtualbank.ui;

import javax.swing.*;
import java.awt.*;

public class TaskLabel extends JLabel {

    private JButton deleteButton; // 每个任务的删除按钮

    public JButton getDeleteButton() {
        return deleteButton;
    }

    // 修改构造函数以适应新的参数
    public TaskLabel(String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
        Font font = new Font("Arial", Font.PLAIN, 18);
        this.setLayout(null);
        this.setPreferredSize(new Dimension(523, 133)); // 设置标签的首选大小

        // 设置任务名
        JLabel nameLabel = new JLabel(taskName);
        nameLabel.setBounds(100, 10, 200, 30); // 可以根据实际需要调整位置和大小
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(nameLabel);

        // 设置描述
        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setBounds(100, 50, 500, 30); // 可以根据实际需要调整位置和大小
        descriptionArea.setFont(font);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setOpaque(false);
        descriptionArea.setEditable(false);
        this.add(descriptionArea);

        // 设置奖励金额
        JLabel prizeLabel = new JLabel("Prize: $" + String.format("%.2f", reward));
        prizeLabel.setBounds(100, 90, 200, 30);
        prizeLabel.setFont(font);
        this.add(prizeLabel);

        // 设置任务对象
        JLabel childLabel = new JLabel("Child: " + childName);
        childLabel.setBounds(300, 50, 200, 30);
        childLabel.setFont(font);
        this.add(childLabel);

        // 设置开始时间
        JLabel startLabel = new JLabel("Start: " + startDate);
        startLabel.setBounds(220, 10, 200, 30);
        startLabel.setFont(font);
        this.add(startLabel);

        // 设置截止时间
        JLabel dueLabel = new JLabel("Due: " + dueDate);
        dueLabel.setBounds(360, 10, 200, 30);
        dueLabel.setFont(font);
        this.add(dueLabel);

        // 删除按钮
        deleteButton = new JButton("Delete");
        deleteButton.setBounds(420, 90, 80, 30);
        this.add(deleteButton);
    }

    // 派生类只需传递相应的图标和构造参数给父类构造函数即可
    public static class TerminatedTaskLabel extends TaskLabel {
        public TerminatedTaskLabel(String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(taskName, description, reward, childName, startDate, dueDate);
            ImageIcon taskIcon = new ImageIcon("images/TerminatedTask.png");
            this.setIcon(taskIcon);
        }
    }

    public static class FinishedTaskLabel extends TaskLabel {
        public FinishedTaskLabel(String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(taskName, description, reward, childName, startDate, dueDate);
            ImageIcon taskIcon = new ImageIcon("images/FinishedTask.png");
            this.setIcon(taskIcon);
        }
    }

    public static class OngoingTaskLabel extends TaskLabel {
        public OngoingTaskLabel(String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(taskName, description, reward, childName, startDate, dueDate);
            ImageIcon taskIcon = new ImageIcon("images/OngoingTask.png");
            this.setIcon(taskIcon);
        }
    }

    public static class NotAcceptedTaskLabel extends TaskLabel {
        public NotAcceptedTaskLabel(String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(taskName, description, reward, childName, startDate, dueDate);
            ImageIcon taskIcon = new ImageIcon("images/NotAcceptedTask.png");
            this.setIcon(taskIcon);
        }
    }
}

