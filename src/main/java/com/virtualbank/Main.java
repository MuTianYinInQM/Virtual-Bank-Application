package com.virtualbank;

import com.virtualbank.controller.ChildTaskController;
import com.virtualbank.controller.UserLoginController;
import com.virtualbank.service.TaskService;
import com.virtualbank.service.UserService;
import com.virtualbank.ui.Page01_Login;
import com.virtualbank.ui.Page05_ChildTask;
import com.virtualbank.model.UIStack;
import com.virtualbank.service.TaskService;
public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService(); // 确保UserService适当地初始化
        Page01_Login page01Login = new Page01_Login();
        UIStack uiStack = new UIStack();
        TaskService taskService = new TaskService();

        UserLoginController userLoginController = new UserLoginController(page01Login, userService, taskService, uiStack);
        uiStack.pushPage(userLoginController);
    }

}