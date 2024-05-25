package com.virtualbank.controller;

import com.virtualbank.interfaces.Page;
import com.virtualbank.model.AccountManager;
import com.virtualbank.model.account.Account;
import com.virtualbank.model.account.CurrentAccount;
import com.virtualbank.model.account.PiggyBank;
import com.virtualbank.model.account.SavingAccount;
import com.virtualbank.repository.AccountManagerSerializer;
import com.virtualbank.service.TaskService;
import com.virtualbank.ui.*;
import com.virtualbank.model.UIStack;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.Period;
import java.util.UUID;

/**
 * Controller class for managing the Child Home page and related actions.
 */
public class AccountManagerController implements PropertyChangeListener, Page {
    private Page03_ChildHome page;
    private AccountManager accountManager;
    private UIStack uiStack;
    private String username;

    /**
     * Constructs an AccountManagerController with the specified Child Home page, account manager, UI stack, and username.
     *
     * @param page           The Child Home page associated with this controller.
     * @param accountManager The AccountManager instance managing the accounts.
     * @param uiStack        The UIStack instance managing the UI navigation.
     * @param username       The username associated with the account manager.
     */
    public AccountManagerController(Page03_ChildHome page, AccountManager accountManager, UIStack uiStack, String username) {
        this.page = page;
        this.accountManager = accountManager;
        this.accountManager.addPropertyChangeListener(this); // 监听AccountManager的变化
        this.uiStack = uiStack;
        this.username = username;
        initPage();
    }

    /**
     * Updates the page based on changes in the account manager.
     */
    private void updatePage() {
        updateAccounts(accountManager);
    }

    /**
     * Initializes the page and sets up event listeners for UI components.
     */
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
                    uiStack.pushWindow(accountTypeChooseController);
                }
        );

        page.getGoalButton().addActionListener(
                e -> {
                    Page06_Goal page06Goal = new Page06_Goal(accountManager, uiStack);
                    // 在函数里面能干的全干了 不需要再 push stack 了
                }
        );

        page.getTaskButton().addActionListener(
                e -> {
                    Page05_ChildTask page05ChildTask = new Page05_ChildTask();
                    TaskService taskService = new TaskService();

                    ChildTaskController childTaskController = new ChildTaskController(taskService, page05ChildTask, accountManager, uiStack);
                    uiStack.pushPage(childTaskController);
                }
        );
        //点击 enter 进入一个Account的界面
        // 在刷新页面的时候一并添加了Account的Listener
        updatePage();
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
        // 然后只要Account有变动，就重新序列化 保证再次载入的时候 Account的一致性
        AccountManagerSerializer.serializeAccountManager(accountManager, username);
    }

    // 动态更新账户信息的方法

    /**
     * Updates the displayed account information on the page.
     *
     * @param accountManager The AccountManager instance containing the accounts.
     */
    public void updateAccounts(AccountManager accountManager) {
        page.totalBalanceUpdate(accountManager.getTotalBalance());

        //重新绘制所有的银行卡
        page.getScrollPanel().removeAll(); // 清空当前显示的所有账户信息

        for (Account account : accountManager.getAccounts().values()) {
            Page03_ChildHome.AccountLabel label;
            if (account instanceof CurrentAccount) {
                label = new Page03_ChildHome.CurrentAccountLabel(
                        account.getUuid().toString(),
                        account.getBalance()
                );
            } else if (account instanceof PiggyBank) {
                label = new Page03_ChildHome.PiggyBankLabel(
                        account.getUuid().toString(),
                        account.getBalance()
                );
            } else if (account instanceof SavingAccount) {
                label = new Page03_ChildHome.SavingAccountLabel(
                        account.getUuid().toString(),
                        account.getBalance()
                );
            } else {
                // 处理未知类型的账户
                continue;
            }
            label.getEnterButton().addActionListener(e -> openAccountDetails(account));

            page.getScrollPanel().add(label);
            page.getScrollPanel().add(Box.createVerticalStrut(10)); // 添加间隔
        }

        page.getScrollPanel().revalidate();
        page.getScrollPanel().repaint();
    }

    /**
     * Opens the details page for a specific account.
     *
     * @param account The account for which details are to be displayed.
     */
    private void openAccountDetails(Account account) {
        Page07_Account page07Account = new Page07_Account(account);
        AccountController accountController = new AccountController(page07Account, accountManager, uiStack);
        uiStack.pushPage(accountController);
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

    public void updateAllInterests() {
        accountManager.updateAllInterests();
        AccountManagerSerializer.serializeAccountManager(accountManager, username);
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

        AccountManagerController controller = new AccountManagerController(page, accountManager, uis, "test");
        uis.pushPage(controller);
        controller.updatePage();
    }
}
