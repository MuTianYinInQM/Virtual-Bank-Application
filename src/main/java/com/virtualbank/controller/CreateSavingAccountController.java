package com.virtualbank.controller;

import com.virtualbank.interfaces.Page;
import com.virtualbank.model.AccountManager;
import com.virtualbank.model.Pair;
import com.virtualbank.parameters.Interest;
import com.virtualbank.ui.UIStack;
import com.virtualbank.ui.Window1_ChooseAccountType;
import com.virtualbank.ui.Window2_CreateSavingAccount;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Period;

public class CreateSavingAccountController implements Page {
    private Window2_CreateSavingAccount page;
    // 虽然这个在这里没用，但是AccountManager要传递给下一个页面使用，这里仅仅是过了一遍手
    private AccountManager accountManager;
    private UIStack uiStack;


    public CreateSavingAccountController(Window2_CreateSavingAccount view, AccountManager accountManager, UIStack uiStack) {
        this.page = view;
        this.accountManager = accountManager;
        this.uiStack = uiStack;
        initController();
    }

    private void initController() {
        page.getConfirmButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取参数 同时添加新的定期银行卡 钱的来源是存钱罐
                double amount = page.getEnteredAmount();
                Pair<Double, Period> selectedInterestOption = page.getSelectedInterestOption();
                if (selectedInterestOption != null) {
                    accountManager.addSavingAccount("no name",
                            amount, selectedInterestOption.getKey(),
                            Interest.timeLapseCoefficient, selectedInterestOption.getValue()
                    );
                    JOptionPane.showMessageDialog(page, "Saving account created successfully.");
                    uiStack.pop();
                } else {
                    // 金额不合法或未选择期限
                    JOptionPane.showMessageDialog(page.getConfirmButton().getTopLevelAncestor(),
                            "Please enter a valid amount and select a term.");
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
