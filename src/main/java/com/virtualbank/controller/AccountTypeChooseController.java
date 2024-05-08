package com.virtualbank.controller;

import com.virtualbank.interfaces.Page;
import com.virtualbank.model.AccountManager;
import com.virtualbank.ui.UIStack;
import com.virtualbank.ui.Window1_ChooseAccountType;
import com.virtualbank.parameters.Interest;
import com.virtualbank.ui.Window2_CreateSavingAccount;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountTypeChooseController implements Page {

    private Window1_ChooseAccountType page;
    // 虽然这个在这里没用，但是AccountManager要传递给下一个页面使用，这里仅仅是过了一遍手
    private AccountManager accountManager;
    private UIStack uiStack;

    public AccountTypeChooseController(Window1_ChooseAccountType view, AccountManager accountManager, UIStack uiStack) {
        this.page = view;
        this.accountManager = accountManager;
        this.uiStack = uiStack;
        initController();
    }

    private void initController() {
        page.getConfirmButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox currentAccountButton = page.getCurrentAccountButton();
                JCheckBox savingAccountButton = page.getSavingAccountButton();

                if (currentAccountButton.isSelected()) {
                    // 直接添加一个空的活期账户 不需要再进一步跳转了
                    accountManager.addCurrentAccount("no name",
                            Interest.currentInterest, Interest.timeLapseCoefficient);
                    uiStack.pop();

                } else if (savingAccountButton.isSelected()) {
                    Window2_CreateSavingAccount window2CreateSavingAccount = new Window2_CreateSavingAccount();
                    CreateSavingAccountController createSavingAccountController =
                            new CreateSavingAccountController(window2CreateSavingAccount, accountManager, uiStack);
                    uiStack.swapWindows(createSavingAccountController);
                } else {
                    // 如果没有选择任何账户类型，则不执行任何操作
                    JOptionPane.showMessageDialog(page.getConfirmButton().getTopLevelAncestor(),
                            "Please select an account type.");
                }
            }
        });
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
