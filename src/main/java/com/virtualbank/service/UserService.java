package com.virtualbank.service;

import com.virtualbank.model.AccountManager;
import com.virtualbank.model.user.ChildUser;
import com.virtualbank.model.user.ParentUser;
import com.virtualbank.repository.UserRepository;
import com.virtualbank.model.user.User;
import com.virtualbank.repository.AccountManagerSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private UserRepository userRepository = new UserRepository();

    public boolean registerUser(String username, String password, boolean isParent) {
        try {
            if (!userRepository.existsByUsername(username)) {
                User user;
                if (isParent) {
                    user = new ParentUser(username, password);
                } else {
                    AccountManager accountManager = AccountManagerSerializer.deserializeAccountManager(username);
                    user = new ChildUser(username, password);
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
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //登录是框架
    // 用户登录方法
    public String loginUser(String username, String password) {
        try {
            List<User> users = userRepository.findAll();
            boolean userFound = false;
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    userFound = true;
                    if (user.getPassword().equals(password)) {
                        if (user instanceof ParentUser) {
                            return "Parent Page"; // 家长账号登录成功，返回家长页面
                        } else if (user instanceof ChildUser) {
                            return "Child Page"; // 孩子账号登录成功，返回孩子页面
                        }
                    } else {
                        return "Password Incorrect"; // 密码不正确
                    }
                }
            }
            if (!userFound) {
                return "Username does not exist"; // 账号不存在
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Login Failed"; // 登录失败，返回失败信息
        }
        return "Login Failed"; // 登录失败，返回失败信息
    }
}

