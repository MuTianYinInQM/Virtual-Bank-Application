package com.virtualbank.controller;

import com.virtualbank.model.SavingGoal;
import com.virtualbank.service.SavingGoalService;
import com.virtualbank.ui.SavingGoalUI;

import javax.swing.*;
import java.util.List;

public class SavingGoalController {
    private SavingGoalService savingGoalService;
    private SavingGoalUI savingGoalUI;

    public SavingGoalController(SavingGoalService savingGoalService, SavingGoalUI savingGoalUI) {
        this.savingGoalService = savingGoalService;
        this.savingGoalUI = savingGoalUI;
        initController();
    }

    private void initController() {
        // Load initial data into UI
        List<SavingGoal> goals = savingGoalService.getAllSavingGoals();
        for (SavingGoal goal : goals) {
            savingGoalUI.getGoalListModel().addElement(goal.getName() + " - $" + goal.getTargetAmount());
        }

        // Add action listeners to UI components
        savingGoalUI.getAddButton().addActionListener(e -> addSavingGoal());
    }

    private void addSavingGoal() {
        String name = savingGoalUI.getTxtGoalName().getText().trim();
        String amountText = savingGoalUI.getTxtTargetAmount().getText().trim();
        if (!name.isEmpty() && !amountText.isEmpty()) {
            try {
                double amount = Double.parseDouble(amountText);
                SavingGoal newGoal = savingGoalService.addSavingGoal("childId", name, amount);
                savingGoalUI.getGoalListModel().addElement(name + " - $" + newGoal.getTargetAmount());
                savingGoalUI.getTxtGoalName().setText("");
                savingGoalUI.getTxtTargetAmount().setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(savingGoalUI,
                        "Please enter a valid number for the target amount.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(savingGoalUI,
                    "Both name and target amount are required.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Other controller methods can be added here as needed.
}
