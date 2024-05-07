package com.virtualbank.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.virtualbank.model.user.User;

public class UserRepository {
    private static final String FILE_PATH = "src/main/resources/users.json";
    private ObjectMapper objectMapper = new ObjectMapper();
    private File file = new File(FILE_PATH);
    private List<User> usersCache; // 添加一个缓存列表

    public UserRepository() {
        initialize();
    }

    private void initialize() {
        try {
            if (!file.exists()) {
                file.createNewFile();
                objectMapper.writeValue(file, new ArrayList<User>()); // 初始化空的用户列表
                usersCache = new ArrayList<>();
            } else {
                usersCache = findAll(); // 加载现有用户到缓存
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> findAll() throws IOException {
        if (usersCache == null) {
            usersCache = objectMapper.readValue(file, new TypeReference<List<User>>() {});
        }
        return usersCache;
    }

    public boolean existsByUsername(String username) throws IOException {
        return findAll().stream().anyMatch(u -> u.getUsername().equals(username));
    }

    public void save(User user) throws IOException {
        if (usersCache == null) {
            usersCache = findAll();
        }
        usersCache.add(user);
        objectMapper.writeValue(file, usersCache);
    }
}
