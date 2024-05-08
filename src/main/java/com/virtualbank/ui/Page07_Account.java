package com.virtualbank.ui;

import com.virtualbank.model.account.*;
import java.awt.*;
import javax.swing.*;

public class Page07_Account extends JFrame {
    private Account currentAccount;

    public Page07_Account(Account account) {
        this.currentAccount = account;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Account Details");
        setSize(1260, 780);  // Set the size to match the Current Account window
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);  // Use null layout for absolute positioning

        if (currentAccount instanceof CurrentAccount) {
            setupCurrentAccountUI();
        }
    }

    private void setupCurrentAccountUI() {
        ImageIcon goalBackground = new ImageIcon("images/Current_Account.png");
        JLabel backgroundLabel = new JLabel(goalBackground);
        backgroundLabel.setBounds(0, 0, 1260, 780);
        this.setContentPane(backgroundLabel);

        JButton exitButton = createButton("Exit", 20, 20, 100, 50);
        this.add(exitButton);

        JLabel idLabel = createLabel("ID", 340, 140, 200, 50, Font.BOLD, 28);
        JLabel balanceLabel = createLabel("Balance", 340, 205, 200, 50, Font.BOLD, 28);
        JLabel typeLabel = createLabel("Type", 340, 270, 200, 50, Font.BOLD, 28);
        JLabel interestLabel = createLabel("Interest", 340, 335, 200, 50, Font.BOLD, 28);
        this.add(idLabel);
        this.add(balanceLabel);
        this.add(typeLabel);
        this.add(interestLabel);

        JLabel dataIdLabel = createDataLabel("", 540, 140, Font.PLAIN, 28);
        JLabel dataBalanceLabel = createDataLabel("", 540, 205, Font.PLAIN, 28);
        JLabel dataTypeLabel = createDataLabel("Current Account", 540, 270, Font.PLAIN, 28);
        JLabel dataInterestLabel = createDataLabel("", 540, 335, Font.PLAIN, 28);
        this.add(dataIdLabel);
        this.add(dataBalanceLabel);
        this.add(dataTypeLabel);
        this.add(dataInterestLabel);

        JButton historyButton = createButton("images/history_Button.png", 340, 420, 233, 85);
        JButton transferButton = createButton("images/transfer_Button.png", 660, 420, 233, 85);
        JButton deleteButton = createButton("images/delete_Button.png", 500, 630, 233, 85);
        this.add(historyButton);
        this.add(transferButton);
        this.add(deleteButton);

        this.setVisible(true);
    }

    private JButton createButton(String textOrIconPath, int x, int y, int width, int height) {
        JButton button = new JButton();
        button.setBounds(x, y, width, height);
        button.setBorderPainted(false);

        // 检查路径是否为图像路径（简单的方式是检查是否有文件扩展名）
        if (textOrIconPath.endsWith(".png") || textOrIconPath.endsWith(".jpg")) {
            // 创建图像按钮
            button.setIcon(new ImageIcon(textOrIconPath));
        } else {
            // 创建文本按钮
            button.setText(textOrIconPath);
            button.setFont(new Font("Arial", Font.PLAIN, 20)); // 设置字体，您可以根据需要调整字体大小
        }

        return button;
    }


    private JLabel createLabel(String text, int x, int y, int width, int height, int style, int fontSize) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Arial", style, fontSize));
        return label;
    }

    private JLabel createDataLabel(String text, int x, int y, int style, int fontSize) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setBounds(x, y, 300, 50);
        label.setFont(new Font("Arial", style, fontSize));
        return label;
    }

    public static void main(String[] args) {
        Account exampleAccount = new CurrentAccount("Example Account",
                1000, 0.02, 24);
        Page07_Account window = new Page07_Account(exampleAccount);
        window.setVisible(true);
    }
}
