package com.virtualbank.controller;

import com.virtualbank.model.AccountManager;
import com.virtualbank.ui.Page03_ChildHome;
import com.virtualbank.model.UIStack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AccountManagerControllerTest {

    @Mock
    private Page03_ChildHome mockPage;
    @Mock
    private AccountManager mockAccountManager;
    @Mock
    private UIStack mockUiStack;
    @Mock
    private JButton mockExitButton;
    @Mock
    private JButton mockCreateAccountButton;
    @Mock
    private JButton mockGoalButton;
    @Mock
    private JButton mockTaskButton;
    @Mock
    private JPanel mockScrollPanel;  // Mock for the scroll panel

    private AccountManagerController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mock the buttons and ensure they are returned by the Page methods
        when(mockPage.getExitButton()).thenReturn(mockExitButton);
        when(mockPage.getCreateAccountButton()).thenReturn(mockCreateAccountButton);
        when(mockPage.getGoalButton()).thenReturn(mockGoalButton);
        when(mockPage.getTaskButton()).thenReturn(mockTaskButton);
        when(mockPage.getScrollPanel()).thenReturn(mockScrollPanel); // Ensure getScrollPanel returns the mocked JPanel

        controller = new AccountManagerController(mockPage, mockAccountManager, mockUiStack, "testUser");
    }

    @Test
    void testInitPage() {
        // Verifying if the controller sets up the action listeners correctly
        verify(mockExitButton).addActionListener(any(ActionListener.class));
        verify(mockCreateAccountButton).addActionListener(any(ActionListener.class));
        verify(mockGoalButton).addActionListener(any(ActionListener.class));
        verify(mockTaskButton).addActionListener(any(ActionListener.class));
    }




    @Test
    void testVisibilityMethods() {
        // Testing visibility toggles
        controller.toggleVisibility();
        verify(mockPage).setVisible(anyBoolean());

        controller.setVisibility(true);
        verify(mockPage, times(2)).setVisible(true);

        when(mockPage.isVisible()).thenReturn(true);
        assertTrue(controller.getVisibility());
    }
}
