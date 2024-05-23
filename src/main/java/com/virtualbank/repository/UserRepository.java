package com.virtualbank.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualbank.model.user.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for managing user data.
 */
public class UserRepository {
    private static final String FILE_PATH = "src/main/resources/users.json";
    private ObjectMapper objectMapper = new ObjectMapper();
    private File file = new File(FILE_PATH);
    private List<User> usersCache;

    /**
     * Constructs a UserRepository and initializes the user data.
     */
    public UserRepository() {
        initialize();
    }

    /**
     * Initializes the user data by creating the file if it does not exist or loading the users from the file if it does.
     */
    private void initialize() {
        try {
            if (!file.exists()) {
                file.createNewFile();
                usersCache = new ArrayList<>();
                objectMapper.writeValue(file, usersCache);
            } else {
                usersCache = loadUsersFromFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads users from the JSON file.
     *
     * @return the list of users
     * @throws IOException if an I/O error occurs
     */
    public synchronized List<User> loadUsersFromFile() throws IOException {
        if (file.length() == 0) {
            usersCache = new ArrayList<>();
        } else {
            usersCache = objectMapper.readValue(file, new TypeReference<List<User>>() {});
        }
        return usersCache;
    }

    /**
     * Returns all users.
     *
     * @return the list of all users
     * @throws IOException if an I/O error occurs
     */
    public synchronized List<User> findAll() throws IOException {
        return usersCache != null ? usersCache : loadUsersFromFile();
    }

    /**
     * Checks if a user with the specified username exists.
     *
     * @param username the username to check
     * @return true if the user exists, false otherwise
     * @throws IOException if an I/O error occurs
     */
    public synchronized boolean existsByUsername(String username) throws IOException {
        return findAll().stream().anyMatch(u -> u.getUsername().equals(username));
    }

    /**
     * Saves a new user to the repository.
     *
     * @param user the user to save
     * @throws IOException if an I/O error occurs
     * @throws IllegalArgumentException if a user with the same username already exists
     */
    public synchronized void save(User user) throws IOException {
        if (!existsByUsername(user.getUsername())) {
            usersCache.add(user);
            objectMapper.writeValue(file, usersCache);
        } else {
            throw new IllegalArgumentException("Username already exists");
        }
    }
}
