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
