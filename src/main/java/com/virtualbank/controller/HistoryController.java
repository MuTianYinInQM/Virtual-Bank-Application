package com.virtualbank.controller;

import com.virtualbank.interfaces.Page;
import com.virtualbank.model.UIStack;
import com.virtualbank.ui.Page08_History;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import java.util.UUID;

/**
 * Controls the interactions for the history page view.
 * This controller manages the operations related to the history page,
 * including navigating back to the previous page and managing the visibility of the history page.
 */
public class HistoryController implements Page {
    private Page08_History page;
    private UIStack uiStack;


    /**
     * Constructs a new HistoryController.
     *
     * @param page    The history page view to be controlled.
     * @param uiStack The UI stack for managing page navigation.
     */
    public HistoryController(Page08_History page, UIStack uiStack) {
        this.page = page;
        this.uiStack = uiStack;
        initController();
    }



    /**
     * Initializes the controller by setting up the event listeners.
     */
    private void initController() {
        // initialize the controller here
        page.getExitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiStack.pop();
            }
        });

    }


    /**
     * Toggles the visibility of the history page.
     */
    @Override
    public void toggleVisibility() {
        // toggle the visibility of the page here
        this.page.setVisible(!this.page.isVisible());
    }



    /**
     * Sets the visibility of the history page.
     *
     * @param visibility true to make the page visible; false otherwise.
     */
    @Override
    public void setVisibility(boolean visibility) {
        // set the visibility of the page here
        this.page.setVisible(visibility);
    }



    /**
     * Returns the visibility status of the history page.
     *
     * @return true if the page is visible; false otherwise.
     */
    @Override
    public boolean getVisibility() {
        // return the visibility of the page here
        return this.page.isVisible();
    }



    /**
     * Disposes of the history page when it is no longer needed.
     */
    @Override
    public void dispose() {
        // dispose the page here
        this.page.dispose();
    }

}
