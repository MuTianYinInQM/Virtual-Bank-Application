package com.virtualbank.controller;

import com.virtualbank.service.UserService;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.virtualbank.ui.Page01_Login;
import com.virtualbank.ui.Page02_Register;

public class UserLoginController {
    private Page01_Login loginPage;
    private UserService userService;

    public UserLoginController(Page01_Login loginPage, UserService userService) {
        this.loginPage = loginPage;
        this.userService = userService;
        attachLoginListener();
        attachRegisterListener();
    }

    private void attachLoginListener() {
        loginPage.getConfirmButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
    }

    private void attachRegisterListener() {
        loginPage.getRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegisterPage();
            }
        });
    }
    private void performLogin() {
        String username = loginPage.getUsername_textField().getText();
        char[] password = loginPage.getPassword_textField().getPassword();
        String result = userService.loginUser(username, new String(password));

        // 清除密码数组，提高安全性
        java.util.Arrays.fill(password, '0');

        switch (result) {
            case "Parent Page":
                JOptionPane.showMessageDialog(loginPage, "Login Successful - Parent Page");
                break;
            case "Child Page":
                JOptionPane.showMessageDialog(loginPage, "Login Successful - Child Page");
                break;
            case "Password Incorrect":
                JOptionPane.showMessageDialog(loginPage, "Password Incorrect", "Login Error", JOptionPane.ERROR_MESSAGE);
                break;
            case "Username does not exist":
                JOptionPane.showMessageDialog(loginPage, "Username does not exist", "Login Error", JOptionPane.ERROR_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(loginPage, "Login Failed", "Login Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    private void openRegisterPage() {
        // 创建注册页面实例
        Page02_Register registerPage = new Page02_Register();
        // 创建并初始化注册控制器实例，传递注册页面和用户服务
        new UserRegisterController(registerPage, userService, loginPage); // 确保传递loginPage

        // 显示注册页面
        registerPage.setVisible(true);

        // 使登录页面不可见（可选）
        loginPage.setVisible(false);
    }
}
