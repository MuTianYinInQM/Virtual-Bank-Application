package com.virtualbank.controller;

import com.virtualbank.model.AccountManager;
import com.virtualbank.ui.Page03_ChildHome;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.Period;
import java.util.UUID;

public class AccountManagerController implements PropertyChangeListener {
    private Page03_ChildHome page;
    private AccountManager accountManager;

    public AccountManagerController(Page03_ChildHome page, AccountManager accountManager) {
        this.page = page;
        this.accountManager = accountManager;
        this.accountManager.addPropertyChangeListener(this); // 监听AccountManager的变化
        initPage();
    }

    private void initPage() {
        // 设置按钮监听器
        // TODO exit 应该最后是退回上一级目录里面 即 栈的pop
        page.getExitButton().addActionListener(e -> System.exit(0));
        // TODO 其他监听器 和 每一个account跳转的监听器

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

    public static void main(String[] args) {
        Page03_ChildHome page = new Page03_ChildHome();

        AccountManager accountManager = new AccountManager();
        accountManager.prize(accountManager.getPiggyUuid(), 100, "Initial money added to Piggy Bank");
        UUID currentAccountId = accountManager.addCurrentAccount("CurrentAccount",
                0.01, 24);
        UUID savingAccountId = accountManager.addSavingAccount("SavingAccount",
                50, 0.05, 24, Period.ofYears(1));
        accountManager.save(currentAccountId, 1000, "Initial deposit to Current Account");

        AccountManagerController controller = new AccountManagerController(page, accountManager);
        controller.updatePage();
    }
}
