package com.virtualbank.ui;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Window07_Transfer {
	private JFrame window;
	private JLabel transferLabel;
	private JComboBox transferBox;
	private JLabel sumLabel;
	private JTextField sumField;
	private JButton okButton;
	
	public Window07_Transfer(){
		super();
		this.window = new JFrame();
		this.transferLabel = new JLabel();
		this.transferBox = new JComboBox();
		this.sumLabel = new JLabel();
		this.sumField = new JTextField();
		this.okButton = new JButton();
		
		final int window_width = 600;
		final int window_height = 378;
		this.window.setBounds(330, 200, window_width, window_height);
        this.window.setResizable(false);
        this.window.setLayout(null);
        this.window.setTitle("JoyBank - Save into PiggyBank");
        ImageIcon windowBackground = new ImageIcon("images/Window_Background.png");
        JLabel windowBackgroundLabel = new JLabel(windowBackground);
        windowBackgroundLabel.setBounds(0, 0, window_width, window_height);
        this.window.setContentPane(windowBackgroundLabel);
		
        Font plainLabelFont = new Font(this.transferLabel.getFont().getName(), Font.PLAIN, 20);
        
        // transferLabel
        this.window.add(this.transferLabel);
        this.transferLabel.setBounds(60, 80, 120, 40);
        this.transferLabel.setFont(plainLabelFont);
        this.transferLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.transferLabel.setText("Sum");
       
        // transferBox
        this.window.add(this.transferBox);
        this.transferBox.setBounds(200, 80, 320, 40);
        this.transferBox.setFont(plainLabelFont);
        
        // sumLabel
        this.window.add(this.sumLabel);
        this.sumLabel.setBounds(60, 140, 120, 40);
        this.sumLabel.setFont(plainLabelFont);
        this.sumLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.sumLabel.setText("Description");
        
        // sumField
        this.window.add(this.sumField);
        this.sumField.setBounds(200, 140, 320, 40);
        this.sumField.setFont(plainLabelFont);
        
        // okButton
        this.window.add(this.okButton);
        this.okButton.setBounds(240, 220, 120, 40);
        this.okButton.setFont(plainLabelFont);
        this.okButton.setHorizontalAlignment(SwingConstants.CENTER);
        this.okButton.setText("OK");
        
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Window07_Transfer transfer = new Window07_Transfer();
	}

}
