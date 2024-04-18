import javax.swing.*;
import java.awt.*;

public class Page03_ChildHome {

    private JButton exitButton = new JButton("Exit");
    private JButton goalButton = new JButton();
    private JButton taskButton = new JButton();
    private JButton createAccountButton = new JButton();

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
        // 页面 window
        JFrame window = new JFrame(); // 页面窗口
        final int window_width = 1260;
        final int window_height = 780;
        window.setBounds(0, 0, window_width, window_height);
        window.setResizable(false);
        window.setLayout(null);
        window.setTitle("JoyBank - Child Home Page");
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(0xf8f6ea));
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, window_width, window_height);
        window.add(backgroundPanel);

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
        Double currentAmount = 25.00; // 当前进度
        Double goal = 500.00; // 目标
        String goalText = String.format("%.2f/%.2f", currentAmount, goal);
        JLabel goalButtonLabel = new JLabel(goalText);
        goalButtonLabel.setBounds(42, 20, 220, 109);
        goalButtonLabel.setHorizontalAlignment(SwingConstants.CENTER);
        goalButton.setLayout(null);
        goalButton.add(goalButtonLabel);
        backgroundPanel.add(goalButton);

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
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        scrollPanel.setBackground(new Color(0xf8f6ea));
        JScrollPane scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setBounds(369, 280, 550, 400); // 调整位置和大小
        scrollPane.setBackground(new Color(0xf8f6ea));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        backgroundPanel.add(scrollPane);

        // 向滚动区域中添加多个显示的块
        // 存钱罐
        PiggyBankLabel piggyBank = new PiggyBankLabel("001", 24.36);
        scrollPanel.add(piggyBank);
        scrollPanel.add(Box.createVerticalStrut(10));
        // 活期账户1
        CurrentAccountLabel currentAccount1 = new CurrentAccountLabel("002", 20.00);
        scrollPanel.add(currentAccount1);
        scrollPanel.add(Box.createVerticalStrut(10));
        // 定期账户1
        SavingAccountLabel savingAccount1 = new SavingAccountLabel("003", 100.00);
        scrollPanel.add(savingAccount1);
        scrollPanel.add(Box.createVerticalStrut(10));
        // 活期账户2
        CurrentAccountLabel currentAccount2 = new CurrentAccountLabel("004", 16.88);
        scrollPanel.add(currentAccount2);
        scrollPanel.add(Box.createVerticalStrut(10));
        // 定期账户2
        SavingAccountLabel savingAccount2 = new SavingAccountLabel("005", 150.00);
        scrollPanel.add(savingAccount2);
        scrollPanel.add(Box.createVerticalStrut(10));

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
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
            enterButton = new JButton("Enter");
            enterButton.setBounds(425, 55, 80, 40);
            this.add(enterButton);
        }
    }

    public static void main(String[] args) {
        Page03_ChildHome page03ChildHome = new Page03_ChildHome();
    }
}
