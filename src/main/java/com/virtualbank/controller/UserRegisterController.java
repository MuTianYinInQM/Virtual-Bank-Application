package com.virtualbank.controller;

import com.virtualbank.service.UserService;
import com.virtualbank.ui.Page01_Login;
import com.virtualbank.ui.Page02_Register;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserRegisterController {
    private Page02_Register registerPage;
    private UserService userService;

    private Page01_Login loginPage; // 添加对登录页面的引用



    public UserRegisterController(Page02_Register registerPage, UserService userService, Page01_Login loginPage) {
        this.registerPage = registerPage;
        this.userService = userService;
        this.loginPage = loginPage; // 保存登录页面的引用
        attachActionListeners();
    }


    private void attachActionListeners() {
        registerPage.getConfirmButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRegistration();
            }
        });
        registerPage.getExitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeRegisterPage();
            }
        });
    }

    private void performRegistration() {
        String username = registerPage.getUsername_textField().getText().trim();
        char[] password = registerPage.getPassword_textField().getPassword();
        boolean isParent = registerPage.getParentButton().isSelected();

        if (username.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(registerPage, "Username and password cannot be empty.", "Registration Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean result = userService.registerUser(username, new String(password), isParent);
        java.util.Arrays.fill(password, '0'); // 清除密码数组，提高安全性

        if (result) {
            JOptionPane.showMessageDialog(registerPage, "Registration Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            closeRegisterPage(); // 关闭注册窗口
            showLoginPage();    // 显示登录窗口
        } else {
            JOptionPane.showMessageDialog(registerPage, "Registration Failed: Username may already exist", "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void showLoginPage() {
        registerPage.dispose();  // 关闭注册窗口
        if (loginPage != null) {
            loginPage.setVisible(true); // 使登录页面可见
        } else {
            loginPage = new Page01_Login(); // 创建新的登录页面
            loginPage.setVisible(true);
        }
    }


    private void closeRegisterPage() {
        registerPage.dispose();
    }
}
