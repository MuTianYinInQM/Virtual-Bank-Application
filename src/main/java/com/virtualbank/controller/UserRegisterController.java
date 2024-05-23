package com.virtualbank.controller;

import com.virtualbank.model.UIStack;
import com.virtualbank.interfaces.Page;
import com.virtualbank.service.UserService;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.virtualbank.ui.Page01_Login;
import com.virtualbank.ui.Page02_Register;

/**
 * Controller for managing user registration interactions.
 */
public class UserRegisterController implements Page {
    private Page02_Register page;
    private UserService userService;
    private UIStack uiStack;
    private Page01_Login loginPage; // Reference to the login page

    /**
     * Constructs a UserRegisterController with the specified register page, user service, UI stack, and login page.
     *
     * @param page the register page UI component
     * @param userService the service for managing user data
     * @param uiStack the UI stack for managing page transitions
     * @param loginPage the login page UI component
     */
    public UserRegisterController(Page02_Register page, UserService userService, UIStack uiStack, Page01_Login loginPage) {
        this.page = page;
        this.userService = userService;
        this.uiStack = uiStack;
        this.loginPage = loginPage;
        attachActionListeners();
    }

    /**
     * Attaches action listeners to the confirm and exit buttons.
     */
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

    /**
     * Performs the registration process by validating input and registering the user.
     */
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

    /**
     * Closes the registration page by popping it from the UI stack.
     */
    private void closeRegisterPage() {
        uiStack.pop();
    }

    /**
     * Toggles the visibility of the registration page.
     */
    @Override
    public void toggleVisibility() {
        this.page.setVisible(!this.page.isVisible());
    }

    /**
     * Sets the visibility of the registration page.
     *
     * @param visibility the new visibility status
     */
    @Override
    public void setVisibility(boolean visibility) {
        this.page.setVisible(visibility);
    }

    /**
     * Returns the visibility status of the registration page.
     *
     * @return true if the registration page is visible, false otherwise
     */
    @Override
    public boolean getVisibility() {
        return this.page.isVisible();
    }

    /**
     * Disposes of the registration page resources.
     */
    @Override
    public void dispose() {
        this.page.dispose();
    }
}
