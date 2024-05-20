package com.virtualbank.controller;

import com.virtualbank.interfaces.Page;
import com.virtualbank.model.UIStack;
import com.virtualbank.ui.Page08_History;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import java.util.UUID;

public class HistoryController implements Page {
    private Page08_History page;
    private UIStack uiStack;

    public HistoryController(Page08_History page, UIStack uiStack) {
        this.page = page;
        this.uiStack = uiStack;
        initController();
    }

    private void initController() {
        // 绑定退出按钮的事件处理器
        page.getExitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiStack.pop();
            }
        });

//        // 绑定滚动条的调整监听器
//        page.getScrollPane().getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
//            @Override
//            public void adjustmentValueChanged(AdjustmentEvent e) {
//                // 这里可以放置一些逻辑，例如动态加载更多的历史记录等
//                if (!e.getValueIsAdjusting()) {
//                    loadMoreHistory();
//                }
//            }
//        });
    }

    @Override
    public void toggleVisibility() {
        this.page.setVisible(!this.page.isVisible());
    }

    @Override
    public void setVisibility(boolean visibility) {
        this.page.setVisible(visibility);
    }

    @Override
    public boolean getVisibility() {
        return this.page.isVisible();
    }

    @Override
    public void dispose() {
        this.page.dispose();
    }


//    private void loadMoreHistory() {
//        // 这里可以实现当用户滚动到底部时加载更多历史记录的功能
//        // 目前这个方法是空的，可以根据需要实现具体功能
//        // TODO
//    }

    public static void main(String[] args) {
        Page08_History historyPage = new Page08_History(UUID.fromString("0b805b57-819f-4c65-88ec-357d89009f5c"));  // 假设账户ID已给出
    }

}
