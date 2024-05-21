package com.virtualbank.controller;

import com.virtualbank.model.AccountManager;
import com.virtualbank.model.UIStack;
import com.virtualbank.ui.Window1_ChooseAccountType;
import com.virtualbank.parameters.Interest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.mockito.Mockito.*;

class AccountTypeChooseControllerTest {
    private AccountTypeChooseController controller;
    private Window1_ChooseAccountType mockPage;
    private AccountManager mockAccountManager;
    private UIStack mockUiStack;
    private JButton mockConfirmButton;
    private JCheckBox mockCurrentAccountButton, mockSavingAccountButton;

    @BeforeEach
    void setUp() {
        mockPage = mock(Window1_ChooseAccountType.class);
        mockAccountManager = mock(AccountManager.class);
        mockUiStack = mock(UIStack.class);
        mockConfirmButton = mock(JButton.class);
        mockCurrentAccountButton = mock(JCheckBox.class);
        mockSavingAccountButton = mock(JCheckBox.class);

        when(mockPage.getConfirmButton()).thenReturn(mockConfirmButton);
        when(mockPage.getCurrentAccountButton()).thenReturn(mockCurrentAccountButton);
        when(mockPage.getSavingAccountButton()).thenReturn(mockSavingAccountButton);

        controller = new AccountTypeChooseController(mockPage, mockAccountManager, mockUiStack);
    }

    @Test
    void testInitController() {
        // Verify if confirm button has been set up with an ActionListener
        verify(mockConfirmButton).addActionListener(any(ActionListener.class));
    }

    @Test
    void testConfirmButtonActionCurrentAccount() {
        when(mockCurrentAccountButton.isSelected()).thenReturn(true);
        when(mockSavingAccountButton.isSelected()).thenReturn(false);

        ArgumentCaptor<ActionListener> captor = ArgumentCaptor.forClass(ActionListener.class);
        verify(mockConfirmButton).addActionListener(captor.capture());
        ActionListener confirmListener = captor.getValue();

        confirmListener.actionPerformed(new ActionEvent(mockConfirmButton, ActionEvent.ACTION_PERFORMED, null));

        verify(mockAccountManager).addCurrentAccount(eq("no name"), eq(Interest.currentInterest), eq(Interest.timeLapseCoefficient));
        verify(mockUiStack).pop();
    }

    @Test
    void testConfirmButtonActionSavingAccount() {
        when(mockCurrentAccountButton.isSelected()).thenReturn(false);
        when(mockSavingAccountButton.isSelected()).thenReturn(true);

        ArgumentCaptor<ActionListener> captor = ArgumentCaptor.forClass(ActionListener.class);
        verify(mockConfirmButton).addActionListener(captor.capture());
        ActionListener confirmListener = captor.getValue();

        confirmListener.actionPerformed(new ActionEvent(mockConfirmButton, ActionEvent.ACTION_PERFORMED, null));

        verify(mockUiStack).swapWindow(any(CreateSavingAccountController.class));
    }

    @Test
    void testConfirmButtonActionNoSelection() {
        when(mockCurrentAccountButton.isSelected()).thenReturn(false);
        when(mockSavingAccountButton.isSelected()).thenReturn(false);

        ArgumentCaptor<ActionListener> captor = ArgumentCaptor.forClass(ActionListener.class);
        verify(mockConfirmButton).addActionListener(captor.capture());
        ActionListener confirmListener = captor.getValue();

        confirmListener.actionPerformed(new ActionEvent(mockConfirmButton, ActionEvent.ACTION_PERFORMED, null));

        verify(mockAccountManager, never()).addCurrentAccount(anyString(), anyDouble(), anyDouble());
        verify(mockUiStack, never()).pop();
        verify(mockUiStack, never()).swapWindow(any());
        //verify(mockPage).showMessageDialog(any(Component.class), eq("Please select an account type."));
    }

    @Test
    void testVisibilityToggle() {
        // Test visibility toggle
        controller.toggleVisibility();
        verify(mockPage).setVisible(anyBoolean());
    }

    @Test
    void testSetVisibility() {
        // Test set visibility
        controller.setVisibility(true);
        verify(mockPage).setVisible(true);

        controller.setVisibility(false);
        verify(mockPage).setVisible(false);
    }

    @Test
    void testGetVisibility() {
        // Test get visibility
        when(mockPage.isVisible()).thenReturn(true);
        assert(controller.getVisibility());

        when(mockPage.isVisible()).thenReturn(false);
        assert(!controller.getVisibility());
    }

    @Test
    void testDispose() {
        // Test dispose
        controller.dispose();
        verify(mockPage).dispose();
    }
}
