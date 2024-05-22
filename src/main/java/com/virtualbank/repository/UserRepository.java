package com.virtualbank.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualbank.model.user.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final String FILE_PATH = "src/main/resources/users.json";
    private ObjectMapper objectMapper = new ObjectMapper();
    private File file = new File(FILE_PATH);
    private List<User> usersCache;

    public UserRepository() {
        initialize();
    }

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

    public synchronized List<User> loadUsersFromFile() throws IOException {
        if (file.length() == 0) {
            usersCache = new ArrayList<>();
        } else {
            usersCache = objectMapper.readValue(file, new TypeReference<List<User>>() {});
        }
        return usersCache;
    }

    public synchronized List<User> findAll() throws IOException {
        return usersCache != null ? usersCache : loadUsersFromFile();
    }

    public synchronized boolean existsByUsername(String username) throws IOException {
        return findAll().stream().anyMatch(u -> u.getUsername().equals(username));
    }

    public synchronized void save(User user) throws IOException {
        if (!existsByUsername(user.getUsername())) {
            usersCache.add(user);
            objectMapper.writeValue(file, usersCache);
        } else {
            throw new IllegalArgumentException("Username already exists");
        }
    }
}
