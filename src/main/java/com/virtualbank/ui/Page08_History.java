//package com.virtualbank.ui;
//
//import java.awt.Color;
//import java.awt.Font;
//
//import javax.swing.*;
//
//public class Page08_History {
//	private JFrame window;
//	private JButton exitButton;
//
//	public Page08_History() {
//		this.window = new JFrame();
//		this.exitButton = new JButton();
//
//		// window
//		final int window_width = 1260;
//		final int window_height = 780;
//		this.window.setBounds(0, 0, window_width, window_height);
//		this.window.setResizable(false);
//		this.window.setLayout(null);
//		ImageIcon goalBackground = new ImageIcon("images/History_Background.png");
//		JLabel backgroundLabel = new JLabel(goalBackground);
//		backgroundLabel.setBounds(0, 0, window_width, window_height);
//		this.window.setContentPane(backgroundLabel);
//
//		// exitButton
//		this.exitButton.setBounds(20, 20, 100, 50);
//		Font exitButton_font = new Font(this.exitButton.getFont().getName(), Font.PLAIN, 20);
//		this.exitButton.setFont(exitButton_font);
//		this.exitButton.setHorizontalAlignment(SwingConstants.CENTER);
//		this.exitButton.setText("Exit");
//		this.window.add(this.exitButton);
//
//		// scrollPanel
//		JPanel scrollPanel = new JPanel();
//		scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
//        scrollPanel.setBackground(new Color(248, 248, 235));
//		JScrollPane scrollPane = new JScrollPane(scrollPanel);
//        scrollPane.setBounds(300, 180, 640, 400); // 调整位置和大小
////        scrollPane.setBackground(new Color(248, 248, 230));
//        scrollPane.setBorder(BorderFactory.createEmptyBorder());
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPanel.add(Box.createVerticalStrut(10));
//		this.window.add(scrollPane);
//
//        // 示例的history记录
//        HistoryLabel history1 = new HistoryLabel(false, 9999.00, "14:00", "21-04-2024", "consume", "buy iPhone 15 Pro Max");
//        scrollPanel.add(history1);
//        scrollPanel.add(Box.createVerticalStrut(10));
//
//        HistoryLabel history2 = new HistoryLabel(true, 6.27, "14:00", "21-04-2024", "interest", "no description");
//        scrollPanel.add(history2);
//        scrollPanel.add(Box.createVerticalStrut(10));
//
//        HistoryLabel history3 = new HistoryLabel(true, 377.00, "13:00", "21-04-2024", "prize", "from task \"Shot Video\"");
//        scrollPanel.add(history3);
//        scrollPanel.add(Box.createVerticalStrut(10));
//
//		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.window.setVisible(true);
//	}
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		Page08_History history = new Page08_History();
//	}
//}
