package com.virtualbank.controller;

import com.virtualbank.service.UserService;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.virtualbank.ui.Page01_Login;
import com.virtualbank.ui.Page02_Register;

public class UserRegisterController {
    private Page02_Register registerPage;
    private UserService userService;
    private Page01_Login loginPage;  // 添加对登录页面的引用

    public UserRegisterController(Page02_Register registerPage, UserService userService, Page01_Login loginPage) {
        this.registerPage = registerPage;
        this.userService = userService;
        this.loginPage = loginPage;  // 初始化登录页面引用
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

        if (!isParent && !registerPage.getChildButton().isSelected()) {
            JOptionPane.showMessageDialog(registerPage, "Please select a role (Parent or Child).", "Registration Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (username.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(registerPage, "Username and password cannot be empty.", "Registration Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean result = userService.registerUser(username, new String(password), isParent);
        java.util.Arrays.fill(password, '0');

        if (result) {
            JOptionPane.showMessageDialog(registerPage, "Registration Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            registerPage.setVisible(false); // 隐藏注册页面
            loginPage.setVisible(true); // 显示登录页面
        } else {
            JOptionPane.showMessageDialog(registerPage, "Registration Failed: Username may already exist", "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void closeRegisterPage() {
        registerPage.dispose(); // 也可以考虑隐藏而非关闭
    }
}
