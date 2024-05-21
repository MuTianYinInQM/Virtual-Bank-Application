package com.virtualbank.controller;

import com.virtualbank.ui.Page08_History;
import com.virtualbank.model.UIStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import static org.mockito.Mockito.*;

class HistoryControllerTest {
    private HistoryController controller;
    private Page08_History mockPage;
    private UIStack mockUiStack;
    private JButton mockExitButton;

    @BeforeEach
    void setUp() {
        mockPage = mock(Page08_History.class);
        mockUiStack = mock(UIStack.class);
        mockExitButton = mock(JButton.class);

        when(mockPage.getExitButton()).thenReturn(mockExitButton);

        controller = new HistoryController(mockPage, mockUiStack);
    }

    @Test
    void testInitController() {
        // Verify if exit button has been set up with an ActionListener
        verify(mockExitButton).addActionListener(any(ActionListener.class));
    }

    @Test
    void testExitButtonAction() {
        // Capture and trigger the ActionListener
        ArgumentCaptor<ActionListener> captor = ArgumentCaptor.forClass(ActionListener.class);
        verify(mockExitButton).addActionListener(captor.capture());
        ActionListener exitListener = captor.getValue();

        // Trigger the click event
        exitListener.actionPerformed(new ActionEvent(mockExitButton, ActionEvent.ACTION_PERFORMED, null));

        // Verify that uiStack.pop() is called
        verify(mockUiStack).pop();
    }

    @Test
    void testToggleVisibility() {
        // Initial visibility is assumed to be false
        when(mockPage.isVisible()).thenReturn(false);

        controller.toggleVisibility();
        verify(mockPage).setVisible(true);

        // Simulate toggling visibility back to false
        when(mockPage.isVisible()).thenReturn(true);
        controller.toggleVisibility();
        verify(mockPage).setVisible(false);
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
