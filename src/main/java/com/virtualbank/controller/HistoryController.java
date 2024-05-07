package com.virtualbank.controller;

import com.virtualbank.ui.HistoryPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import javax.swing.JOptionPane;

public class HistoryController {
    private HistoryPage historyPage;

    public HistoryController(HistoryPage historyPage) {
        this.historyPage = historyPage;
        initController();
    }

    private void initController() {
        // 绑定退出按钮的事件处理器
        historyPage.getExitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performExitOperation();
            }
        });

        // 绑定滚动条的调整监听器
        historyPage.getScrollPane().getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                // 这里可以放置一些逻辑，例如动态加载更多的历史记录等
                if (!e.getValueIsAdjusting()) {
                    loadMoreHistory();
                }
            }
        });
    }

    private void performExitOperation() {
        // 关闭窗口，返回到账户概览界面（假设已经存在）
        historyPage.closeWindow();
        // 如果需要，可以在这里加入更多的逻辑，比如打开另一个视图等
    }

    private void loadMoreHistory() {
        // 这里可以实现当用户滚动到底部时加载更多历史记录的功能
        // 目前这个方法是空的，可以根据需要实现具体功能
    }
}
