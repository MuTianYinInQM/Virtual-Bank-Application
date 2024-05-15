package com.virtualbank.controller;

import com.virtualbank.interfaces.Page;
import com.virtualbank.model.AccountManager;
import com.virtualbank.model.AccountOperationType;
import com.virtualbank.model.account.Account;
import com.virtualbank.model.account.CurrentAccount;
import com.virtualbank.model.account.PiggyBank;
import com.virtualbank.model.account.SavingAccount;
import com.virtualbank.ui.Page07_Account;
import com.virtualbank.model.UIStack;
import com.virtualbank.ui.Page08_History;
import com.virtualbank.ui.Window07_AccountOperation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AccountController implements PropertyChangeListener, Page {

    private Page07_Account page;
    // 虽然这个在这里没用，但是AccountManager要传递给下一个页面使用，这里仅仅是过了一遍手
    private AccountManager accountManager;
    private UIStack uiStack;

    public AccountController(Page07_Account view, AccountManager accountManager, UIStack uiStack) {
        this.page = view;
        this.accountManager = accountManager;
        this.uiStack = uiStack;
        this.accountManager.addPropertyChangeListener(this);
        initController();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        page.updatePage();
    }


    private void initController() {
        Account account = page.getCurrentAccount();
        page.getExitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiStack.pop();
            }
        });
        page.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // 尝试移除账户
                    accountManager.removeAccount(page.getCurrentAccount().getUuid());
                    // 如果成功，移除页面
                    uiStack.pop();
                } catch (IllegalArgumentException ex) {
                    // 异常处理：显示错误信息弹窗
                    JOptionPane.showMessageDialog(page, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        page.getHistoryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Page08_History window = new Page08_History(account.getUuid());
                HistoryController controller =
                        new HistoryController(window, uiStack);
                uiStack.pushPage(controller);
            }
        });
        if (account instanceof PiggyBank) {
            page.getConsumeButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Window07_AccountOperation window = new Window07_AccountOperation(account, AccountOperationType.CONSUME);
                    AccountOperationController controller =
                            new AccountOperationController(window, accountManager, uiStack);
                    uiStack.pushWindow(controller);
                }
            });
            page.getSaveButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Window07_AccountOperation window = new Window07_AccountOperation(account, AccountOperationType.SAVE);
                    AccountOperationController controller =
                            new AccountOperationController(window, accountManager, uiStack);
                    uiStack.pushWindow(controller);
                }
            });
            page.getTransferButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Window07_AccountOperation window = new Window07_AccountOperation(account, AccountOperationType.TRANSFER);
                    AccountOperationController controller =
                            new AccountOperationController(window, accountManager, uiStack);
                    uiStack.pushWindow(controller);
                }
            });
        } else if (account instanceof CurrentAccount) {
            page.getTransferButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Window07_AccountOperation window = new Window07_AccountOperation(account, AccountOperationType.TRANSFER);
                    AccountOperationController controller =
                            new AccountOperationController(window, accountManager, uiStack);
                    uiStack.pushWindow(controller);
                }
            });
        } else if (account instanceof SavingAccount) {
            // 什么也不干
        }
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
