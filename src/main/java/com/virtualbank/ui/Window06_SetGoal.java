package com.virtualbank.ui;

import java.awt.Font;
import javax.swing.*;

import com.virtualbank.interfaces.DataUpdateListener;
import com.virtualbank.model.SavingGoal;
import com.virtualbank.service.SavingGoalService;
import com.virtualbank.repository.SavingGoalRepository;

public class Window06_SetGoal {
	private JFrame window;
	private JTextField goalNameField, childNameField, targetAmountField;
	private JLabel goalNameLabel, childNameLabel, targetAmountLabel;
	private JButton confirmButton;
	private JLabel backgroundLabel;

	private SavingGoalService savingGoalService;
	private SavingGoal goalToUpdate;
	private DataUpdateListener updateListener;

	public Window06_SetGoal(SavingGoal goalToUpdate, SavingGoalService savingGoalService, DataUpdateListener listener) {
		this.goalToUpdate = goalToUpdate;
		this.savingGoalService = savingGoalService;
		this.updateListener = listener;

		// Window settings
		this.window = new JFrame("JoyBank - Set Your Goal");
		window.setBounds(330, 200, 600, 378);
		window.setLayout(null);
		window.setResizable(false);
		this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		initializeComponents();
	}

	// 提供一个方法来显示窗口
	public void display() {
		window.setVisible(true);
	}

	private void initializeComponents(){
		// Background image
		ImageIcon windowBackground = new ImageIcon("images/Window_Background.png");
		this.backgroundLabel = new JLabel(windowBackground);
		backgroundLabel.setBounds(0, 0, 600, 378);
		window.setContentPane(backgroundLabel);

		// Font settings
		Font labelFont = new Font("Arial", Font.PLAIN, 18);

		// Goal Name components
		this.goalNameLabel = new JLabel("Goal Name:");
		this.goalNameLabel.setBounds(50, 80, 150, 30);
		this.goalNameLabel.setFont(labelFont);
		backgroundLabel.add(goalNameLabel);

		this.goalNameField = new JTextField();
		this.goalNameField.setBounds(220, 80, 330, 30);
		this.goalNameField.setFont(labelFont);
		backgroundLabel.add(goalNameField);

		// Child Name components
		this.childNameLabel = new JLabel("Child Name:");
		this.childNameLabel.setBounds(50, 130, 150, 30);
		this.childNameLabel.setFont(labelFont);
		backgroundLabel.add(childNameLabel);

		this.childNameField = new JTextField();
		this.childNameField.setBounds(220, 130, 330, 30);
		this.childNameField.setFont(labelFont);
		backgroundLabel.add(childNameField);

		// Target Amount components
		this.targetAmountLabel = new JLabel("Target Amount:");
		this.targetAmountLabel.setBounds(50, 180, 150, 30);
		this.targetAmountLabel.setFont(labelFont);
		backgroundLabel.add(targetAmountLabel);

		this.targetAmountField = new JTextField();
		this.targetAmountField.setBounds(220, 180, 330, 30);
		this.targetAmountField.setFont(labelFont);
		backgroundLabel.add(targetAmountField);

		// Confirm Button
		this.confirmButton = new JButton(new ImageIcon("images/confirm2.png"));
		this.confirmButton.setBounds(230, 240, 140, 50);
//		this.confirmButton.setFont(new Font("Arial", Font.BOLD, 20));
		this.confirmButton.setOpaque(false);
		this.confirmButton.setContentAreaFilled(false);
		this.confirmButton.setBorderPainted(false);
		this.confirmButton.addActionListener(e -> saveGoal());
		backgroundLabel.add(confirmButton);

		if (goalToUpdate != null) {
			goalNameField.setText(goalToUpdate.getGoalName());
			childNameField.setText(goalToUpdate.getChildName());
			targetAmountField.setText(String.valueOf(goalToUpdate.getTargetAmount()));
		}

		// 如果目标更新信息不为空，则将其填充到文本框中
		if (goalToUpdate != null) {
			goalNameField.setText(goalToUpdate.getGoalName());
			childNameField.setText(goalToUpdate.getChildName());
			targetAmountField.setText(String.valueOf(goalToUpdate.getTargetAmount()));
		}
	}

	private void saveGoal() {
		try {
			String goalName = goalNameField.getText();
			String childName = childNameField.getText();
			double targetAmount = Double.parseDouble(targetAmountField.getText());

			if (goalToUpdate != null) {
				// 如果目标更新信息不为空，则更新目标信息
				goalToUpdate.setGoalName(goalName);
				//goalToUpdate.setChildName(childName);
				goalToUpdate.setTargetAmount(targetAmount);
				savingGoalService.updateSavingGoal(goalToUpdate.getGoalId(), targetAmount);
				JOptionPane.showMessageDialog(window, "Goal successfully updated!");
			} else {
				// 否则创建一个新的目标
				SavingGoal newGoal = new SavingGoal(null, childName, targetAmount, 0, goalName);
				savingGoalService.addSavingGoal(childName, goalName, targetAmount);
				JOptionPane.showMessageDialog(window, "Goal successfully saved!");
			}

			if (updateListener != null) {
				updateListener.onDataUpdated();
			}
			window.dispose(); // 关闭窗口

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(window, "Please enter a valid amount", "Input Error", JOptionPane.ERROR_MESSAGE);
		}
	}


	public static void main(String[] args) {
		DataUpdateListener listener = new DataUpdateListener() {
			@Override
			public void onDataUpdated() {
				System.out.println("Data has been updated.");
			}
		};
		new Window06_SetGoal(null, new SavingGoalService(new SavingGoalRepository()), listener);
	}
}
