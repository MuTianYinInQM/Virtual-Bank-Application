package com.virtualbank.ui;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Page07a_SavingAccount {
	private JFrame window;
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
		super();
		
		this.window = new JFrame();
		this.exitButton = new JButton();
		this.id1 = new JLabel();
		this.idLabel = new JLabel();
		this.balance1 = new JLabel();
		this.balanceLabel = new JLabel();
		this.type1 = new JLabel();
		this.typeLabel = new JLabel();
		this.interest1 = new JLabel();
		this.interestLabel = new JLabel();
		this.deleteButton = new JButton();
		this.start_date1 = new JLabel();
		this.start_dateLabel = new JLabel();
		this.end_date1 = new JLabel();
		this.end_dateLabel = new JLabel();
		this.duration1 = new JLabel();
		this.durationLabel = new JLabel();
		
		// window
		final int window_width = 1260;
		final int window_height = 780;
		this.window.setBounds(0, 0, window_width, window_height);
		this.window.setResizable(false);
		this.window.setLayout(null);
		ImageIcon goalBackground = new ImageIcon("images/Saving_Account.png");
		JLabel backgroundLabel = new JLabel(goalBackground);
		backgroundLabel.setBounds(0, 0, window_width, window_height);
		this.window.setContentPane(backgroundLabel);
		
		// exit_button
		this.window.add(this.exitButton);
		this.exitButton.setBounds(20, 20, 100, 50);
		Font exitButton_font = new Font(this.exitButton.getFont().getName(), Font.PLAIN, 20);
		this.exitButton.setFont(exitButton_font);
		this.exitButton.setHorizontalAlignment(SwingConstants.CENTER);
		this.exitButton.setText("Exit");
		
		// id1, balance1, type1, interest1, start_date1, end_date1, duration1 (waiting to be improved)
		this.window.add(this.id1);
		this.id1.setBounds(340, 140, 200, 50);
		this.window.add(this.balance1);
		this.balance1.setBounds(340, 205, 200, 50);
		this.window.add(this.type1);
		this.type1.setBounds(340, 270, 200, 50);		
		this.window.add(this.interest1);
		this.interest1.setBounds(340, 335, 200, 50);
		this.window.add(this.start_date1);
		this.start_date1.setBounds(340, 400, 200, 50);
		this.window.add(this.end_date1);
		this.end_date1.setBounds(340, 465, 200, 50);
		this.window.add(this.duration1);
		this.duration1.setBounds(340, 530, 200, 50);
		
		Font boldLabelFont = new Font(this.id1.getFont().getName(), Font.BOLD, 28);
		this.id1.setFont(boldLabelFont);
		this.balance1.setFont(boldLabelFont);
		this.type1.setFont(boldLabelFont);
		this.interest1.setFont(boldLabelFont);
		this.start_date1.setFont(boldLabelFont);
		this.end_date1.setFont(boldLabelFont);
		this.duration1.setFont(boldLabelFont);
		this.id1.setHorizontalAlignment(SwingConstants.CENTER);
		this.balance1.setHorizontalAlignment(SwingConstants.CENTER);
		this.type1.setHorizontalAlignment(SwingConstants.CENTER);
		this.interest1.setHorizontalAlignment(SwingConstants.CENTER);
		this.start_date1.setHorizontalAlignment(SwingConstants.CENTER);
		this.end_date1.setHorizontalAlignment(SwingConstants.CENTER);
		this.duration1.setHorizontalAlignment(SwingConstants.CENTER);
		this.id1.setText("ID");
		this.balance1.setText("Balance");
		this.type1.setText("Type");
		this.interest1.setText("Interest");
		this.start_date1.setText("Start Date");
		this.end_date1.setText("End Date");
		this.duration1.setText("Duration");
		
		Font plainLabelFont = new Font(boldLabelFont.getName(), Font.PLAIN, 28);
		
		// idLabel
		this.window.add(this.idLabel);
		this.idLabel.setBounds(540, 140, 300, 50);
		this.idLabel.setFont(plainLabelFont);
		this.idLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		this.idLabel.setText("dfskhkbibvindsind");
		
		// balanceLabel
		this.window.add(this.balanceLabel);
		this.balanceLabel.setBounds(540, 205, 300, 50);
		this.balanceLabel.setFont(plainLabelFont);
		this.balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		this.balanceLabel.setText("6874350");
		
		// typeLabel
		this.window.add(this.typeLabel);
		this.typeLabel.setBounds(540, 270, 300, 50);
		this.typeLabel.setFont(plainLabelFont);
		this.typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.typeLabel.setText("Saving Account");
		
		// interestLabel
		this.window.add(this.interestLabel);
		this.interestLabel.setBounds(540, 335, 300, 50);
		this.interestLabel.setFont(plainLabelFont);
		this.interestLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		this.interestLabel.setText("624.54");
		
		// start_dateLabel
		this.window.add(this.start_dateLabel);
		this.start_dateLabel.setBounds(540, 400, 300, 50);
		this.start_dateLabel.setFont(plainLabelFont);
		this.start_dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		this.start_dateLabel.setText("csjkjsvnk");
				
		// end_dateLabel
		this.window.add(this.end_dateLabel);
		this.end_dateLabel.setBounds(540, 465, 300, 50);
		this.end_dateLabel.setFont(plainLabelFont);
		this.end_dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		this.end_dateLabel.setText("dvjsifsf");
		
		// durationLabel
		this.window.add(this.durationLabel);
		this.durationLabel.setBounds(540, 530, 300, 50);
		this.durationLabel.setFont(plainLabelFont);
		this.durationLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		this.durationLabel.setText("644452286");
				
		// deleteButton
		this.window.add(this.deleteButton);
		this.deleteButton.setBounds(390, 630, 460, 75);
		this.deleteButton.setIcon(new ImageIcon("images/delete_Button.png"));
		this.deleteButton.setBorderPainted(false);
		
		
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setVisible(true);		
	}
	
	public JButton getExitButton() {
		return exitButton;
	}

	public JLabel getIdLabel() {
		return idLabel;
	}

	public JLabel getBalanceLabel() {
		return balanceLabel;
	}

	public JLabel getTypeLabel() {
		return typeLabel;
	}

	public JLabel getInterestLabel() {
		return interestLabel;
	}

	public JLabel getStart_dateLabel() {
		return start_dateLabel;
	}

	public JLabel getEnd_dateLabel() {
		return end_dateLabel;
	}

	public JLabel getDurationLabel() {
		return durationLabel;
	}

	public JButton getDeleteButton() {
		return deleteButton;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Page07a_SavingAccount page = new Page07a_SavingAccount();
	}

}
