package com.virtualbank;

import com.virtualbank.controller.SavingGoalController;
import com.virtualbank.repository.SavingGoalRepository;
import com.virtualbank.service.SavingGoalService;
import com.virtualbank.ui.SavingGoalUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> {
                    SavingGoalRepository repository = new SavingGoalRepository();
                    SavingGoalService service = new SavingGoalService(repository);
                    SavingGoalUI ui = new SavingGoalUI(service);
                    new SavingGoalController(service, ui);

                    ui.pack();
                    ui.setVisible(true);
                }
        );
    }
}
