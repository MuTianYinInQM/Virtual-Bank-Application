package com.virtualbank.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.virtualbank.model.AccountManager;
import com.virtualbank.model.UIStack;
import com.virtualbank.ui.Page03_ChildHome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.beans.PropertyChangeEvent;

class AccountManagerControllerTest {
    private AccountManagerController controller;
    private Page03_ChildHome mockPage;
    private AccountManager mockAccountManager;
    private UIStack mockUiStack;

    @BeforeEach
    void setUp() {
        mockPage = mock(Page03_ChildHome.class);
        mockAccountManager = mock(AccountManager.class);
        mockUiStack = mock(UIStack.class);

        when(mockPage.getExitButton()).thenReturn(mock(JButton.class));
        when(mockPage.getCreateAccountButton()).thenReturn(mock(JButton.class));
        when(mockPage.getScrollPanel()).thenReturn(mock(JPanel.class));

    }

    @Test
    void testInitPage() {
        // test initPage
        verify(mockPage.getExitButton(), times(1)).addActionListener(any());
        verify(mockPage.getCreateAccountButton(), times(1)).addActionListener(any());
    }

    @Test
    void testPropertyChange() {
        PropertyChangeEvent evt = new PropertyChangeEvent(this, "balance", null, null);
        controller.propertyChange(evt);

        verify(mockAccountManager, atLeastOnce()).getTotalBalance();
        verify(mockPage, atLeastOnce()).totalBalanceUpdate(anyDouble());

        // test other properties
        verify(mockPage.getScrollPanel(), atLeastOnce()).removeAll();
        verify(mockPage.getScrollPanel(), atLeastOnce()).revalidate();
        verify(mockPage.getScrollPanel(), atLeastOnce()).repaint();
    }

    @Test
    void testToggleVisibility() {
        // test toggle visibility
        controller.toggleVisibility();
        verify(mockPage, times(1)).setVisible(anyBoolean());
    }

    @Test
    void testSetVisibility() {
        // test set visibility
        controller.setVisibility(true);
        verify(mockPage, times(1)).setVisible(true);
        controller.setVisibility(false);
        verify(mockPage, times(1)).setVisible(false);
    }

    @Test
    void testGetVisibility() {
        // test get visibility
        when(mockPage.isVisible()).thenReturn(true);
        assertTrue(controller.getVisibility());
        when(mockPage.isVisible()).thenReturn(false);
        assertFalse(controller.getVisibility());
    }



    @Test
    void testDispose() {
        // test dispose
        controller.dispose();
        verify(mockPage, times(1)).dispose();
    }
}
