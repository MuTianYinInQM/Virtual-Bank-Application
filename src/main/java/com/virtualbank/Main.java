package com.virtualbank;

import com.virtualbank.controller.ParentHomeController;
import com.virtualbank.service.TaskService;
import com.virtualbank.ui.Page04_ParentHome;

public class Main {
    public static void main(String[] args) {
        // 创建主界面的实例
        Page04_ParentHome parentHomeUI = new Page04_ParentHome();
        TaskService taskService = new TaskService();
        // 创建控制器，并将主界面实例传递给它
        new ParentHomeController(taskService, parentHomeUI);

        // 显示主界面
        parentHomeUI.setVisible(true);
    }
}

//package com.virtualbank;
//import com.virtualbank.controller.ChildTaskController;
//import com.virtualbank.model.AccountManager;
//import com.virtualbank.service.TaskService;
//import com.virtualbank.ui.Page05_ChildTask;
//
//public class Main {
//    public static void main(String[] args) {
//        Page05_ChildTask childTaskUI = new Page05_ChildTask();
//        TaskService taskService = new TaskService();  // 确保 TaskService 正确初始化，可能需要数据库或任务存储的配置
//        AccountManager accountManager = new AccountManager();
//
//        // 创建控制器并将儿童任务页面UI和任务服务传递给它
//        new ChildTaskController(taskService, childTaskUI, accountManager);
//    }
//}

//package com.virtualbank;

//import com.virtualbank.controller.ChildTaskController;
//import com.virtualbank.controller.UserLoginController;
//import com.virtualbank.service.TaskService;
//import com.virtualbank.service.UserService;
//import com.virtualbank.ui.Page01_Login;
//import com.virtualbank.ui.Page05_ChildTask;
//
//public class Main {
//    public static void main(String[] args) {
//        UserService userService = new UserService(); // 确保UserService适当地初始化
//        Page01_Login page01Login = new Page01_Login();
//        new UserLoginController(page01Login, userService);
//    }
//
//}