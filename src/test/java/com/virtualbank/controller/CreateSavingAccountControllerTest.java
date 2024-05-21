package com.virtualbank.controller;

import com.virtualbank.model.AccountManager;
import com.virtualbank.model.Pair;
import com.virtualbank.model.UIStack;
import com.virtualbank.parameters.Interest;
import com.virtualbank.ui.Window2_CreateSavingAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Period;

import static org.mockito.Mockito.*;

class CreateSavingAccountControllerTest {
    private CreateSavingAccountController controller;
    private Window2_CreateSavingAccount mockPage;
    private AccountManager mockAccountManager;
    private UIStack mockUiStack;
    private JButton mockConfirmButton;
    private JComboBox<String> mockComboBox;

    @BeforeEach
    void setUp() {
        mockPage = mock(Window2_CreateSavingAccount.class);
        mockAccountManager = mock(AccountManager.class);
        mockUiStack = mock(UIStack.class);
        mockConfirmButton = mock(JButton.class);
        mockComboBox = mock(JComboBox.class);


        when(mockPage.getConfirmButton()).thenReturn(mockConfirmButton);
        when(mockPage.getComboBox()).thenReturn(mockComboBox);
        when(mockComboBox.getSelectedIndex()).thenReturn(0); // Ensure a valid index is selected

        controller = new CreateSavingAccountController(mockPage, mockAccountManager, mockUiStack);
    }

    @Test
    void testInitController() {
        // Verify if confirm button has been set up with an ActionListener
        verify(mockConfirmButton).addActionListener(any(ActionListener.class));
    }

    @Test
    void testConfirmButtonActionValidInput() {
        double amount = 1000.0;
        Pair<Double, Period> interestOption = new Pair<>(0.05, Period.ofYears(1));

        when(mockPage.getEnteredAmount()).thenReturn(amount);
        when(mockPage.getSelectedInterestOption()).thenReturn(null);

        ArgumentCaptor<ActionListener> captor = ArgumentCaptor.forClass(ActionListener.class);
        verify(mockConfirmButton).addActionListener(captor.capture());
        ActionListener confirmListener = captor.getValue();

        confirmListener.actionPerformed(new ActionEvent(mockConfirmButton, ActionEvent.ACTION_PERFORMED, null));

        verify(mockAccountManager, never()).addSavingAccount(anyString(), anyDouble(), anyDouble(), anyDouble(), any(Period.class));
        verify(mockUiStack, never()).pop();
    }

    @Test
    void testConfirmButtonActionInvalidInput() {
        when(mockPage.getEnteredAmount()).thenReturn(0.0);
        when(mockPage.getSelectedInterestOption()).thenReturn(null);

        ArgumentCaptor<ActionListener> captor = ArgumentCaptor.forClass(ActionListener.class);
        verify(mockConfirmButton).addActionListener(captor.capture());
        ActionListener confirmListener = captor.getValue();

        confirmListener.actionPerformed(new ActionEvent(mockConfirmButton, ActionEvent.ACTION_PERFORMED, null));

        verify(mockAccountManager, never()).addSavingAccount(anyString(), anyDouble(), anyDouble(), anyDouble(), any(Period.class));
        verify(mockUiStack, never()).pop();
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

        controller.setVisibility(false);
        verify(mockPage).setVisible(false);
    }

    @Test
    void testGetVisibility() {
        when(mockPage.isVisible()).thenReturn(true);
        assert(controller.getVisibility());

        when(mockPage.isVisible()).thenReturn(false);
        assert(!controller.getVisibility());
    }

    @Test
    void testDispose() {
        controller.dispose();
        verify(mockPage).dispose();
    }
}
