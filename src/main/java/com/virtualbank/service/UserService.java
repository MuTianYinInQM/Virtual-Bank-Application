package com.virtualbank.service;

import com.virtualbank.model.AccountManager;
import com.virtualbank.model.user.ChildUser;
import com.virtualbank.model.user.ParentUser;
import com.virtualbank.model.user.User;
import com.virtualbank.repository.UserRepository;
import com.virtualbank.repository.AccountManagerSerializer;

import java.io.IOException;

public class UserService {
    private UserRepository userRepository = new UserRepository();

    // 注册用户方法
    public boolean registerUser(String username, String password, boolean isParent) {
        try {
            if (!userRepository.existsByUsername(username)) {
                User user;
                if (isParent) {
                    // 创建家长用户
                    user = new ParentUser(username, password);
                } else {
                    // 创建孩子用户并为其创建 AccountManager
                    user = new ChildUser(username, password);
                    AccountManager accountManager = new AccountManager();  // 创建新的 AccountManager
                    AccountManagerSerializer.serializeAccountManager(accountManager, username);  // 序列化新的 AccountManager
                }
                userRepository.save(user);
                return true;
            } else {
                System.out.println("Username already exists.");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 用户登录方法
    public String loginUser(String username, String password) {
        try {
            for (User user : userRepository.findAll()) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    if (user instanceof ParentUser) {
                        return "Parent Page"; // 家长账号登录成功，返回家长页面
                    } else if (user instanceof ChildUser) {
                        try {
                            AccountManager accountManager = AccountManagerSerializer.deserializeAccountManager(username);
                            // 可以在这里处理或检查 accountManager，如需要
                        } catch (Exception e) {
                            e.printStackTrace();
                            return "Failed to load account manager";
                        }
                        return "Child Page"; // 孩子账号登录成功，返回孩子页面
                    }
                }
            }
            return "Username or password incorrect";
        } catch (IOException e) {
            e.printStackTrace();
            return "Login Failed";
        }
    }

    // 获取账户管理器方法
    public AccountManager getAccountManager(String username) throws IOException, ClassNotFoundException {
        return AccountManagerSerializer.deserializeAccountManager(username);
    }
}
