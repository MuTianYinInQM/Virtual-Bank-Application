package com.virtualbank.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import com.virtualbank.interfaces.DataUpdateListener;
import com.virtualbank.controller.SavingGoalController;
import com.virtualbank.model.SavingGoal;
import com.virtualbank.service.SavingGoalService;
import com.virtualbank.repository.SavingGoalRepository;


public class Page06_Goal implements DataUpdateListener {
	private JFrame window;
	private JPanel goalsPanel; // Panel for goals and buttons
	private JScrollPane scrollPane; // Scroll pane for goalsPanel
	private JButton createButton;
	private SavingGoalService goalService;
	private ImageIcon goalBackground; // 定义图标变量
	private List<JPanel> goalPanels = new ArrayList<>(); // 用于存储每个目标的面板
	private SavingGoalController controller;

	public Page06_Goal() {
		this.goalService = new SavingGoalService(new SavingGoalRepository());
		initializeComponents();
		configureUI();
		loadGoals();
		window.setVisible(true);
		this.controller = new SavingGoalController(this, goalService); // 初始化控制器
	}

	public JButton getCreateButton() {
		return createButton;
	}

	public List<JPanel> getGoalPanels() {
		return goalPanels;
	}

	public void removeGoalPanel(JPanel panel) {
		goalsPanel.remove(panel);
		goalsPanel.revalidate();
		goalsPanel.repaint();
	}

	private void initializeComponents() {
		window = new JFrame("Goal Tracker");
		goalsPanel = new JPanel();
		scrollPane = new JScrollPane(goalsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		createButton = new JButton("Create");
		goalBackground = new ImageIcon("images/Goal_Background.png"); // 加载背景图片

		// 确保正确设置关闭操作
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(window,
						"Are you sure you want to close this window?", "Close Window?",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
		});

		configureUI();  // 进行UI配置
	}

	private void configureUI() {
		window.setLayout(null); // 使用绝对布局
		window.setSize(1260, 780);

		goalsPanel.setLayout(new BoxLayout(goalsPanel, BoxLayout.Y_AXIS));
		goalsPanel.setOpaque(false);

		createButton.setFont(new Font("Arial", Font.BOLD, 16));
		createButton.setBounds(580, 620, 150, 50);  // 适当调整按钮位置和大小
		createButton.addActionListener(e -> createOrModifyGoal(null));  // 添加监听器以创建新目标

		JLabel backgroundLabel = new JLabel(goalBackground);
		backgroundLabel.setBounds(0, 0, 1260, 780);
		window.setContentPane(backgroundLabel);

		scrollPane.setOpaque(true);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBounds(100, 100, 1000, 360); // 设置滚动面板的位置和大小
		scrollPane.getViewport().setBackground(new Color(53, 145, 175)); // 使用RGB颜色代码
		scrollPane.setBackground(new Color(240, 240, 240));		createButton.setBounds(580, 620, 100, 50); // 设置创建按钮的位置和大小

		window.add(scrollPane);
		window.add(createButton);
	}

	private void loadGoals() {
		List<SavingGoal> goals = goalService.getAllSavingGoals();
		goalsPanel.removeAll();
		goalPanels.clear(); // 清空目标面板列表

		for (SavingGoal goal : goals) {
			JPanel goalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 5));
			goalPanel.setOpaque(false);

			// 使用HTML设置不同颜色
			String labelText = String.format(
					"<html><font color='blue'>%s</font>: <font color='green'>$</font><font color='green'>%.2f</font> / <font color='red'>$</font><font color='red'>%.2f</font></html>",
					goal.getGoalName(), goal.getCurrentAmount(), goal.getTargetAmount());
			JLabel goalLabel = new JLabel(labelText);
			goalLabel.setFont(new Font("Arial", Font.BOLD, 14)); // 设置字体大小和风格

			JProgressBar progressBar = new JProgressBar(0, 100);
			progressBar.setPreferredSize(new Dimension(400, 20));
			int progressValue = (int) ((goal.getCurrentAmount() / goal.getTargetAmount()) * 100);
			progressBar.setValue(progressValue);
			updateProgressBar(progressBar, progressValue);

			JButton modifyButton = new JButton("Modify");
			JButton deleteButton = new JButton("Delete");

			modifyButton.putClientProperty("goal", goal);
			deleteButton.putClientProperty("goal", goal);

			goalPanel.add(goalLabel);
			goalPanel.add(progressBar);
			goalPanel.add(modifyButton);
			goalPanel.add(deleteButton);

			goalPanels.add(goalPanel);
			goalsPanel.add(goalPanel);
		}

		goalsPanel.revalidate();
		goalsPanel.repaint();
	}

	private void updateProgressBar(JProgressBar progressBar, int progress) {
		progressBar.setStringPainted(true);
		if (progress < 25) {
			progressBar.setForeground(Color.RED);
		} else if (progress < 50) {
			progressBar.setForeground(Color.ORANGE);
		} else if (progress < 75) {
			progressBar.setForeground(Color.YELLOW);
		} else {
			progressBar.setForeground(Color.GREEN);
		}
	}

	@Override
	public void onDataUpdated() {
		refreshData();
	}
	public void refreshData() {
		loadGoals();  // 重新加载目标
		controller.refreshListeners();  // 重新绑定按钮监听器
	}

	private void rebindButtonListeners() {
		for (JPanel goalPanel : getGoalPanels()) {
			JButton modifyButton = (JButton) goalPanel.getComponent(2);
			JButton deleteButton = (JButton) goalPanel.getComponent(3);

			// 移除旧的监听器
			for (ActionListener al : modifyButton.getActionListeners()) {
				modifyButton.removeActionListener(al);
			}
			for (ActionListener al : deleteButton.getActionListeners()) {
				deleteButton.removeActionListener(al);
			}

			// 重新添加监听器
			modifyButton.addActionListener(e -> {
				SavingGoal goal = (SavingGoal) modifyButton.getClientProperty("goal");
				openSetGoalWindow(goal);
			});
			deleteButton.addActionListener(e -> {
				SavingGoal goal = (SavingGoal) deleteButton.getClientProperty("goal");
				if (JOptionPane.showConfirmDialog(null,
						"Are you sure you want to delete this goal?", "Confirm Deletion",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					goalService.deleteSavingGoal(goal.getGoalId());
					removeGoalPanel(goalPanel);
				}
			});
		}
	}



	private void openSetGoalWindow(SavingGoal goal) {
		Window06_SetGoal setGoalWindow = new Window06_SetGoal(goal, goalService, this);
		setGoalWindow.display(); // 使用display方法来显示窗口
	}

	// 创建新目标或修改现有目标时调用此方法
	public void createOrModifyGoal(SavingGoal goal) {
		if (goal == null) {
			goal = new SavingGoal();  // 创建一个新的SavingGoal实例
		}
		openSetGoalWindow(goal);
	}

	public static void main(String[] args) {
		new Page06_Goal();
	}
}



