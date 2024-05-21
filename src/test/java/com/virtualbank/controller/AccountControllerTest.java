package com.virtualbank.controller;

import static org.mockito.Mockito.*;

import com.virtualbank.model.AccountManager;
import com.virtualbank.model.UIStack;
import com.virtualbank.model.account.Account;
import com.virtualbank.model.account.CurrentAccount;
import com.virtualbank.model.account.PiggyBank;
import com.virtualbank.model.account.SavingAccount;
import com.virtualbank.ui.Page07_Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;

import java.awt.event.ActionListener;


class AccountControllerTest {
    private AccountController controller;
    private Page07_Account mockPage;
    private AccountManager mockAccountManager;
    private UIStack mockUiStack;
    private Account mockAccount;
    private JButton mockExitButton, mockDeleteButton, mockHistoryButton, mockConsumeButton, mockSaveButton, mockTransferButton;

    @BeforeEach
    void setUp() {
        mockPage = mock(Page07_Account.class);
        mockAccountManager = mock(AccountManager.class);
        mockUiStack = mock(UIStack.class);
        mockAccount = mock(Account.class);
        mockExitButton = mock(JButton.class);
        mockDeleteButton = mock(JButton.class);
        mockHistoryButton = mock(JButton.class);
        mockConsumeButton = mock(JButton.class);
        mockSaveButton = mock(JButton.class);
        mockTransferButton = mock(JButton.class);

        when(mockPage.getExitButton()).thenReturn(mockExitButton);
        when(mockPage.getDeleteButton()).thenReturn(mockDeleteButton);
        when(mockPage.getHistoryButton()).thenReturn(mockHistoryButton);
        when(mockPage.getConsumeButton()).thenReturn(mockConsumeButton);
        when(mockPage.getSaveButton()).thenReturn(mockSaveButton);
        when(mockPage.getTransferButton()).thenReturn(mockTransferButton);
        when(mockPage.getCurrentAccount()).thenReturn(mockAccount);

        controller = new AccountController(mockPage, mockAccountManager, mockUiStack);
    }

    @Test
    void testInitController() {
        // Verify event listeners are added for common buttons
        verify(mockExitButton).addActionListener(any(ActionListener.class));
        verify(mockDeleteButton).addActionListener(any(ActionListener.class));
        verify(mockHistoryButton).addActionListener(any(ActionListener.class));
    }

    @Test
    void testInitControllerWithPiggyBank() {
        // 设置特定账户类型
        when(mockPage.getCurrentAccount()).thenReturn(mock(PiggyBank.class));
        controller = new AccountController(mockPage, mockAccountManager, mockUiStack);

        verify(mockConsumeButton).addActionListener(any(ActionListener.class));
        verify(mockSaveButton).addActionListener(any(ActionListener.class));
        verify(mockTransferButton).addActionListener(any(ActionListener.class));
    }

    @Test
    void testInitControllerWithCurrentAccount() {
        // 设置特定账户类型
        when(mockPage.getCurrentAccount()).thenReturn(mock(CurrentAccount.class));
        controller = new AccountController(mockPage, mockAccountManager, mockUiStack);

        verify(mockTransferButton).addActionListener(any(ActionListener.class));
    }

    @Test
    void testInitControllerWithSavingAccount() {
        // 设置特定账户类型
        when(mockPage.getCurrentAccount()).thenReturn(mock(SavingAccount.class));
        controller = new AccountController(mockPage, mockAccountManager, mockUiStack);

        // No additional listeners should be added for SavingAccount
        verify(mockConsumeButton, never()).addActionListener(any(ActionListener.class));
        verify(mockSaveButton, never()).addActionListener(any(ActionListener.class));
        verify(mockTransferButton, never()).addActionListener(any(ActionListener.class));
    }


    @Test
    void testVisibilityToggle() {
        // 测试界面可见性切换
        controller.toggleVisibility();
        verify(mockPage).setVisible(anyBoolean());
    }

    @Test
    void testPropertyChangeUpdatesPage() {
        // 模拟属性变更
        controller.propertyChange(null);
        verify(mockPage).updatePage();
    }

    @Test
    void testDispose() {
        // 测试资源释放
        controller.dispose();
        verify(mockPage).dispose();
    }
}
