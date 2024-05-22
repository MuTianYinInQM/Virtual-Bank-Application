package com.virtualbank.service;

import com.virtualbank.model.AccountManager;
import com.virtualbank.model.user.ChildUser;
import com.virtualbank.model.user.ParentUser;
import com.virtualbank.model.user.User;
import com.virtualbank.repository.UserRepository;
import com.virtualbank.repository.AccountManagerSerializer;

import java.io.IOException;
import java.util.List;

public class UserService {
    private UserRepository userRepository = new UserRepository();

    public void loadUserData() throws IOException {
        userRepository.loadUsersFromFile();
    }

    public boolean registerUser(String username, String password, boolean isParent) {
        try {
            if (!userRepository.existsByUsername(username)) {
                User user;
                if (isParent) {
                    user = new ParentUser(username, password);
                } else {
                    user = new ChildUser(username, password);
                    AccountManager accountManager = new AccountManager();
                    AccountManagerSerializer.serializeAccountManager(accountManager, username);
                }
                userRepository.save(user);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String loginUser(String username, String password) {
        try {
            List<User> users = userRepository.findAll();
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    if (user.isParent()) {
                        return "Parent Page";
                    } else {
                        try {
                            AccountManager accountManager = AccountManagerSerializer.deserializeAccountManager(username);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return "Failed to load account manager";
                        }
                        return "Child Page";
                    }
                }
            }
            return "Username or password incorrect";
        } catch (IOException e) {
            e.printStackTrace();
            return "Login Failed";
        }
    }

    public AccountManager getAccountManager(String username) throws IOException, ClassNotFoundException {
        return AccountManagerSerializer.deserializeAccountManager(username);
    }
}
