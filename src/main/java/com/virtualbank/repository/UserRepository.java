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
                usersCache = new ArrayList<>();  // 初始化一个空的用户列表
                objectMapper.writeValue(file, usersCache);  // 将空列表写入文件
            } else {
                usersCache = findAll();  // 加载现有用户到缓存
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<User> findAll() throws IOException {
        if (usersCache == null) {
            if (file.length() == 0) {  // 检查文件是否为空
                usersCache = new ArrayList<>();  // 直接返回一个新的空列表
            } else {
                usersCache = objectMapper.readValue(file, new TypeReference<List<User>>() {});
            }
        }
        return usersCache;
    }

    public boolean existsByUsername(String username) throws IOException {
        List<User> users = findAll();
        return users.stream().anyMatch(u -> u.getUsername().equals(username));
    }



    public void save(User user) throws IOException {
        if (usersCache == null) {
            usersCache = findAll();
        }
        if (!existsByUsername(user.getUsername())) {  // 仅在用户名不存在时添加
            usersCache.add(user);
            objectMapper.writeValue(file, usersCache);
        } else {
            throw new IllegalArgumentException("Username already exists");
        }
    }

}
