package com.virtualbank.controller;

import com.virtualbank.interfaces.Page;
import com.virtualbank.model.AccountManager;
import com.virtualbank.model.AccountOperationType;
import com.virtualbank.model.Pair;
import com.virtualbank.model.UIStack;
import com.virtualbank.model.account.Account;
import com.virtualbank.parameters.Interest;
import com.virtualbank.ui.Window07_AccountOperation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Period;
import java.util.UUID;

/**
 * Controller class for handling account operations.
 */
public class AccountOperationController implements Page {
    private Window07_AccountOperation page;
    // 虽然这个在这里没用，但是AccountManager要传递给下一个页面使用，这里仅仅是过了一遍手
    private AccountManager accountManager;
    private UIStack uiStack;

    /**
     * Constructs an AccountOperationController with the specified view, account manager, and UI stack.
     *
     * @param view           The Window07_AccountOperation view associated with this controller.
     * @param accountManager The AccountManager instance managing the accounts.
     * @param uiStack        The UIStack instance managing the UI navigation.
     */
    public AccountOperationController(Window07_AccountOperation view, AccountManager accountManager, UIStack uiStack) {
        this.page = view;
        this.accountManager = accountManager;
        this.uiStack = uiStack;
        initController();
    }

    /**
     * Initializes the controller by setting up event listeners for UI components.
     */
    private void initController() {
        page.getConfirmButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取参数 同时添加新的定期银行卡 钱的来源是存钱罐
                try {
                    double amount = page.getAmount();
                    String description = page.getDescription();
                    AccountOperationType operationType = page.getOperationType();
                    Account account = page.getAccount(); // ensure id is not null

                    if (AccountOperationType.SAVE == operationType) {
                        accountManager.save(account.getUuid(), amount, description);
                    } else if (AccountOperationType.TRANSFER == operationType) {
                        if (amount >= 0) {
                            accountManager.transfer(account.getUuid(), accountManager.getPiggyUuid(), amount, description);
                        } else {
                            // amount < 0
                            // transfer money from piggybank to current account
                            accountManager.transfer(accountManager.getPiggyUuid(), account.getUuid(), -amount, description);
                        }
                    } else if (AccountOperationType.CONSUME == operationType) {
                        accountManager.consume(account.getUuid(), amount, description);
                    }
                    uiStack.pop();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(page, exception.getMessage());
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
