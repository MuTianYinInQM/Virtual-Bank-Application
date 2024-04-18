import javax.swing.*;
import java.awt.*;

public class Window2_CreateSavingAccount {

    private JTextField money_textField; // 金额输入框
    private JComboBox<String> comboBox; // 定期利率期限选项
    private JButton confirmButton; // 确认按钮

    public JTextField getMoney_textField() {
        return money_textField;
    }

    public JComboBox<String> getComboBox() {
        return comboBox;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public Window2_CreateSavingAccount() {
        // 页面 window
        JFrame window = new JFrame(); // 页面窗口
        final int window_width = 800;
        final int window_height = 504;
        window.setBounds(0, 0, window_width, window_height);
        window.setResizable(false);
        window.setLayout(null);
        window.setTitle("JoyBank - Creating a New Saving Account");
        ImageIcon loginBackgroundImageIcon = new ImageIcon("images/CreateSavingAccount.png"); // 添加背景图
        JLabel backgroundLabel = new JLabel(loginBackgroundImageIcon);
        backgroundLabel.setBounds(0, 0, window_width, window_height);
        window.getContentPane().add(backgroundLabel);

        // 金额输入框
        money_textField = new JTextField();
        money_textField.setBounds(380, 120, 200, 50);
        Font font = new Font(money_textField.getFont().getName(), Font.PLAIN, 20);
        money_textField.setFont(font);
        backgroundLabel.add(money_textField);

        // 选择定期时间和利率的下拉选择框
        String[] options = {"2%/day for 7 days", "选项2", "选项3", "选项4"};
        comboBox = new JComboBox<>(options);
        comboBox.setBounds(380, 190, 200, 50);
        backgroundLabel.add(comboBox);

        // 确认按钮
        confirmButton = new JButton();
        confirmButton.setBounds(290, 410, 200, 55);
        ImageIcon confirmButtonIcon = new ImageIcon("images/ConfirmButton.png");
        confirmButton.setIcon(confirmButtonIcon);
        backgroundLabel.add(confirmButton);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        Window2_CreateSavingAccount window2CreateSavingAccount = new Window2_CreateSavingAccount();
    }
}
