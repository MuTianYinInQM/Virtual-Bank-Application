package com.virtualbank.controller;

import com.virtualbank.service.TaskService;
import com.virtualbank.ui.Page01_Login;
import com.virtualbank.ui.Page04_ParentHome;
import com.virtualbank.ui.TaskLabel;
import com.virtualbank.ui.Window3_CreateNewTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller class for managing the parent home page.
 * Provides methods to handle user interactions and manage tasks from the UI.
 */
public class ParentHomeController {
    private Page04_ParentHome parentHomeUI;
    private Window3_CreateNewTask createTaskWindow;
    private CreateTaskController createTaskController; // Persistent controller
    private TaskService taskService;

    private Map<Component, String> taskLabelToIdMap = new HashMap<>(); // Maps TaskLabel to task ID

    /**
     * Constructs a ParentHomeController with the specified TaskService and UI components.
     *
     * @param taskService the task service to be used for managing tasks
     * @param parentHomeUI the UI component representing the parent home page
     */
    public ParentHomeController(TaskService taskService, Page04_ParentHome parentHomeUI) {
        this.taskService = taskService;
        this.parentHomeUI = parentHomeUI;
        initializeTaskMap();
        attachEventHandlers();
    }

    /**
     * Initializes the task map by mapping UI components to task IDs.
     */
    private void initializeTaskMap() {
        JPanel viewPanel = (JPanel) parentHomeUI.getScrollPane().getViewport().getView();
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
        taskLabelToIdMap.forEach((comp, taskId) -> {
            if (comp instanceof TaskLabel) {
                TaskLabel taskLabel = (TaskLabel) comp;
                taskLabel.getDeleteButton().addActionListener(e -> deleteTask(taskId));
            }
            if (comp instanceof Page04_ParentHome.Parent_OngoingTaskLabel) {
                Page04_ParentHome.Parent_OngoingTaskLabel ongoingLabel = (Page04_ParentHome.Parent_OngoingTaskLabel) comp;
                ongoingLabel.getTerminateButton().addActionListener(e -> terminateTask(taskId));
            }
            if (comp instanceof Page04_ParentHome.Parent_NotAcceptedTaskLabel) {
                Page04_ParentHome.Parent_NotAcceptedTaskLabel notAcceptedLabel = (Page04_ParentHome.Parent_NotAcceptedTaskLabel) comp;
                notAcceptedLabel.getTerminateButton().addActionListener(e -> terminateTask(taskId));
            }
            if (comp instanceof Page04_ParentHome.Parent_FinishedTaskLabel) {
                Page04_ParentHome.Parent_FinishedTaskLabel finishedTaskLabel = (Page04_ParentHome.Parent_FinishedTaskLabel) comp;
                finishedTaskLabel.getConfirmButton().addActionListener(e -> confirmTask(taskId));
            }
        });

        parentHomeUI.getExitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentHomeUI.dispose();
                new Page01_Login();
            }
        });

        parentHomeUI.getCreateTaskButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to display the create task window
                createTaskWindow = new Window3_CreateNewTask();
                createTaskController = new CreateTaskController(taskService, createTaskWindow);
                parentHomeUI.dispose();
            }
        });
    }

    /**
     * Deletes a task by its ID and displays a confirmation message.
     *
     * @param taskId the ID of the task to delete
     */
    private void deleteTask(String taskId) {
        taskService.deleteTask(taskId);
        JOptionPane.showMessageDialog(parentHomeUI, "Task deleted successfully.");
        refreshTasks();
    }

    /**
     * Terminates a task by its ID and displays a confirmation message.
     *
     * @param taskId the ID of the task to terminate
     */
    private void terminateTask(String taskId) {
        try {
            taskService.terminateTask(taskId);
            JOptionPane.showMessageDialog(parentHomeUI, "Task terminated successfully.");
            refreshTasks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parentHomeUI, "Failed to terminate task: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Confirms a task by its ID and displays a confirmation message.
     *
     * @param taskId the ID of the task to confirm
     */
    private void confirmTask(String taskId){
        try {
            taskService.confirmTask(taskId);
            JOptionPane.showMessageDialog(parentHomeUI, "Task confirmed successfully.");
            refreshTasks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parentHomeUI, "Failed to confirm task: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
            parentHomeUI.displayTasksFromJsonFile("src/main/resources/tasks.json");
            // Reinitialize the task map with newly loaded components
            initializeTaskMap();
            // Reattach event handlers
            attachEventHandlers();
            // Revalidate and repaint the UI
            parentHomeUI.validate();
            parentHomeUI.repaint();
        });
    }

    /**
     * Displays the create task window.
     */
    private void showCreateTaskWindow() {
        if (createTaskWindow == null) {
            createTaskWindow = new Window3_CreateNewTask();
            createTaskController = new CreateTaskController(taskService, createTaskWindow);
        }
        createTaskWindow.setVisible(true);
        parentHomeUI.setVisible(false);
    }

    /**
     * Detaches event handlers from UI components.
     */
    private void detachEventHandlers() {
        JPanel viewPanel = (JPanel) parentHomeUI.getScrollPane().getViewport().getView();
        Component[] components = viewPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof TaskLabel) {
                TaskLabel taskLabel = (TaskLabel) comp;
                ActionListener[] listeners = taskLabel.getDeleteButton().getActionListeners();
                for (ActionListener listener : listeners) {
                    taskLabel.getDeleteButton().removeActionListener(listener);
                }
            }
            if (comp instanceof Page04_ParentHome.Parent_OngoingTaskLabel) {
                Page04_ParentHome.Parent_OngoingTaskLabel ongoingLabel = (Page04_ParentHome.Parent_OngoingTaskLabel) comp;
                ActionListener[] terminateListeners = ongoingLabel.getTerminateButton().getActionListeners();
                for (ActionListener listener : terminateListeners) {
                    ongoingLabel.getTerminateButton().removeActionListener(listener);
                }
            }
            if (comp instanceof Page04_ParentHome.Parent_NotAcceptedTaskLabel) {
                Page04_ParentHome.Parent_NotAcceptedTaskLabel notAcceptedLabel = (Page04_ParentHome.Parent_NotAcceptedTaskLabel) comp;
                ActionListener[] acceptListeners = notAcceptedLabel.getTerminateButton().getActionListeners();
                for (ActionListener listener : acceptListeners) {
                    notAcceptedLabel.getTerminateButton().removeActionListener(listener);
                }
            }

            if (comp instanceof Page04_ParentHome.Parent_FinishedTaskLabel) {
                Page04_ParentHome.Parent_FinishedTaskLabel finishedTaskLabel = (Page04_ParentHome.Parent_FinishedTaskLabel) comp;
                ActionListener[] confirmListeners = finishedTaskLabel.getConfirmButton().getActionListeners();
                for (ActionListener listener : confirmListeners) {
                    finishedTaskLabel.getConfirmButton().removeActionListener(listener);
                }
            }
        }
    }
}