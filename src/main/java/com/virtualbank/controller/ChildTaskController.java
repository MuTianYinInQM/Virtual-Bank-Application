package com.virtualbank.controller;

import com.virtualbank.model.AccountManager;
import com.virtualbank.model.Task;
import com.virtualbank.service.TaskService;
import com.virtualbank.ui.Page05_ChildTask;
import com.virtualbank.ui.TaskLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Controller class for managing child tasks.
 * Provides methods to handle user interactions and manage tasks from the child UI.
 */
public class ChildTaskController {
    private TaskService taskService;
    private Page05_ChildTask childTaskUI;
    private Map<Component, String> taskLabelToIdMap = new HashMap<>(); // Maps TaskLabel to task ID
    private AccountManager accountManager;

    /**
     * Constructs a ChildTaskController with the specified TaskService, UI components, and AccountManager.
     *
     * @param taskService the task service to be used for managing tasks
     * @param childTaskUI the UI component representing the child task page
     * @param accountManager the account manager for handling rewards
     */
    public ChildTaskController(TaskService taskService, Page05_ChildTask childTaskUI, AccountManager accountManager) {
        this.taskService = taskService;
        this.childTaskUI = childTaskUI;
        this.accountManager = accountManager;
        initializeTaskMap(); // 在构造函数中初始化映射
        attachEventHandlers();
    }

    /**
     * Initializes the task map by mapping UI components to task IDs.
     */
    private void initializeTaskMap() {
        JPanel viewPanel = (JPanel) childTaskUI.getScrollPane().getViewport().getView();
        Component[] components = viewPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof TaskLabel) {
                TaskLabel label = (TaskLabel) comp;
                taskLabelToIdMap.put(label, label.getTaskId());
            }
        }
    }

    /**
     * Attaches event handlers to UI components.
     */
    private void attachEventHandlers() {
        // 为每个TaskLabel及其子类添加事件处理
        taskLabelToIdMap.forEach((comp, taskId) -> {
            if (comp instanceof TaskLabel) {
                TaskLabel taskLabel = (TaskLabel) comp;
                taskLabel.getDeleteButton().addActionListener(e -> deleteTask(taskId));
            }
            if (comp instanceof Page05_ChildTask.Child_OngoingTaskLabel) {
                Page05_ChildTask.Child_OngoingTaskLabel ongoingLabel = (Page05_ChildTask.Child_OngoingTaskLabel) comp;
                ongoingLabel.getSubmitButton().addActionListener(e -> submitTask(taskId));
                ongoingLabel.getGiveUPButton().addActionListener(e -> giveUpTask(taskId));
            }
            if (comp instanceof Page05_ChildTask.Child_NotAcceptedTaskLabel) {
                Page05_ChildTask.Child_NotAcceptedTaskLabel notAcceptedLabel = (Page05_ChildTask.Child_NotAcceptedTaskLabel) comp;
                notAcceptedLabel.getAcceptButton().addActionListener(e -> acceptTask(taskId));
                notAcceptedLabel.getGiveUpButton().addActionListener(e -> giveUpTask(taskId));
            }
        });
    }

    /**
     * Submits a task by its ID, awards the child, and displays a confirmation message.
     *
     * @param taskId the ID of the task to submit
     */
    private void submitTask(String taskId) {
        try {
            Task task = taskService.submitTask(taskId);
            JOptionPane.showMessageDialog(childTaskUI, "Task submitted successfully.");
            accountManager.prize(accountManager.getPiggyUuid(), task.getReward(), "Task prize for " + task.getTaskName());
            refreshTasks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(childTaskUI, "Failed to submit task: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Allows a child user to give up a task by its ID and displays a confirmation message.
     *
     * @param taskId the ID of the task to give up
     */
    private void giveUpTask(String taskId) {
        try {
            taskService.giveUpTask(taskId);
            JOptionPane.showMessageDialog(childTaskUI, "Task given up successfully.");
            refreshTasks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(childTaskUI, "Failed to give up task: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Allows a child user to accept a task by its ID and displays a confirmation message.
     *
     * @param taskId the ID of the task to accept
     */
    private void acceptTask(String taskId) {
        try {
            taskService.acceptTask(taskId);
            JOptionPane.showMessageDialog(childTaskUI, "Task accepted successfully.");
            refreshTasks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(childTaskUI, "Failed to accept task: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Deletes a task by its ID and displays a confirmation message.
     *
     * @param taskId the ID of the task to delete
     */
    private void deleteTask(String taskId) {
        taskService.deleteTask(taskId);
        JOptionPane.showMessageDialog(childTaskUI, "Task deleted successfully.");
        refreshTasks();
    }

    /**
     * Refreshes the tasks displayed in the UI.
     * Ensures thread safety by running on the UI thread.
     */
    private void refreshTasks() {
        // Runs on the UI thread to ensure thread safety
        SwingUtilities.invokeLater(() -> {
            // First, detach existing event handlers from UI components
            detachEventHandlers();
            // Reload and display tasks
            childTaskUI.displayTasksFromJsonFile("src/main/resources/tasks.json");
            // Reinitialize the task map with newly loaded components
            initializeTaskMap();
            // Reattach event handlers
            attachEventHandlers();
            // Revalidate and repaint the UI
            childTaskUI.validate();
            childTaskUI.repaint();
        });
    }

    /**
     * Detaches event handlers from UI components.
     */
    private void detachEventHandlers() {
        JPanel viewPanel = (JPanel) childTaskUI.getScrollPane().getViewport().getView();
        Component[] components = viewPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof TaskLabel) {
                TaskLabel taskLabel = (TaskLabel) comp;
                ActionListener[] listeners = taskLabel.getDeleteButton().getActionListeners();
                for (ActionListener listener : listeners) {
                    taskLabel.getDeleteButton().removeActionListener(listener);
                }
            }
            if (comp instanceof Page05_ChildTask.Child_OngoingTaskLabel) {
                Page05_ChildTask.Child_OngoingTaskLabel ongoingLabel = (Page05_ChildTask.Child_OngoingTaskLabel) comp;
                ActionListener[] submitListeners = ongoingLabel.getSubmitButton().getActionListeners();
                for (ActionListener listener : submitListeners) {
                    ongoingLabel.getSubmitButton().removeActionListener(listener);
                }
                ActionListener[] giveUpListeners = ongoingLabel.getGiveUPButton().getActionListeners();
                for (ActionListener listener : giveUpListeners) {
                    ongoingLabel.getGiveUPButton().removeActionListener(listener);
                }
            }
            if (comp instanceof Page05_ChildTask.Child_NotAcceptedTaskLabel) {
                Page05_ChildTask.Child_NotAcceptedTaskLabel notAcceptedLabel = (Page05_ChildTask.Child_NotAcceptedTaskLabel) comp;
                ActionListener[] acceptListeners = notAcceptedLabel.getAcceptButton().getActionListeners();
                for (ActionListener listener : acceptListeners) {
                    notAcceptedLabel.getAcceptButton().removeActionListener(listener);
                }
                ActionListener[] giveUpListeners = notAcceptedLabel.getGiveUpButton().getActionListeners();
                for (ActionListener listener : giveUpListeners) {
                    notAcceptedLabel.getGiveUpButton().removeActionListener(listener);
                }
            }
        }
    }
}
