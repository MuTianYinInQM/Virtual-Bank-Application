import javax.swing.*;
import java.awt.*;

public class Window3_CreateNewTask {

    private JTextField money_textField; // 任务奖励金额输入
    private JTextField taskName_textField; // 任务名称/标题输入
    private JTextField content_textField; // 任务内容输入
    private JComboBox<String> comboBox; // 任务对象/小孩选择
    private JButton confirmButton; // 确认按钮

    public JTextField getMoney_textField() {
        return money_textField;
    }

    public JTextField getTaskName_textField() {
        return taskName_textField;
    }

    public JTextField getContent_textField() {
        return content_textField;
    }

    public JComboBox<String> getComboBox() {
        return comboBox;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public Window3_CreateNewTask() {
        // 页面 window
        JFrame window = new JFrame(); // 页面窗口
        final int window_width = 800;
        final int window_height = 558;
        window.setBounds(0, 0, window_width, window_height);
        window.setResizable(false);
        window.setLayout(null);
        window.setTitle("JoyBank - Creating a New Task");
        ImageIcon loginBackgroundImageIcon = new ImageIcon("images/CreateTaskBackground.png"); // 添加背景图
        JLabel backgroundLabel = new JLabel(loginBackgroundImageIcon);
        backgroundLabel.setBounds(0, 0, window_width, window_height);
        window.getContentPane().add(backgroundLabel);

        // 金额输入框
        money_textField = new JTextField();
        money_textField.setBounds(380, 120, 200, 50);
        Font font = new Font(money_textField.getFont().getName(), Font.PLAIN, 20);
        money_textField.setFont(font);
        backgroundLabel.add(money_textField);

        // 任务名称/标题输入框
        taskName_textField = new JTextField();
        taskName_textField.setBounds(380, 180, 200, 50);
        taskName_textField.setFont(font);
        backgroundLabel.add(taskName_textField);

        // 任务内容输入框
        content_textField = new JTextField();
        content_textField.setBounds(380, 240, 200, 50);
        content_textField.setFont(font);
        backgroundLabel.add(content_textField);

        // 选择任务对象/小孩的下拉选择框
        String[] options = {"Tom", "Lisa", "Sam"};
        comboBox = new JComboBox<>(options);
        comboBox.setBounds(380, 295, 200, 50);
        backgroundLabel.add(comboBox);

        // 确认按钮
        confirmButton = new JButton();
        confirmButton.setBounds(310, 460, 200, 55);
        ImageIcon confirmButtonIcon = new ImageIcon("images/ConfirmButton.png");
        confirmButton.setIcon(confirmButtonIcon);
        backgroundLabel.add(confirmButton);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        Window3_CreateNewTask window3CreateNewTask = new Window3_CreateNewTask();
    }
}
