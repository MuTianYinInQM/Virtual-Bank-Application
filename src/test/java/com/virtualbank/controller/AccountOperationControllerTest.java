package com.virtualbank.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.virtualbank.model.AccountManager;
import com.virtualbank.model.UIStack;
import com.virtualbank.model.account.Account;
import com.virtualbank.model.AccountOperationType;
import com.virtualbank.ui.Window07_AccountOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

class AccountOperationControllerTest {
    private AccountOperationController controller;
    private Window07_AccountOperation mockPage;
    private AccountManager mockAccountManager;
    private UIStack mockUiStack;
    private Account mockAccount;
    private JButton mockConfirmButton;

    @BeforeEach
    void setUp() {
        mockPage = mock(Window07_AccountOperation.class);
        mockAccountManager = mock(AccountManager.class);
        mockUiStack = mock(UIStack.class);
        mockAccount = mock(Account.class);
        mockConfirmButton = mock(JButton.class);

        when(mockPage.getConfirmButton()).thenReturn(mockConfirmButton);
        when(mockPage.getAccount()).thenReturn(mockAccount);

        controller = new AccountOperationController(mockPage, mockAccountManager, mockUiStack);
    }

    @Test
    void testInitController() {
        // Verify if confirm button has been set up with an ActionListener
        verify(mockConfirmButton).addActionListener(any(ActionListener.class));
    }

    @Test
    void testConfirmButtonActionSave() {
        UUID accountUuid = UUID.randomUUID();
        when(mockAccount.getUuid()).thenReturn(accountUuid);
        when(mockPage.getAmount()).thenReturn(100.0);
        when(mockPage.getDescription()).thenReturn("Deposit");
        when(mockPage.getOperationType()).thenReturn(AccountOperationType.SAVE);

        ArgumentCaptor<ActionListener> captor = ArgumentCaptor.forClass(ActionListener.class);
        verify(mockConfirmButton).addActionListener(captor.capture());
        ActionListener confirmListener = captor.getValue();

        confirmListener.actionPerformed(new ActionEvent(mockConfirmButton, ActionEvent.ACTION_PERFORMED, null));

        verify(mockAccountManager).save(accountUuid, 100.0, "Deposit");
        verify(mockUiStack).pop();
    }

    @Test
    void testConfirmButtonActionTransfer() {
        UUID accountUuid = UUID.randomUUID();
        when(mockAccount.getUuid()).thenReturn(accountUuid);
        when(mockPage.getAmount()).thenReturn(50.0);
        when(mockPage.getDescription()).thenReturn("Transfer to piggy bank");
        when(mockPage.getOperationType()).thenReturn(AccountOperationType.TRANSFER);

        ArgumentCaptor<ActionListener> captor = ArgumentCaptor.forClass(ActionListener.class);
        verify(mockConfirmButton).addActionListener(captor.capture());
        ActionListener confirmListener = captor.getValue();

        confirmListener.actionPerformed(new ActionEvent(mockConfirmButton, ActionEvent.ACTION_PERFORMED, null));

        verify(mockAccountManager).transfer(accountUuid, mockAccountManager.getPiggyUuid(), 50.0, "Transfer to piggy bank");
        verify(mockUiStack).pop();
    }

    @Test
    void testConfirmButtonActionConsume() {
        UUID accountUuid = UUID.randomUUID();
        when(mockAccount.getUuid()).thenReturn(accountUuid);
        when(mockPage.getAmount()).thenReturn(30.0);
        when(mockPage.getDescription()).thenReturn("Purchase");
        when(mockPage.getOperationType()).thenReturn(AccountOperationType.CONSUME);

        ArgumentCaptor<ActionListener> captor = ArgumentCaptor.forClass(ActionListener.class);
        verify(mockConfirmButton).addActionListener(captor.capture());
        ActionListener confirmListener = captor.getValue();

        confirmListener.actionPerformed(new ActionEvent(mockConfirmButton, ActionEvent.ACTION_PERFORMED, null));

        verify(mockAccountManager).consume(accountUuid, 30.0, "Purchase");
        verify(mockUiStack).pop();
    }


    @Test
    void testToggleVisibility() {
        controller.toggleVisibility();
        verify(mockPage).setVisible(anyBoolean());
    }

    @Test
    void testSetVisibility() {
        controller.setVisibility(true);
        verify(mockPage).setVisible(true);
    }

    @Test
    void testGetVisibility() {
        when(mockPage.isVisible()).thenReturn(true);
        assertTrue(controller.getVisibility());
        when(mockPage.isVisible()).thenReturn(false);
        assertFalse(controller.getVisibility());
    }

    @Test
    void testDispose() {
        controller.dispose();
        verify(mockPage).dispose();
    }
}
