package com.virtualbank.controller;

import com.virtualbank.model.UIStack;
import com.virtualbank.interfaces.Page;
import com.virtualbank.service.UserService;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.virtualbank.ui.Page01_Login;
import com.virtualbank.ui.Page02_Register;

public class UserRegisterController implements Page {
    private Page02_Register page;
    private UserService userService;
    private UIStack uiStack;
    private Page01_Login loginPage;  // 添加对登录页面的引用

    public UserRegisterController(Page02_Register page, UserService userService, UIStack uiStack, Page01_Login loginPage) {
        this.page = page;
        this.userService = userService;
        this.uiStack = uiStack;
        this.loginPage = loginPage;  // 初始化登录页面引用
        attachActionListeners();
    }

    private void attachActionListeners() {
        page.getConfirmButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRegistration();
            }
        });
        page.getExitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeRegisterPage();
            }
        });
    }

    private void performRegistration() {
        String username = page.getUsername_textField().getText().trim();
        char[] password = page.getPassword_textField().getPassword();
        boolean isParent = page.getParentButton().isSelected();

        if (!isParent && !page.getChildButton().isSelected()) {
            JOptionPane.showMessageDialog(page, "Please select a role (Parent or Child).", "Registration Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (username.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(page, "Username and password cannot be empty.", "Registration Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean result = userService.registerUser(username, new String(password), isParent);
        java.util.Arrays.fill(password, '0');

        if (result) {
            JOptionPane.showMessageDialog(page, "Registration Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            uiStack.pop();
        } else {
            JOptionPane.showMessageDialog(page, "Registration Failed: Username may already exist", "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void closeRegisterPage() {
        uiStack.pop(); // 使用 UI 栈来管理页面
    }

    @Override
    public void toggleVisibility() {
        this.page.setVisible(!this.page.isVisible());
    }

    @Override
    public void setVisibility(boolean visibility) {
        this.page.setVisible(visibility);
    }

    @Override
    public boolean getVisibility() {
        return this.page.isVisible();
    }

    @Override
    public void dispose() {
        this.page.dispose();
    }
}
