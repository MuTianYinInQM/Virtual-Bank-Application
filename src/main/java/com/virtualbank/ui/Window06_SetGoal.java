package joyBank;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class Window06_SetGoal {
	private JFrame window;
	private JLabel setLabel;
	private JTextField setField;
	private JButton confirmButton;
	
	public Window06_SetGoal() {
		super();
		
		this.window = new JFrame();
		this.setLabel = new JLabel();
		this.setField = new JTextField();
		this.confirmButton = new JButton();
		
		final int window_width = 600;
		final int window_height = 378;
		this.window.setBounds(330, 200, window_width, window_height);
        this.window.setResizable(false);
        this.window.setLayout(null);
        this.window.setTitle("JoyBank - Set Your Goal");
        ImageIcon windowBackground = new ImageIcon("images/Window_Background.png");
        JLabel windowBackgroundLabel = new JLabel(windowBackground);
        windowBackgroundLabel.setBounds(0, 0, window_width, window_height);
        this.window.setContentPane(windowBackgroundLabel);

        this.window.add(this.setLabel);
        this.setLabel.setBounds(80, 100, 100, 40);
        Font setLabel_font = new Font(this.setLabel.getFont().getName(), Font.PLAIN, 20);
        this.setLabel.setFont(setLabel_font);
        this.setLabel.setText("Set Goal");
        
        this.window.add(this.setField);
        this.setField.setBounds(200, 100, 320, 40);
        this.setField.setFont(setLabel_font);
        
        this.window.add(this.confirmButton);
        this.confirmButton.setBounds(240, 220, 120, 40);
        this.confirmButton.setFont(new Font(this.confirmButton.getFont().getName(), Font.PLAIN, 20));
        this.confirmButton.setHorizontalAlignment(SwingConstants.CENTER);
        this.confirmButton.setBorderPainted(false);
        this.confirmButton.setText("confirm");
        
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setVisible(true);
	}
	
		
	public JFrame getWindow() {
		return window;
	}

	public JLabel getSetLabel() {
		return setLabel;
	}

	public JTextField getSetField() {
		return setField;
	}

	public JButton getConfirmButton() {
		return confirmButton;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Window06_SetGoal setGoal = new Window06_SetGoal();
	}

}
