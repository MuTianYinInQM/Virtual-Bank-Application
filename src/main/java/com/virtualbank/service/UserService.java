package com.virtualbank.service;

import com.virtualbank.model.AccountManager;
import com.virtualbank.model.user.ChildUser;
import com.virtualbank.model.user.ParentUser;
import com.virtualbank.model.user.User;
import com.virtualbank.repository.UserRepository;
import com.virtualbank.repository.AccountManagerSerializer;

import java.io.IOException;
import java.util.List;
/**
 * Service class for managing user-related operations.
 */
public class UserService {
    private UserRepository userRepository = new UserRepository();

    /**
     * Loads user data from the repository.
     *
     * @throws IOException if an I/O error occurs
     */
    public void loadUserData() throws IOException {
        userRepository.loadUsersFromFile();
    }

    /**
     * Registers a new user with the specified username, password, and parent status.
     *
     * @param username the username of the new user
     * @param password the password of the new user
     * @param isParent whether the new user is a parent
     * @return true if registration is successful, false if the username already exists or an error occurs
     */
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

    /**
     * Logs in a user with the specified username and password.
     *
     * @param username the username of the user attempting to log in
     * @param password the password of the user attempting to log in
     * @return a string indicating the result of the login attempt:
     *         "Parent Page" if the user is a parent,
     *         "Child Page" if the user is a child,
     *         "Failed to load account manager" if there is an error loading the account manager,
     *         "Username or password incorrect" if the username or password is incorrect,
     *         or "Login Failed" if there is a general login failure
     */
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

    /**
     * Retrieves the account manager associated with the specified username.
     *
     * @param username the username associated with the account manager
     * @return the account manager associated with the specified username
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    public AccountManager getAccountManager(String username) throws IOException, ClassNotFoundException {
        return AccountManagerSerializer.deserializeAccountManager(username);
    }
}
