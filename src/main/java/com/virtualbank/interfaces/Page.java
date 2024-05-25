package com.virtualbank.interfaces;

/**
 * Interface representing a page in a user interface.
 */
public interface Page {
    /**
     * Toggles the visibility of the current page.
     * If the page is currently visible, it will be made invisible.
     * If it is invisible, it will be made visible.
     */
    void toggleVisibility();

    /**
     * Sets the visibility of the page.
     *
     * @param visibility {@code true} to make the page visible, {@code false} to make it invisible
     */
    void setVisibility(boolean visibility);

    /**
     * Gets the visibility of the page.
     *
     * @return {@code true} if the page is visible, {@code false} if it is invisible
     */
    boolean getVisibility();

    /**
     * Disposes of the page, releasing any resources associated with it.
     * This typically involves removing it from the UI and freeing memory.
     */
    void dispose();
}
