package com.virtualbank.controller;

import com.virtualbank.service.TaskService;
import com.virtualbank.ui.Window3_CreateNewTask;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class CreateTaskController {
    private TaskService taskService;
    private Window3_CreateNewTask createTaskUI;

    public CreateTaskController(TaskService taskService, Window3_CreateNewTask createTaskUI) {
        this.taskService = taskService;
        this.createTaskUI = createTaskUI;
        attachEventHandlers();
    }

    public boolean checkTime(String str) { // 检查输入是否符合规范
        if (str.length() == 8) {
            if (Character.isDigit(str.charAt(0))
                    && Character.isDigit(str.charAt(1))
                    && str.charAt(2) == '-'
                    && Character.isDigit(str.charAt(3))
                    && Character.isDigit(str.charAt(4))
                    && str.charAt(5) == '-'
                    && Character.isDigit(str.charAt(6))
                    && Character.isDigit(str.charAt(7))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void attachEventHandlers() {
        createTaskUI.getConfirmButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取用户输入的数据
                String taskName = createTaskUI.getTaskName_textField().getText().trim();
                String content = createTaskUI.getContent_textField().getText().trim();
                String money = createTaskUI.getMoney_textField().getText().trim();
                String childName = createTaskUI.getChildName_textField().getText().trim();
                String startTime = createTaskUI.getStart_textField().getText().trim();
                String dueTime = createTaskUI.getDue_textField().getText().trim();

                // 验证输入数据的有效性
                if (taskName.isEmpty() || content.isEmpty() || money.isEmpty() || childName.isEmpty() || startTime.isEmpty() || dueTime.isEmpty()) {
                    JOptionPane.showMessageDialog(createTaskUI, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (taskName.length() > 14) {
                    JOptionPane.showMessageDialog(createTaskUI, "\"Task Name\" should be no longer than 14 characters in length.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (content.length() > 60) {
                    JOptionPane.showMessageDialog(createTaskUI, "\"Task Content\" should be no longer than 60 characters in length.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try { // 判断reward/money是否可以转换为浮点数形式
                    double number = Double.parseDouble(money);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(createTaskUI, "\"Reward Amount\" should be in this format: xx.xx", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (childName.length() > 8) {
                    JOptionPane.showMessageDialog(createTaskUI, "\"Child Name\" should be no longer than 8 characters in length.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!checkTime(startTime)) {
                    JOptionPane.showMessageDialog(createTaskUI, "\"Start Date\" should be in this format: DD-MM-YY.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!checkTime(dueTime)) {
                    JOptionPane.showMessageDialog(createTaskUI, "\"Due Date\" should be in this format: DD-MM-YY.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 尝试转换金额为数值
                double reward;
                try {
                    reward = Double.parseDouble(money);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(createTaskUI, "Please enter a valid number for the reward amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 创建新的任务并添加到任务列表
                try {
                    taskService.createTask(taskName, content, reward, childName, startTime, dueTime);
                    JOptionPane.showMessageDialog(createTaskUI, "Task created successfully.", "Task Creation", JOptionPane.INFORMATION_MESSAGE);
                    // 清除输入框
                    createTaskUI.getTaskName_textField().setText("");
                    createTaskUI.getContent_textField().setText("");
                    createTaskUI.getMoney_textField().setText("");
                    createTaskUI.getChildName_textField().setText("");
                    createTaskUI.getStart_textField().setText("");
                    createTaskUI.getDue_textField().setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(createTaskUI, "Error creating task: " + ex.getMessage(), "Creation Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
