package com.virtualbank.ui;

import com.virtualbank.model.AccountManager;
import com.virtualbank.model.account.*;

import javax.swing.*;
import java.awt.*;
import java.time.Period;
import java.util.UUID;

public class Page03_ChildHome extends JFrame {

    private JButton exitButton = new JButton("Exit");
    private JButton goalButton = new JButton();
    private JButton taskButton = new JButton();
    private JButton createAccountButton = new JButton();
    private JPanel scrollPanel; // 将滚动面板声明为类的成员变量
    private JLabel goalButtonLabel; // 处理目标和总金额的

    public JButton getExitButton() {
        return exitButton;
    }

    public JButton getGoalButton() {
        return goalButton;
    }

    public JButton getTaskButton() {
        return taskButton;
    }

    public JButton getCreateAccountButton() {
        return createAccountButton;
    }


    public Page03_ChildHome() {
        final int window_width = 1260;
        final int window_height = 780;
        setBounds(0, 0, window_width, window_height);
        setResizable(false);
        setLayout(null);
        setTitle("JoyBank - Child Home Page");
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(0xf8f6ea));
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, window_width, window_height);
        add(backgroundPanel);

        JLabel sticker = new JLabel(new ImageIcon("images/StickerForAccount.png"));
        sticker.setBounds(1060, 550, 200, 200);
        backgroundPanel.add(sticker);

        // 返回上一级，LoginPage的按钮Exit
        exitButton.setBounds(20, 20, 100, 50);
        Font font = new Font(exitButton.getFont().getName(), Font.PLAIN, 20);
        exitButton.setFont(font);
        backgroundPanel.add(exitButton);

        // 进入孩子目标页面的Goal按钮
        goalButton.setBounds(371, 80, 220, 109);
        ImageIcon goalButtonIcon = new ImageIcon("images/GoalButton.png");
        goalButton.setIcon(goalButtonIcon);
        // 目标及其完成进度
        // TODO 现在这部分还不能变化 目标不归我管 怎么处理
        Double currentAmount = 2544.00; // 当前进度
        Double goal = 500.00; // 目标
        String goalText = String.format("%.2f/%.2f", currentAmount, goal);
        JLabel initialGoalButtonLabel = new JLabel(goalText);
        initialGoalButtonLabel.setBounds(42, 20, 220, 109);
        initialGoalButtonLabel.setHorizontalAlignment(SwingConstants.CENTER);
        goalButton.setLayout(null);
        goalButton.add(initialGoalButtonLabel);
        backgroundPanel.add(goalButton);
        this.goalButtonLabel = initialGoalButtonLabel;

        // 进入孩子任务页面的Task按钮
        taskButton.setBounds(672, 80, 220, 110);
        ImageIcon taskButtonIcon = new ImageIcon("images/TaskButton.png");
        taskButton.setIcon(taskButtonIcon);
        backgroundPanel.add(taskButton);

        // 创建新账户的按钮
        createAccountButton.setBounds(369, 200, 523, 64);
        ImageIcon createAccountButtonIcon = new ImageIcon("images/CreateAccountButton.png");
        createAccountButton.setIcon(createAccountButtonIcon);
        backgroundPanel.add(createAccountButton);

        // 创建带滚动条的区域
        JPanel initialScrollPanel = new JPanel();
        initialScrollPanel.setLayout(new BoxLayout(initialScrollPanel, BoxLayout.Y_AXIS));
        initialScrollPanel.setBackground(new Color(0xf8f6ea));
        JScrollPane scrollPane = new JScrollPane(initialScrollPanel);
        scrollPane.setBounds(369, 280, 550, 400); // 调整位置和大小
        scrollPane.setBackground(new Color(0xf8f6ea));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        backgroundPanel.add(scrollPane);

        this.scrollPanel = initialScrollPanel;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }


    // 动态更新账户信息的方法
    public void updateAccounts(AccountManager accountManager) {
        //重新绘制总金额
        Double totalBalance = accountManager.getTotalBalance();
        // TODO goal 应该可以更改 相信后人的智慧
        final double goal = 500;
        String goalText = String.format("%.2f/%.2f", totalBalance, goal);
        goalButtonLabel.setText(goalText);
        goalButtonLabel.repaint(); // 重新绘制文本

        //重新绘制所有的银行卡
        scrollPanel.removeAll(); // 清空当前显示的所有账户信息

        for (Account account : accountManager.getAccounts().values()) {
            AccountLabel label;
            if (account instanceof CurrentAccount) {
                label = new CurrentAccountLabel(
                        account.getUuid().toString(),
                        account.getBalance()
                );
            } else if (account instanceof PiggyBank) {
                label = new PiggyBankLabel(
                        account.getUuid().toString(),
                        account.getBalance()
                );
            } else if (account instanceof SavingAccount) {
                label = new SavingAccountLabel(
                        account.getUuid().toString(),
                        account.getBalance()
                );
            } else {
                // 处理未知类型的账户
                continue;
            }
            scrollPanel.add(label);
            scrollPanel.add(Box.createVerticalStrut(10)); // 添加间隔
        }

        scrollPanel.revalidate();
        scrollPanel.repaint();
    }

    private class CurrentAccountLabel extends AccountLabel { // 活期账户卡片
        public CurrentAccountLabel(String ID, Double money) {
            super(ID, money, "Current Account");
            // 图标
            ImageIcon savingAccountIcon = new ImageIcon("images/CurrentAccountFrame.png");
            this.setIcon(savingAccountIcon);
        }
    }

    private class SavingAccountLabel extends AccountLabel {
        public SavingAccountLabel(String ID, Double money) {
            super(ID, money, "Saving Account");
            // 图标
            ImageIcon currentAccountIcon = new ImageIcon("images/SavingAccountFrame.png");
            this.setIcon(currentAccountIcon);
        }
    }

    private class PiggyBankLabel extends AccountLabel {
        public PiggyBankLabel(String ID, Double money) {
            super(ID, money, "Piggy Bank");
            // 图标
            ImageIcon currentAccountIcon = new ImageIcon("images/PiggybankFrame.png");
            this.setIcon(currentAccountIcon);
        }
    }

    public class AccountLabel extends JLabel {

        private JButton enterButton;

        public JButton getEnterButton() {
            return enterButton;
        }

        public AccountLabel(String ID, Double money, String type) {
            this.setSize(523, 106);
            Font font = new Font(this.getFont().getName(), Font.PLAIN, 22);
            // 账户ID
            JLabel idLabel = new JLabel("ID:" + ID);
            idLabel.setBounds(133, -20, 220, 109);
            idLabel.setFont(font);
            this.add(idLabel);
            // 账户金额
            String moneyText = String.format("$ %.2f", money);
            JLabel moneyLabel = new JLabel(moneyText);
            moneyLabel.setBounds(350, -20, 220, 109);
            moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            moneyLabel.setFont(font);
            this.add(moneyLabel);
            // 类型名称
            Font nameFont = new Font(this.getFont().getName(), Font.PLAIN, 25);
            JLabel typeLabel = new JLabel(type);
            typeLabel.setBounds(133, 20, 220, 109);
            typeLabel.setFont(nameFont);
            this.add(typeLabel);
            // 进入账户的按钮 Enter
            // TODO 这个应该怎么回调？
            enterButton = new JButton("Enter");
            enterButton.setBounds(425, 55, 80, 40);
            this.add(enterButton);
        }
    }

    // 测试该页面的代码
    public static void main(String[] args) {
        Page03_ChildHome page03ChildHome = new Page03_ChildHome();

        AccountManager accountManager = new AccountManager();
        accountManager.prize(accountManager.getPiggyUuid(), 100, "Initial money added to Piggy Bank");
        UUID currentAccountId = accountManager.addCurrentAccount("CurrentAccount", 0.01, 24);
        UUID savingAccountId = accountManager.addSavingAccount("SavingAccount",
                50, 0.05, 24, Period.ofYears(1));
        accountManager.save(currentAccountId, 1000, "Initial deposit to Current Account");

        page03ChildHome.updateAccounts(accountManager);
    }


}