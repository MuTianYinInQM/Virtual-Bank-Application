package com.virtualbank.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

import com.virtualbank.interfaces.DataUpdateListener;
import com.virtualbank.controller.SavingGoalController;
import com.virtualbank.model.SavingGoal;
import com.virtualbank.service.SavingGoalService;
import com.virtualbank.repository.SavingGoalRepository;


public class Page06_Goal implements DataUpdateListener {
	private JFrame window;
	private JLabel backgroundLabel;	// 盛放背景图的JLabel
	private JPanel goalsPanel; // goalsPanel是被滚动的面板视口
	private JScrollPane scrollPane; // Scroll pane for goalsPanel
	private JButton createButton;
	private JButton exitButton;
	private SavingGoalService goalService;
	private ImageIcon goalBackground;
	private List<JPanel> goalPanels = new ArrayList<>(); // 存储每个目标的面板
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

	// 删除目标的函数
	public void removeGoalPanel(JPanel panel) {
		goalsPanel.remove(panel);
		goalsPanel.revalidate();
		goalsPanel.repaint();
	}

	private void initializeComponents() {
		window = new JFrame("Goal Tracker");
		backgroundLabel = new JLabel();
		goalsPanel = new JPanel();
		scrollPane = new JScrollPane(goalsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		createButton = new JButton();
		exitButton = new JButton();
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
		window.setResizable(false);

		// 配置exitButton
		exitButton.setBounds(16, 2, 135, 57);
		exitButton.addActionListener(e -> window.dispose());
		exitButton.setOpaque(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setBorderPainted(false);
		exitButton.setFocusPainted(false);
		window.add(exitButton);

		// 配置createButton
		createButton.setBounds(85, 180, 350, 56);
		createButton.setOpaque(false);
		createButton.setContentAreaFilled(false);
		createButton.setBorderPainted(false);
		createButton.setFocusPainted(false);
		createButton.addActionListener(e -> createOrModifyGoal(null));  // 添加监听器以创建新目标
		window.add(createButton);

		// 配置scrollPane
		scrollPane.setBounds(500, 125, 620, 580); // 设置滚动面板的位置和大小
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);
		goalsPanel.setOpaque(false);
		goalsPanel.setLayout(new BoxLayout(goalsPanel, BoxLayout.Y_AXIS));
		window.add(scrollPane);

		// 配置backgroundLabel
		backgroundLabel.setIcon(goalBackground);
		backgroundLabel.setBounds(0, 0, window.getWidth(), window.getHeight());
		window.add(backgroundLabel);
	}

	private void loadGoals() {
		List<SavingGoal> goals = goalService.getAllSavingGoals();
		goalsPanel.removeAll();
		goalPanels.clear(); // 清空存储goalCard的ArrayList

		// 遍历每个goal并为其创建、配置goalCard
		for (SavingGoal goal : goals) {
			// 提取每个goal信息
			String goalName = goal.getGoalName();
			double currentAmountNum = goal.getCurrentAmount();
			double targetAmountNum = goal.getTargetAmount();
			String currentAmount = String.format("%.2f", currentAmountNum);
			String targetAmount = String.format("%.2f", targetAmountNum);

			// 创建并配置goalCard
			GoalCard goalCard = new GoalCard(goal, goalName, currentAmount, targetAmount);
			goalPanels.add(goalCard);	// 添加到ArrayList中
			goalsPanel.add(goalCard);	// 添加到滚动面板中
			goalsPanel.add(Box.createVerticalStrut(10));	// 卡片之间留有间隙

		}

		// 重绘滚动面板
		goalsPanel.revalidate();
		goalsPanel.repaint();
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

	// 静态内部类GoalCard
	public static class GoalCard extends JPanel {
		private JLabel valueLabel;
		private JPanel upBarPanel;
		private JPanel downBarPanel;
		private JLabel progressLabel;
		private JButton modifyButton;
		private JButton deleteButton;
		private static final Font valueLabelFont = new Font("Arial", Font.PLAIN, 24);
		private static final Color Background_Color = new Color(0xBCCCDF);
		private static final Color Border_Color = new Color(0x5C5C5C);
		private static final int ARC_WIDTH = 10;
		private static final int ARC_HEIGHT = 10;

		public GoalCard(SavingGoal goal, String goalName, String currentAmount, String targetAmount) {
			setLayout(null);
			setPreferredSize(new Dimension(1600, 150));
			setMaximumSize(new Dimension(1600, 150));

			valueLabel = new JLabel();
			String valueLabelText = String.format(
					"<html><font color='yellow'>%s : </font><font color='blue'>%s</font> / <font color='red'>%s</font></html>",
					goalName, currentAmount, targetAmount);
			valueLabel.setText(valueLabelText);
			valueLabel.setFont(valueLabelFont);
			valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
			valueLabel.setOpaque(false);
			valueLabel.setBorder(null);
			valueLabel.setBounds(10, 15, 480, 45);
			add(valueLabel);

			modifyButton = new JButton();
			modifyButton.putClientProperty("goal", goal);
			modifyButton.setIcon(new ImageIcon("images/Modify_Button.png"));
			modifyButton.setBorderPainted(false);
			modifyButton.setContentAreaFilled(false);
			modifyButton.setFocusPainted(false);
			modifyButton.setBounds(500, 12, 113, 43);
			add(modifyButton);

			deleteButton = new JButton();
			deleteButton.putClientProperty("goal", goal);
			deleteButton.setIcon(new ImageIcon("images/Delete_Button.png"));
			deleteButton.setBorderPainted(false);
			deleteButton.setContentAreaFilled(false);
			deleteButton.setFocusPainted(false);
			deleteButton.setBounds(500, 65, 113, 43);
			add(deleteButton);

			int progressValue = (int) ((goal.getCurrentAmount() / goal.getTargetAmount()) * 100);
			String progressValueText = progressValue + "%";
			progressLabel = new JLabel(progressValueText);
			progressLabel.setFont(valueLabelFont);
			progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
			progressLabel.setOpaque(false);
			progressLabel.setBorder(null);
			progressLabel.setBounds(300, 68, 60, 30);
			add(progressLabel);

			upBarPanel = new JPanel();
			upBarPanel.setBorder(null);

			Color upBarColor = null;
			int upBarWidth = 0;
			if (progressValue < 25) {
				upBarColor = new Color(0xB30000);
				upBarWidth = 50;
			} else if (progressValue < 50) {
				upBarColor = new Color(0xFFAB0A);
				upBarWidth = 105;
			} else if (progressValue < 75) {
				upBarColor = new Color(0xFBFF0A);
				upBarWidth = 165;
			} else if (progressValue < 100) {
				upBarColor = new Color(0x0AFF0F);
				upBarWidth = 240;
			} else {
				upBarColor = new Color(0x0AFF0F);
				upBarWidth = 270;
			}
			upBarPanel.setBackground(upBarColor);
			upBarPanel.setBounds(20, 80, upBarWidth, 8);

			downBarPanel = new JPanel();
			downBarPanel.setBackground(new Color(0x9EABBB));
			downBarPanel.setBorder(null);
			downBarPanel.setBounds(20, 80, 270, 8);
			add(upBarPanel);
			add(downBarPanel);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g2.setColor(Background_Color);
			g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT));

			g2.dispose();
		}

		@Override
		protected void paintBorder(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g2.setColor(Border_Color);
			g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, ARC_WIDTH, ARC_HEIGHT));

			g2.dispose();
		}
	}


	public static void main(String[] args) {
		new Page06_Goal();
	}
}
