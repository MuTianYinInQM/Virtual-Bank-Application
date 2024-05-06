package com.virtualbank.ui;

import javax.swing.*;
import java.awt.*;

public class TaskLabel extends JLabel {

    private String taskId;
    private JButton deleteButton; // 每个任务的删除按钮

    public String getTaskId() {
        return taskId;
    }
    
    public JButton getDeleteButton() {
        return deleteButton;
    }

    // 修改构造函数以适应新的参数
    public TaskLabel(String taskId, String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
        this.taskId = taskId;
        Font font = new Font("Arial", Font.PLAIN, 16);
        this.setLayout(null);
        this.setPreferredSize(new Dimension(523, 133)); // 设置标签的首选大小

        // 设置任务名
        JLabel nameLabel = new JLabel(taskName);
        nameLabel.setBounds(100, -20, 220, 109);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        this.add(nameLabel);

        // 设置描述
        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setBounds(100, 50, 280, 59);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 16));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setOpaque(false);
        descriptionArea.setEditable(false);
        this.add(descriptionArea);

        // 设置奖励金额
        JLabel prizeLabel = new JLabel("Prize: $" + String.format("%.2f", reward));
        prizeLabel.setVerticalAlignment(SwingConstants.TOP);
        prizeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        prizeLabel.setBounds(400, 10, 220, 109);
        prizeLabel.setFont(font);
        this.add(prizeLabel);

        // 设置开始时间
        JLabel startLabel = new JLabel("Start: " + startDate);
        startLabel.setVerticalAlignment(SwingConstants.TOP);
        startLabel.setHorizontalAlignment(SwingConstants.LEFT);
        startLabel.setBounds(400, 30, 220, 109);
        startLabel.setFont(font);
        this.add(startLabel);

        // 设置截止时间
        JLabel dueLabel = new JLabel("Due: " + dueDate);
        dueLabel.setVerticalAlignment(SwingConstants.TOP);
        dueLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dueLabel.setBounds(400, 50, 220, 109);
        dueLabel.setFont(font);
        this.add(dueLabel);

        // 设置任务对象
        JLabel childLabel = new JLabel("Child: " + childName);
        childLabel.setVerticalAlignment(SwingConstants.TOP);
        childLabel.setHorizontalAlignment(SwingConstants.LEFT);
        childLabel.setBounds(400, 70, 220, 109);
        childLabel.setFont(font);
        this.add(childLabel);

        // 删除按钮
        deleteButton = new JButton("Delete");
        deleteButton.setBounds(400, 90, 105, 40);
        this.add(deleteButton);
    }

    // 派生类，利用相同的构造参数
    public static class TerminatedTaskLabel extends TaskLabel {
        public TerminatedTaskLabel(String taskId, String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(taskId, taskName, description, reward, childName, startDate, dueDate);
            ImageIcon taskIcon = new ImageIcon("images/TerminatedTask.png");
            this.setIcon(taskIcon);
        }
    }

    public static class FinishedTaskLabel extends TaskLabel {
        public FinishedTaskLabel(String taskId, String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(taskId, taskName, description, reward, childName, startDate, dueDate);
            ImageIcon taskIcon = new ImageIcon("images/FinishedTask.png");
            this.setIcon(taskIcon);
        }
    }

    public static class OngoingTaskLabel extends TaskLabel {
       public OngoingTaskLabel(String taskId, String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(taskId, taskName, description, reward, childName, startDate, dueDate);
            ImageIcon taskIcon = new ImageIcon("images/OngoingTask.png");
            this.setIcon(taskIcon);
        }
    }

    public static class NotAcceptedTaskLabel extends TaskLabel {
        public NotAcceptedTaskLabel(String taskId, String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(taskId, taskName, description, reward, childName, startDate, dueDate);
            ImageIcon taskIcon = new ImageIcon("images/NotAcceptedTask.png");
            this.setIcon(taskIcon);
        }
    }
}
