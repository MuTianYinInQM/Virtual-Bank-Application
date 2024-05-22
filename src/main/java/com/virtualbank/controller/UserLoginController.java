package com.virtualbank.controller;

import com.virtualbank.repository.AccountManagerSerializer;
import com.virtualbank.service.UserService;
import com.virtualbank.service.TaskService;
import com.virtualbank.model.UIStack;
import com.virtualbank.interfaces.Page;
import com.virtualbank.ui.Page01_Login;
import com.virtualbank.ui.Page02_Register;
import com.virtualbank.ui.Page04_ParentHome;
import com.virtualbank.ui.Page03_ChildHome;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UserLoginController implements Page {
    private Page01_Login page;
    private UserService userService;
    private TaskService taskService;
    private UIStack uiStack;

    public UserLoginController(Page01_Login loginPage, UserService userService, TaskService taskService, UIStack uiStack) {
        this.page = loginPage;
        this.userService = userService;
        this.taskService = taskService;
        this.uiStack = uiStack;
        initializeUserData();
        attachLoginListener();
        attachRegisterListener();
    }

    private void initializeUserData() {
        try {
            userService.loadUserData();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(page, "Failed to load user data", "Initialization Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void attachLoginListener() {
        page.getConfirmButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    performLogin();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void attachRegisterListener() {
        page.getRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegisterPage();
            }
        });
    }

    private void performLogin() throws IOException, ClassNotFoundException {
        String username = page.getUsername_textField().getText();
        char[] password = page.getPassword_textField().getPassword();
        String result = userService.loginUser(username, new String(password));

        java.util.Arrays.fill(password, '0');

        switch (result) {
            case "Parent Page":
                JOptionPane.showMessageDialog(page, "Login Successful - Parent Page");
                Page04_ParentHome parentPage = new Page04_ParentHome();
                ParentHomeController parentController = new ParentHomeController(taskService, parentPage);
                parentPage.setVisible(true);
                page.setVisible(false);
                break;
            case "Child Page":
                JOptionPane.showMessageDialog(page, "Login Successful - Child Page");
                Page03_ChildHome childPage = new Page03_ChildHome();
                AccountManagerController accountManagerController =
                        new AccountManagerController(
                                childPage,
                                AccountManagerSerializer.deserializeAccountManager(username),
                                uiStack,
                                username
                        );
                uiStack.pushPage(accountManagerController);
                break;
            case "Password Incorrect":
                JOptionPane.showMessageDialog(page, "Password Incorrect", "Login Error", JOptionPane.ERROR_MESSAGE);
                break;
            case "Username does not exist":
                JOptionPane.showMessageDialog(page, "Username does not exist", "Login Error", JOptionPane.ERROR_MESSAGE);
                break;
            case "Failed to load account manager":
                JOptionPane.showMessageDialog(page, "Failed to load account manager", "Login Error", JOptionPane.ERROR_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(page, "Login Failed", "Login Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    private void openRegisterPage() {
        Page02_Register registerPage = new Page02_Register();
        UserRegisterController registerController = new UserRegisterController(registerPage, userService, uiStack, this.page);
        uiStack.pushPage(registerController);
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
