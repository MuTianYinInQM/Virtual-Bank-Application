package joyBank;

import java.awt.Font;

import javax.swing.*;

public class Page07_CurrentAccount {
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
	private JButton historyButton;
	private JButton transferButton;
	private JButton deleteButton;
	
	public Page07_CurrentAccount() {
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
		this.historyButton = new JButton();
		this.transferButton = new JButton();
		this.deleteButton = new JButton();
		
		// window
		final int window_width = 1260;
		final int window_height = 780;
		this.window.setBounds(0, 0, window_width, window_height);
		this.window.setResizable(false);
		this.window.setLayout(null);
		ImageIcon goalBackground = new ImageIcon("images/Current_Account.png");
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
		
		// id1, balance1, type1, interest1 (waiting to be improved)
		this.window.add(this.id1);
		this.id1.setBounds(340, 140, 200, 50);
		this.window.add(this.balance1);
		this.balance1.setBounds(340, 205, 200, 50);
		this.window.add(this.type1);
		this.type1.setBounds(340, 270, 200, 50);		
		this.window.add(this.interest1);
		this.interest1.setBounds(340, 335, 200, 50);
		Font boldLabelFont = new Font(this.id1.getFont().getName(), Font.BOLD, 28);
		this.id1.setFont(boldLabelFont);
		this.balance1.setFont(boldLabelFont);
		this.type1.setFont(boldLabelFont);
		this.interest1.setFont(boldLabelFont);
		this.id1.setHorizontalAlignment(SwingConstants.CENTER);
		this.balance1.setHorizontalAlignment(SwingConstants.CENTER);
		this.type1.setHorizontalAlignment(SwingConstants.CENTER);
		this.interest1.setHorizontalAlignment(SwingConstants.CENTER);
		this.id1.setText("ID");
		this.balance1.setText("Balance");
		this.type1.setText("Type");
		this.interest1.setText("Interest");
		
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
		this.typeLabel.setText("Current Account");
		
		// interestLabel
		this.window.add(this.interestLabel);
		this.interestLabel.setBounds(540, 335, 300, 50);
		this.interestLabel.setFont(plainLabelFont);
		this.interestLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		this.interestLabel.setText("624.54");
		
		// historyButton
		this.window.add(this.historyButton);
		this.historyButton.setBounds(340, 420, 233, 85);
		this.historyButton.setIcon(new ImageIcon("images/history_Button.png"));
		this.historyButton.setBorderPainted(false);
		
		// transferButton
		this.window.add(this.transferButton);
		this.transferButton.setBounds(660, 420, 233, 85);
		this.transferButton.setIcon(new ImageIcon("images/transfer_Button.png"));
		this.transferButton.setBorderPainted(false);
		
		
		
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


	public JButton getHistoryButton() {
		return historyButton;
	}


	public JButton getTransferButton() {
		return transferButton;
	}


	public JButton getDeleteButton() {
		return deleteButton;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Page07_CurrentAccount page = new Page07_CurrentAccount();
	}

}
