package com.virtualbank.ui;

import com.virtualbank.interfaces.ToggleVisibility;

import java.util.Stack;

public class UIStack {
    private Stack<ToggleVisibility> stack;

    public UIStack() {
        this.stack = new Stack<>();
    }


    public void pushPage(ToggleVisibility view) {
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
    public void pushWindows(ToggleVisibility view) {
        // 需要确保 view是可见的
        // 理论上这个可以删除
        view.setVisibility(true);
        stack.push(view);
    }

    public void pop() {
        if (!stack.isEmpty()) {
            ToggleVisibility topView = stack.pop();
            // The top view is supposed to be visible, so we make it invisible upon removal
            topView.toggleVisibility(); // Make the top view invisible

            if (!stack.isEmpty()) {
                // Ensure the new top view is visible
                if (!stack.peek().getVisibility()) {
                    stack.peek().toggleVisibility();
                }
            }
        }
    }
}
