package com.virtualbank.interfaces;

public interface Page {
    /**
     * Toggles the visibility of the current page.
     * If the page is currently visible, it will be made invisible.
     * If it is invisible, it will be made visible.
     */
    void toggleVisibility();

    void setVisibility(boolean visibility);

    boolean getVisibility();

    void dispose();
}
