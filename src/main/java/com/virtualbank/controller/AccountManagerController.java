package com.virtualbank.controller;

import com.virtualbank.interfaces.ToggleVisibility;
import com.virtualbank.model.AccountManager;
import com.virtualbank.ui.Page03_ChildHome;
import com.virtualbank.ui.UIStack;
import com.virtualbank.ui.Window1_ChooseAccountType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.Period;
import java.util.UUID;

public class AccountManagerController implements PropertyChangeListener, ToggleVisibility {
    private Page03_ChildHome page;
    private AccountManager accountManager;
    private UIStack uiStack;

    public AccountManagerController(Page03_ChildHome page, AccountManager accountManager, UIStack uiStack) {
        this.page = page;
        this.accountManager = accountManager;
        this.accountManager.addPropertyChangeListener(this); // 监听AccountManager的变化
        this.uiStack = uiStack;
        initPage();
    }

    private void initPage() {
        // 设置按钮监听器
        // 退回上一级目录
        page.getExitButton().addActionListener(e -> uiStack.pop());

        // 创建下一个目录
        page.getCreateAccountButton().addActionListener(
                e -> {
                    Window1_ChooseAccountType chooseAccountTypeWindow = new Window1_ChooseAccountType();
                    AccountTypeChooseController accountTypeChooseController =
                            new AccountTypeChooseController(chooseAccountTypeWindow, accountManager, this.uiStack);
                    uiStack.pushWindows(accountTypeChooseController);
                }
        );

        updatePage();
    }

    private void updatePage() {
        page.updateAccounts(accountManager);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//        // 当AccountManager的属性发生变化时，更新页面上的信息
//        if ("balance".equals(evt.getPropertyName())) {
//            // 如果是余额发生变化，则更新账户信息
//            updatePage();
//        } else if ("current_accounts".equals(evt.getPropertyName()) || "saving_accounts".equals(evt.getPropertyName())) {
//            // 如果有新的当前账户或储蓄账户被添加，则更新页面上的账户列表
//            updatePage();
//        }

        // TODO 至少现在所有的处理都是一样的 重新渲染一遍
        updatePage();
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

    public static void main(String[] args) {
        UIStack uis = new UIStack();

        Page03_ChildHome page = new Page03_ChildHome();

        AccountManager accountManager = new AccountManager();
        accountManager.prize(accountManager.getPiggyUuid(), 100, "Initial money added to Piggy Bank");
        UUID currentAccountId = accountManager.addCurrentAccount("CurrentAccount",
                0.01, 24);
        UUID savingAccountId = accountManager.addSavingAccount("SavingAccount",
                50, 0.05, 24, Period.ofYears(1));
        accountManager.save(currentAccountId, 1000, "Initial deposit to Current Account");

        AccountManagerController controller = new AccountManagerController(page, accountManager, uis);
        uis.pushPage(controller);
        controller.updatePage();
    }
}
