package com.virtualbank.controller;

import com.virtualbank.service.TaskService;
import com.virtualbank.ui.Window3_CreateNewTask;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Controller class for creating new tasks.
 * Provides methods to handle user input and validate task creation.
 */
public class CreateTaskController {
    private TaskService taskService;
    private Window3_CreateNewTask createTaskUI;

    /**
     * Constructs a CreateTaskController with the specified TaskService and UI components.
     *
     * @param taskService the task service to be used for managing tasks
     * @param createTaskUI the UI component representing the task creation window
     */
    public CreateTaskController(TaskService taskService, Window3_CreateNewTask createTaskUI) {
        this.taskService = taskService;
        this.createTaskUI = createTaskUI;
        attachEventHandlers();
    }

    /**
     * Checks if the given time string is in the correct format (DD-MM-YY).
     *
     * @param str the time string to check
     * @return true if the time string is in the correct format, false otherwise
     */
    public boolean checkTime(String str) {
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

    /**
     * Attaches event handlers to the UI components.
     */
    private void attachEventHandlers() {
        createTaskUI.getConfirmButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieves user input data
                String taskName = createTaskUI.getTaskName_textField().getText().trim();
                String content = createTaskUI.getContent_textField().getText().trim();
                String money = createTaskUI.getMoney_textField().getText().trim();
                String childName = createTaskUI.getChildName_textField().getText().trim();
                String startTime = createTaskUI.getStart_textField().getText().trim();
                String dueTime = createTaskUI.getDue_textField().getText().trim();

                // Validates the input data
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

                try { // Checks if reward/money can be converted to a double
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

                // Attempts to convert the reward to a double
                double reward;
                try {
                    reward = Double.parseDouble(money);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(createTaskUI, "Please enter a valid number for the reward amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Creates a new task and adds it to the task list
                try {
                    taskService.createTask(taskName, content, reward, childName, startTime, dueTime);
                    JOptionPane.showMessageDialog(createTaskUI, "Task created successfully.", "Task Creation", JOptionPane.INFORMATION_MESSAGE);
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

    public void showMessageDialog(Component parentComponent, Object message, String title, int messageType) {
        JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
    }
}
