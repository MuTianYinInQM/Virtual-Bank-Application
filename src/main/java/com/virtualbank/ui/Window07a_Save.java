package joyBank;

import java.awt.Font;

import javax.swing.*;

public class Window07a_Save {
	private JFrame window;
	private JLabel sumLabel;
	private JTextField sumField;
	private JLabel descLabel;
	private JTextField descField;
	private JButton okButton;
	
	public Window07a_Save() {
		super();
		this.window = new JFrame();
		this.sumLabel = new JLabel();
		this.sumField = new JTextField();
		this.descLabel = new JLabel();
		this.descField = new JTextField();
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
		
        Font plainLabelFont = new Font(this.sumLabel.getFont().getName(), Font.PLAIN, 20);
        
        // sumLabel
        this.window.add(this.sumLabel);
        this.sumLabel.setBounds(60, 80, 120, 40);
        this.sumLabel.setFont(plainLabelFont);
        this.sumLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.sumLabel.setText("Sum");
        
        // sumField
        this.window.add(this.sumField);
        this.sumField.setBounds(200, 80, 320, 40);
        this.sumField.setFont(plainLabelFont);
        
        // descLabel
        this.window.add(this.descLabel);
        this.descLabel.setBounds(60, 140, 120, 40);
        this.descLabel.setFont(plainLabelFont);
        this.descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.descLabel.setText("Description");
        
        // descField
        this.window.add(this.descField);
        this.descField.setBounds(200, 140, 320, 40);
        this.descField.setFont(plainLabelFont);
        
        // okButton
        this.window.add(this.okButton);
        this.okButton.setBounds(240, 220, 120, 40);
        this.okButton.setFont(plainLabelFont);
        this.okButton.setHorizontalAlignment(SwingConstants.CENTER);
        this.okButton.setText("OK");
        
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setVisible(true);
	}
	
	public JTextField getSumField() {
		return sumField;
	}

	public JTextField getDescField() {
		return descField;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Window07a_Save save = new Window07a_Save();
	}

}
