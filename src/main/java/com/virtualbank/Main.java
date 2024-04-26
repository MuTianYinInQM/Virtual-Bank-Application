package com.virtualbank;

import com.virtualbank.controller.CreateTaskController;
import com.virtualbank.controller.ParentHomeController;
import com.virtualbank.service.TaskService;
import com.virtualbank.ui.Page04_ParentHome;
import com.virtualbank.ui.Window3_CreateNewTask;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Page04_ParentHome parentHomeUI = new Page04_ParentHome();
        // 创建控制器并将家长首页UI传递给它
        new ParentHomeController(parentHomeUI);
    }
}
