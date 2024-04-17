package com.virtualbank.ui;

import com.virtualbank.model.SavingGoal;
import com.virtualbank.repository.SavingGoalRepository;
import com.virtualbank.service.SavingGoalService;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;

public class SavingGoalUI extends JFrame {
    private final SavingGoalService savingGoalService;
    private final DefaultListModel<String> goalListModel;
    private final JTextField txtGoalName;
    private final JTextField txtTargetAmount;
    private final JButton addButton;

    public SavingGoalUI(SavingGoalService savingGoalService) {
        this.savingGoalService = savingGoalService;
        goalListModel = new DefaultListModel<>();
        txtGoalName = new JTextField(20);
        txtTargetAmount = new JTextField(20);
        addButton = new JButton("Add Goal");

        setTitle("Saving Goals");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null); // Center the window

        initializeComponents();
        layoutComponents();
        attachEventHandlers();
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JTextField getTxtGoalName() {
        return txtGoalName;
    }

    public JTextField getTxtTargetAmount() {
        return txtTargetAmount;
    }

    public DefaultListModel<String> getGoalListModel() {
        return goalListModel;
    }

    private void initializeComponents() {
//        List<SavingGoal> goals = savingGoalService.getAllSavingGoals();
//        for (SavingGoal goal : goals) {
//            goalListModel.addElement(goal.getName() + " - $" + goal.getTargetAmount());
//        }
    }

    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));

        // List Panel
        JList<String> goalList = new JList<>(goalListModel);
        JScrollPane scrollPane = new JScrollPane(goalList);
        add(scrollPane, BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Goal Name:"));
        inputPanel.add(txtGoalName);
        inputPanel.add(new JLabel("Target Amount:"));
        inputPanel.add(txtTargetAmount);
        add(inputPanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void attachEventHandlers() {
        addButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtGoalName.getText();
                String amountText = txtTargetAmount.getText();
                String childId = "someChildId"; // 你需要从用户界面或者上下文中获取这个值
                try {
                    double amount = Double.parseDouble(amountText);
                    SavingGoal newGoal = savingGoalService.addSavingGoal(childId, name, amount);
                    goalListModel.addElement(name + " - $" + amount);
                    txtGoalName.setText("");
                    txtTargetAmount.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SavingGoalUI.this,
                            "Please enter a valid number for the target amount.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    public static void main(String[] args) {
        // Assume that SavingGoalRepository is correctly set up
        SavingGoalRepository repository = new SavingGoalRepository(); // This should point to your actual json file
        SavingGoalService service = new SavingGoalService(repository);

        SwingUtilities.invokeLater(() -> {
            SavingGoalUI ui = new SavingGoalUI(service);
            ui.setVisible(true);
        });
    }
}
