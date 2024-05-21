package com.virtualbank.controller;

import com.virtualbank.interfaces.Page;
import com.virtualbank.model.SavingGoal;
import com.virtualbank.service.SavingGoalService;
import com.virtualbank.ui.Page06_Goal;
import com.virtualbank.ui.Window06_SetGoal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Controller class for managing saving goals in the UI.
 * Provides methods to handle user interactions and manage saving goals from the UI.
 */
public class SavingGoalController implements Page {
    private Page06_Goal page;
    private SavingGoalService savingGoalService;

    /**
     * Constructs a SavingGoalController with the specified Page06_Goal and SavingGoalService.
     *
     * @param page              the UI component representing the saving goals page
     * @param savingGoalService the service for managing saving goals
     */
    public SavingGoalController(Page06_Goal page, SavingGoalService savingGoalService) {
        this.page = page;
        this.savingGoalService = savingGoalService;
        initController();
    }

    /**
     * Initializes the controller by setting up the button listeners.
     */
    private void initController() {
        // 绑定创建按钮的动作监听器
        setupCreateButtonListener();
        setupModifyAndDeleteButtonListeners();
    }

    /**
     * Sets up the action listener for the create button.
     */
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

    /**
     * Sets up the action listeners for the modify and delete buttons on the goal panels.
     */
    private void setupModifyAndDeleteButtonListeners() {
        for (JPanel goalPanel : page.getGoalPanels()) {
            // Uses the correct index to get the components
            JButton modifyButton = (JButton) goalPanel.getComponent(1);
            JButton deleteButton = (JButton) goalPanel.getComponent(2);

            // Removes old listeners
            for (ActionListener al : modifyButton.getActionListeners()) {
                modifyButton.removeActionListener(al);
            }
            for (ActionListener al : deleteButton.getActionListeners()) {
                deleteButton.removeActionListener(al);
            }

            // Adds new listeners
            modifyButton.addActionListener(e -> {
                SavingGoal goal = (SavingGoal) modifyButton.getClientProperty("goal");
                double currentBalance = page.getAccountManager().getTotalBalance(); // Gets the current balance
                openSetGoalWindow(goal, currentBalance); // Passes the current balance
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

    /**
     * Refreshes the listeners for the modify and delete buttons.
     */
    public void refreshListeners() {
        setupModifyAndDeleteButtonListeners();
    }

    /**
     * Opens the window to set or modify a saving goal.
     *
     * @param goal           the saving goal to modify, or null to create a new goal
     * @param currentBalance the current balance to be passed to the window
     */
    private void openSetGoalWindow(SavingGoal goal, double currentBalance) {
        Window06_SetGoal setGoalWindow = new Window06_SetGoal(goal, savingGoalService, page, currentBalance);
        setGoalWindow.display();
    }


    @Override
    public void toggleVisibility() {
        this.page.setVisible(!this.page.isVisible());
    }

    @Override
    public void setVisibility(boolean visibility) {
        this.page.setVisible(visibility);
    }

    @Override
    public boolean getVisibility() {
        return this.page.isVisible();
    }

    @Override
    public void dispose() {
        this.page.dispose();
    }
}

