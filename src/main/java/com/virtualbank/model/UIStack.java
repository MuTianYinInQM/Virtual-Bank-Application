package com.virtualbank.model;

import com.virtualbank.interfaces.Page;

import java.util.Stack;

/**
 * A stack for managing UI pages in a virtual bank application.
 */
public class UIStack {
    private Stack<Page> stack;

    /**
     * Constructs a new UIStack.
     */
    public UIStack() {
        this.stack = new Stack<>();
    }

    /**
     * Pushes a page onto the stack.
     *
     * @param view the page to push
     */
    public void pushPage(Page view) {
        // 需要确保 view是可见的
        // 理论上这个可以删除
        view.setVisibility(true);

        if (!stack.isEmpty()) {
            stack.peek().toggleVisibility(); // Make the current top view invisible
        }
        stack.push(view);
    }

    // 添加一个可见的小窗 不改变底下物体的可见性
    // pop 时候由于下面已经可见 因此就不翻转了

    /**
     * Pushes a window onto the stack.
     *
     * @param view the window to push
     */
    public void pushWindow(Page view) {
        // 需要确保 view是可见的
        // 理论上这个可以删除
        view.setVisibility(true);
        stack.push(view);
    }

    // pop出来顶部的那个显示着的
    // 然后push一个

    /**
     * Swaps the top window with the given window.
     *
     * @param view the window to swap with the top window
     */
    public void swapWindow(Page view) {
        if (!stack.isEmpty()) {
            Page topView = stack.pop();
            topView.dispose();

            view.setVisibility(true);
            stack.push(view);
        }
    }

    /**
     * Pops the top page/window from the stack.
     * If the stack becomes empty after popping, the application exits.
     */
    public void pop() {
        if (!stack.isEmpty()) {
            Page topView = stack.pop();
            // The top view is supposed to be visible, so we make it invisible upon removal
            topView.dispose(); // Make the top view invisible

            if (!stack.isEmpty()) {
                // Ensure the new top view is visible
                if (!stack.peek().getVisibility()) {
                    stack.peek().toggleVisibility();
                }
            }
        }
        // 如果栈里面没有东西了 就停止运行
        if (stack.isEmpty()) {
            System.exit(0);
        }
    }

    /**
     * Returns the size of the stack.
     *
     * @return the size of the stack
     */
    public int size() {
        return stack.size();
    }
}
