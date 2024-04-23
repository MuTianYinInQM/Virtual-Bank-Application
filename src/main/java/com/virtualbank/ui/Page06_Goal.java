package joyBank;
import java.awt.*;
import javax.swing.*;

public class Page06_Goal {
	private JFrame window;
	private JButton exitButton;
	private JTextField goalField;
	private JButton modifyButton;
	private JLabel encourageLabel;
	
	public Page06_Goal() {
		super();
		
		this.window = new JFrame();
		this.exitButton = new JButton();
		this.goalField = new JTextField();
		this.modifyButton = new JButton();
		this.encourageLabel = new JLabel();
		
		// window
		final int window_width = 1260;
		final int window_height = 780;
		this.window.setBounds(0, 0, window_width, window_height);
		this.window.setResizable(false);
		this.window.setLayout(null);
		ImageIcon goalBackground = new ImageIcon("images/Goal_Background.png");
		JLabel backgroundLabel = new JLabel(goalBackground);
		backgroundLabel.setBounds(0, 0, window_width, window_height);
		this.window.setContentPane(backgroundLabel);
		
		// goal_field
		this.window.add(this.goalField);
		this.goalField.setBounds(450, 135, 361, 105);
		Font goalField_font = new Font(this.goalField.getFont().getName(), Font.PLAIN, 28);
		this.goalField.setFont(goalField_font);
		this.goalField.setHorizontalAlignment(SwingConstants.CENTER);
		this.goalField.setText("00.00/00.00");
		
		// exit_button
		this.window.add(this.exitButton);
		this.exitButton.setBounds(20, 20, 100, 50);
		Font exitButton_font = new Font(this.exitButton.getFont().getName(), Font.PLAIN, 20);
		this.exitButton.setFont(exitButton_font);
		this.exitButton.setHorizontalAlignment(SwingConstants.CENTER);
		this.exitButton.setText("Exit");
		
		// modify_button
		this.window.add(this.modifyButton);
		this.modifyButton.setBounds(558, 307, 145, 62);
		this.modifyButton.setIcon(new ImageIcon("images/Modify_Button.png"));
		this.modifyButton.setBorderPainted(false);
		
		// encourageLabel
		this.window.add(this.encourageLabel);
		this.encourageLabel.setBounds(300, 380, 660, 105);
		Font encourageLabel_font = new Font(this.encourageLabel.getFont().getName(), Font.PLAIN, 20);
		this.encourageLabel.setFont(encourageLabel_font);
		this.encourageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.encourageLabel.setText("You have not set a goal yet. Try now!");
		
		
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setVisible(true);
	}
			
	public JButton getExitButton() {
		return exitButton;
	}
	
	public JTextField getGoalField() {
		return goalField;
	}

	public JButton getModifyButton() {
		return modifyButton;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Page06_Goal page = new Page06_Goal();
		
	}

}
