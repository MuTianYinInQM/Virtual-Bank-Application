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

public class ChildTaskController {
    private TaskService taskService;
    private Page05_ChildTask childTaskUI;
    private Map<Component, String> taskLabelToIdMap = new HashMap<>(); // 映射TaskLabel到任务ID
    private AccountManager accountManager;

    public ChildTaskController(TaskService taskService, Page05_ChildTask childTaskUI, AccountManager accountManager) {
        this.taskService = taskService;
        this.childTaskUI = childTaskUI;
        this.accountManager = accountManager;
        initializeTaskMap(); // 在构造函数中初始化映射
        attachEventHandlers();
    }

    // 初始化映射，将组件与任务ID相关联
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

    void submitTask(String taskId) {
        try {
            Task task = taskService.submitTask(taskId);
            JOptionPane.showMessageDialog(childTaskUI, "Task submitted successfully.");
            accountManager.prize(accountManager.getPiggyUuid(), task.getReward(), "Task prize for " + task.getTaskName());
            refreshTasks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(childTaskUI, "Failed to submit task: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void giveUpTask(String taskId) {
        try {
            taskService.giveUpTask(taskId);
            JOptionPane.showMessageDialog(childTaskUI, "Task given up successfully.");
            refreshTasks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(childTaskUI, "Failed to give up task: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void acceptTask(String taskId) {
        try {
            taskService.acceptTask(taskId);
            JOptionPane.showMessageDialog(childTaskUI, "Task accepted successfully.");
            refreshTasks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(childTaskUI, "Failed to accept task: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void deleteTask(String taskId) {
        taskService.deleteTask(taskId);
        JOptionPane.showMessageDialog(childTaskUI, "Task deleted successfully.");
        refreshTasks();
    }

    void refreshTasks() {
        // 在UI线程中运行，确保线程安全
        SwingUtilities.invokeLater(() -> {
            // 首先清除现有的UI组件上的所有事件监听器
            detachEventHandlers();

            // 重新加载和显示任务
            childTaskUI.displayTasksFromJsonFile("src/main/resources/tasks.json");

            // 从新加载的组件中获取新的映射
            initializeTaskMap();

            // 重新绑定事件处理器
            attachEventHandlers();

            // 重新验证和重绘界面
            childTaskUI.validate();
            childTaskUI.repaint();
        });
    }

    void detachEventHandlers() {
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
