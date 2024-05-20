package com.virtualbank.controller;

import com.virtualbank.model.SavingGoal;
import com.virtualbank.service.SavingGoalService;
import com.virtualbank.ui.Page06_Goal;
import com.virtualbank.ui.Window06_SetGoal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SavingGoalController {
    private Page06_Goal page;
    private SavingGoalService savingGoalService;

    public SavingGoalController(Page06_Goal page, SavingGoalService savingGoalService) {
        this.page = page;
        this.savingGoalService = savingGoalService;
        initController();
    }

    private void initController() {
        // 绑定创建按钮的动作监听器
        setupCreateButtonListener();
        setupModifyAndDeleteButtonListeners();
    }

    private void setupCreateButtonListener() {
        ActionListener[] listeners = page.getCreateButton().getActionListeners();
        for (ActionListener listener : listeners) {
            page.getCreateButton().removeActionListener(listener);
        }
        page.getCreateButton().addActionListener(e -> {
            double currentBalance = page.getAccountManager().getTotalBalance(); // 获取当前余额
            openSetGoalWindow(null, currentBalance); // 传递当前余额
        });
    }

    private void setupModifyAndDeleteButtonListeners() {
        for (JPanel goalPanel : page.getGoalPanels()) {
            // 使用正确的索引值获取组件
            JButton modifyButton = (JButton) goalPanel.getComponent(1);
            JButton deleteButton = (JButton) goalPanel.getComponent(2);

            // 清除旧的监听器
            for (ActionListener al : modifyButton.getActionListeners()) {
                modifyButton.removeActionListener(al);
            }
            for (ActionListener al : deleteButton.getActionListeners()) {
                deleteButton.removeActionListener(al);
            }

            // 添加新的监听器
            modifyButton.addActionListener(e -> {
                SavingGoal goal = (SavingGoal) modifyButton.getClientProperty("goal");
                double currentBalance = page.getAccountManager().getTotalBalance(); // 获取当前余额
                openSetGoalWindow(goal, currentBalance); // 传递当前余额
            });

            deleteButton.addActionListener(e -> {
                SavingGoal goal = (SavingGoal) deleteButton.getClientProperty("goal");
                int response = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete this goal?", "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    savingGoalService.deleteSavingGoal(goal.getGoalId());
                    page.removeGoalPanel(goalPanel);
                }
            });
        }
    }


    public void refreshListeners() {
        setupModifyAndDeleteButtonListeners();
    }

    private void openSetGoalWindow(SavingGoal goal, double currentBalance) {
        Window06_SetGoal setGoalWindow = new Window06_SetGoal(goal, savingGoalService, page, currentBalance);
        setGoalWindow.display();
    }
}

