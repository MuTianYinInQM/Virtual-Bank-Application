package joyBank;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

public class HistoryLabel extends JLabel{
	
	private JLabel sumLabel;
	private JLabel timeLabel;
	private JLabel dateLabel;
	private JLabel typeLabel;
	private JLabel descLabel;
	
	public HistoryLabel(boolean isIncrease, double sum, String time,
						String date, String type, String desc) {
		super();
		this.sumLabel = new JLabel();
		this.timeLabel = new JLabel();
		this.dateLabel = new JLabel();
		this.typeLabel = new JLabel();
		this.descLabel = new JLabel("<html>"+"</html>");
		
		this.setSize(620, 90);
		this.setIcon(new ImageIcon("images/history_Label.png"));
		this.setOpaque(true);
		
		Font plainLabelFont = new Font(this.sumLabel.getFont().getName(), Font.PLAIN, 20);
		Font boldLabelFont = new Font(this.sumLabel.getFont().getName(), Font.BOLD, 20);
		
		// 流水
		this.sumLabel.setBounds(25, 10, 120, 70);
		this.sumLabel.setFont(boldLabelFont);
		this.sumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		String formattedSum = null;
		if(isIncrease) {
			formattedSum = "+" + String.format("%.2f", sum);
		}
		else {
			formattedSum = "-" + String.format("%.2f", sum);
		}
		this.sumLabel.setText(formattedSum);
		this.sumLabel.setBackground(new Color(255, 127, 127));
//		this.sumLabel.setOpaque(true);
		this.add(this.sumLabel);
		
		// 交易时刻
		this.timeLabel.setBounds(155, 15, 120, 30);
		this.timeLabel.setFont(plainLabelFont);
		this.timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.timeLabel.setText(time);
		this.timeLabel.setBackground(new Color(255, 127, 127));
//		this.timeLabel.setOpaque(true);
		this.add(this.timeLabel);

		// 交易日期
		this.dateLabel.setBounds(155, 45, 120, 30);
		this.dateLabel.setFont(plainLabelFont);
		this.dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.dateLabel.setText(date);
		this.dateLabel.setBackground(new Color(255, 127, 127));
//		this.dateLabel.setOpaque(true);
		this.add(this.dateLabel);

		// 交易类型
		this.typeLabel.setBounds(285, 10, 120, 70);
		this.typeLabel.setFont(plainLabelFont);
		this.typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.typeLabel.setText(type);
		this.typeLabel.setBackground(new Color(255, 127, 127));
//		this.typeLabel.setOpaque(true);
		this.add(this.typeLabel);

		// 描述（暂时无法自动换行）
		this.descLabel.setBounds(415, 10, 190, 70);
		this.descLabel.setFont(plainLabelFont);
		this.descLabel.setHorizontalAlignment(SwingConstants.LEFT);
		this.descLabel.setVerticalAlignment(SwingConstants.CENTER);
		this.descLabel.setText("<html>"+desc+"</html>");
//		this.descLabel.setPreferredSize(new Dimension(180, 70));
		this.descLabel.setBackground(new Color(255,127,127));
//		this.descLabel.setOpaque(true);
		this.add(this.descLabel);
	}
	
	
	
	public JLabel getSumLabel() {
		return sumLabel;
	}



	public JLabel getTimeLabel() {
		return timeLabel;
	}



	public JLabel getDateLabel() {
		return dateLabel;
	}



	public JLabel getTypeLabel() {
		return typeLabel;
	}



	public JLabel getDescLabel() {
		return descLabel;
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, 1260, 780);
		frame.setLayout(null);
		HistoryLabel history1 = new HistoryLabel(true, 20.00, "13:20", "03-11-2023", "prize", "discription");
		frame.add(history1);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
