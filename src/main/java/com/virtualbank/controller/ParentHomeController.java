package com.virtualbank.controller;

import com.virtualbank.service.TaskService;
import com.virtualbank.ui.Page04_ParentHome;
import com.virtualbank.ui.Window3_CreateNewTask;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParentHomeController {
    private Page04_ParentHome parentHomeUI;
    private Window3_CreateNewTask createTaskWindow;
    private CreateTaskController createTaskController; // 持久化控制器
    private TaskService taskService = new TaskService();

    public ParentHomeController(Page04_ParentHome parentHomeUI) {
        this.parentHomeUI = parentHomeUI;
        attachEventHandlers();
    }

    private void attachEventHandlers() {
        parentHomeUI.getCreateTaskButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCreateTaskWindow();
            }
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
}
