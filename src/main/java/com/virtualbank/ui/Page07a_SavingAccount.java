package com.virtualbank.ui;

import java.awt.Font;
import javax.swing.*;

public class Page07a_SavingAccount extends JFrame {
	private JButton exitButton;
	private JLabel id1;
	private JLabel idLabel;
	private JLabel balance1;
	private JLabel balanceLabel;
	private JLabel type1;
	private JLabel typeLabel;
	private JLabel interest1;
	private JLabel interestLabel;
	private JLabel start_date1;
	private JLabel start_dateLabel;
	private JLabel end_date1;
	private JLabel end_dateLabel;
	private JLabel duration1;
	private JLabel durationLabel;
	private JButton deleteButton;

	public Page07a_SavingAccount() {
		super("Saving Account Page"); // Set the title of the JFrame

		// window settings
		final int window_width = 1260;
		final int window_height = 780;
		this.setBounds(0, 0, window_width, window_height);
		this.setResizable(false);
		this.setLayout(null);
		ImageIcon goalBackground = new ImageIcon("images/Saving_Account.png");
		JLabel backgroundLabel = new JLabel(goalBackground);
		backgroundLabel.setBounds(0, 0, window_width, window_height);
		this.setContentPane(backgroundLabel);

		// exit_button
		this.exitButton = new JButton("Exit");
		this.exitButton.setBounds(20, 20, 100, 50);
		Font exitButton_font = new Font("Arial", Font.PLAIN, 20);
		this.exitButton.setFont(exitButton_font);
		this.exitButton.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(this.exitButton);

		// Labels and Text Fields
		Font boldLabelFont = new Font("Arial", Font.BOLD, 28);
		Font plainLabelFont = new Font("Arial", Font.PLAIN, 28);

		createLabel(this.id1 = new JLabel("ID"), 340, 140, boldLabelFont);
		createLabel(this.balance1 = new JLabel("Balance"), 340, 205, boldLabelFont);
		createLabel(this.type1 = new JLabel("Type"), 340, 270, boldLabelFont);
		createLabel(this.interest1 = new JLabel("Interest"), 340, 335, boldLabelFont);
		createLabel(this.start_date1 = new JLabel("Start Date"), 340, 400, boldLabelFont);
		createLabel(this.end_date1 = new JLabel("End Date"), 340, 465, boldLabelFont);
		createLabel(this.duration1 = new JLabel("Duration"), 340, 530, boldLabelFont);

		createDataLabel(this.idLabel = new JLabel(), 540, 140, plainLabelFont);
		createDataLabel(this.balanceLabel = new JLabel(), 540, 205, plainLabelFont);
		createDataLabel(this.typeLabel = new JLabel("Saving Account"), 540, 270, plainLabelFont);
		createDataLabel(this.interestLabel = new JLabel(), 540, 335, plainLabelFont);
		createDataLabel(this.start_dateLabel = new JLabel(), 540, 400, plainLabelFont);
		createDataLabel(this.end_dateLabel = new JLabel(), 540, 465, plainLabelFont);
		createDataLabel(this.durationLabel = new JLabel(), 540, 530, plainLabelFont);

		// deleteButton
		this.deleteButton = new JButton(new ImageIcon("images/delete_Button.png"));
		this.deleteButton.setBounds(390, 630, 460, 75);
		this.deleteButton.setBorderPainted(false);
		this.add(this.deleteButton);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void createLabel(JLabel label, int x, int y, Font font) {
		label.setBounds(x, y, 200, 50);
		label.setFont(font);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(label);
	}

	private void createDataLabel(JLabel label, int x, int y, Font font) {
		label.setBounds(x, y, 300, 50);
		label.setFont(font);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(label);
	}

	public static void main(String[] args) {
		new Page07a_SavingAccount();
	}
}
