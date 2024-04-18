import java.awt.*;
import javax.swing.*;

public class Page01_Login {

    private JTextField username_textField;
    private JTextField password_textField;
    private JButton confirmButton;
    private JButton registerButton;

    public Page01_Login() {
        // 页面窗口
        JFrame window = new JFrame();
        final int window_width = 1260;
        final int window_height = 841;
        window.setBounds(0, 0, window_width, window_height);
        window.setTitle("JoyBank - Login Page");
        window.setResizable(false);
        window.setLayout(null);
        ImageIcon loginBackgroundImageIcon = new ImageIcon("resources/LoginBackground.png"); // 添加背景图
        JLabel backgroundLabel = new JLabel(loginBackgroundImageIcon);
        backgroundLabel.setBounds(0, 0, window_width, window_height);
        window.getContentPane().add(backgroundLabel);

        // 输入框
        username_textField = new JTextField();
        username_textField.setBounds(450, 255, 360, 50);
        Font font = new Font(username_textField.getFont().getName(), Font.PLAIN, 20); // 创建一个新字体对象，设置大小为20
        username_textField.setFont(font);
        backgroundLabel.add(username_textField);

        password_textField = new JTextField();
        password_textField.setBounds(450, 335, 360, 50);
        password_textField.setFont(font);
        backgroundLabel.add(password_textField);

        // 确认按钮
        confirmButton = new JButton();
        confirmButton.setBounds(530, 410, 200, 55);
        ImageIcon confirmButtonIcon = new ImageIcon("resources/ConfirmButton.png");
        confirmButton.setIcon(confirmButtonIcon);
        backgroundLabel.add(confirmButton);

        // 注册按钮
        registerButton = new JButton("Register");
        registerButton.setBounds(580, 470, 122, 44);
        ImageIcon registerButtonIcon = new ImageIcon("resources/RegisterButton.png");
        registerButton.setIcon(registerButtonIcon);
        backgroundLabel.add(registerButton);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public JTextField getUsername_textField() {
        return username_textField;
    }

    public JTextField getPassword_textField() {
        return password_textField;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public static void main(String[] args) {
       Page01_Login page01Login =  new Page01_Login();
    }
}
