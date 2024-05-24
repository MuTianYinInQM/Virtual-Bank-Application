package com.virtualbank.controller;

import com.virtualbank.model.AccountManager;
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

/**
 * Controller for managing user login interactions.
 */
public class UserLoginController implements Page {
    private Page01_Login page;
    private UserService userService;
    private TaskService taskService;
    private UIStack uiStack;

    /**
     * Constructs a UserLoginController with the specified login page, user service, task service, and UI stack.
     *
     * @param loginPage   the login page UI component
     * @param userService the service for managing user data
     * @param taskService the service for managing tasks
     * @param uiStack     the UI stack for managing page transitions
     */
    public UserLoginController(Page01_Login loginPage, UserService userService, TaskService taskService, UIStack uiStack) {
        this.page = loginPage;
        this.userService = userService;
        this.taskService = taskService;
        this.uiStack = uiStack;
        initializeUserData();
        attachLoginListener();
        attachRegisterListener();
    }

    /**
     * Initializes user data by loading it from the storage.
     */
    private void initializeUserData() {
        try {
            userService.loadUserData();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(page, "Failed to load user data", "Initialization Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Attaches the login button listener to handle login actions.
     */
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

    /**
     * Attaches the register button listener to handle registration actions.
     */
    private void attachRegisterListener() {
        page.getRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegisterPage();
            }
        });
    }

    /**
     * Performs the login operation by validating user credentials and navigating to the appropriate page.
     *
     * @throws IOException            if an I/O error occurs
     * @throws ClassNotFoundException if a class cannot be found during the process
     */
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
                AccountManager accountManager = AccountManagerSerializer.deserializeAccountManager(username);
                accountManager.updateAllInterests();
                AccountManagerController accountManagerController =
                        new AccountManagerController(
                                childPage,
                                accountManager,
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

    /**
     * Opens the registration page.
     */
    private void openRegisterPage() {
        Page02_Register registerPage = new Page02_Register();
        UserRegisterController registerController = new UserRegisterController(registerPage, userService, uiStack, this.page);
        uiStack.pushPage(registerController);
    }

    /**
     * Toggles the visibility of the login page.
     */
    @Override
    public void toggleVisibility() {
        this.page.setVisible(!this.page.isVisible());
    }

    /**
     * Sets the visibility of the login page.
     *
     * @param visibility the new visibility status
     */
    @Override
    public void setVisibility(boolean visibility) {
        this.page.setVisible(visibility);
    }

    /**
     * Returns the visibility status of the login page.
     *
     * @return true if the login page is visible, false otherwise
     */
    @Override
    public boolean getVisibility() {
        return this.page.isVisible();
    }

    /**
     * Disposes of the login page resources.
     */
    @Override
    public void dispose() {
        this.page.dispose();
    }
}
