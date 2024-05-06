package com.virtualbank.controller;

import com.virtualbank.service.TaskService;
import com.virtualbank.ui.Page04_ParentHome;
import com.virtualbank.ui.TaskLabel;
import com.virtualbank.ui.Window3_CreateNewTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ParentHomeController {
    private Page04_ParentHome parentHomeUI;
    private Window3_CreateNewTask createTaskWindow;
    private CreateTaskController createTaskController; // 持久化控制器
    private TaskService taskService;

    private Map<Component, String> taskLabelToIdMap = new HashMap<>(); // 映射TaskLabel到任务ID

    public ParentHomeController(TaskService taskService, Page04_ParentHome parentHomeUI) {
        this.taskService = taskService;
        this.parentHomeUI = parentHomeUI;
        initializeTaskMap(); // 在构造函数中初始化映射
        attachEventHandlers();
    }

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
        });

        parentHomeUI.getCreateTaskButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 添加逻辑来显示创建任务窗口
                createTaskWindow = new Window3_CreateNewTask();
                createTaskController = new CreateTaskController(taskService, createTaskWindow);
                parentHomeUI.dispose();
                // if (createTaskWindow == null) {
                //     createTaskWindow = new Window3_CreateNewTask();
                //     createTaskController = new CreateTaskController(taskService, createTaskWindow);
                // }
                // createTaskWindow.setVisible(true);
                // parentHomeUI.setVisible(false);
            }
        });
    }
    private void deleteTask(String taskId) {
        taskService.deleteTask(taskId);
        JOptionPane.showMessageDialog(parentHomeUI, "Task deleted successfully.");
        refreshTasks();
    }

    private void terminateTask(String taskId) {
        try {
            taskService.terminateTask(taskId);
            JOptionPane.showMessageDialog(parentHomeUI, "Task terminated successfully.");
            refreshTasks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parentHomeUI, "Failed to terminate task: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTasks() {
        // 在UI线程中运行，确保线程安全
        SwingUtilities.invokeLater(() -> {
            // 首先清除现有的UI组件上的所有事件监听器
            detachEventHandlers();

            // 重新加载和显示任务
            parentHomeUI.displayTasksFromJsonFile("src/main/resources/tasks.json");

            // 从新加载的组件中获取新的映射
            initializeTaskMap();

            // 重新绑定事件处理器
            attachEventHandlers();

            // 重新验证和重绘界面
            parentHomeUI.validate();
            parentHomeUI.repaint();
        });
    }
    private void showCreateTaskWindow() {
        if (createTaskWindow == null) {
            createTaskWindow = new Window3_CreateNewTask();
            createTaskController = new CreateTaskController(taskService, createTaskWindow);
        }
        createTaskWindow.setVisible(true);
        parentHomeUI.setVisible(false);
    }
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
        }
    }
}
//package com.virtualbank.controller;
//
//import com.virtualbank.service.TaskService;
//import com.virtualbank.ui.Page04_ParentHome;
//import com.virtualbank.ui.Window3_CreateNewTask;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class ParentHomeController {
//    private Page04_ParentHome parentHomeUI;
//    private Window3_CreateNewTask createTaskWindow;
//    private CreateTaskController createTaskController; // 持久化控制器
//    private TaskService taskService = new TaskService();
//
//    public ParentHomeController(Page04_ParentHome parentHomeUI) {
//        this.parentHomeUI = parentHomeUI;
//        attachEventHandlers();
//    }
//
//    private void attachEventHandlers() {
//        parentHomeUI.getCreateTaskButton().addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showCreateTaskWindow();
//            }
//        });
//    }
//
//    private void showCreateTaskWindow() {
//        if (createTaskWindow == null) {
//            createTaskWindow = new Window3_CreateNewTask();
//            createTaskController = new CreateTaskController(taskService, createTaskWindow);
//        }
//        createTaskWindow.setVisible(true);
//        parentHomeUI.setVisible(false);
//    }
//}
