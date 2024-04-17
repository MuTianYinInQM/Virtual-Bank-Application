package com.virtualbank.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualbank.model.SavingGoal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SavingGoalRepository {

    private final File file = new File("savingGoals.json"); // JSON文件的路径
    private final ObjectMapper objectMapper = new ObjectMapper(); // 用于JSON的序列化和反序列化

    // 获取所有SavingGoal对象
    public List<SavingGoal> findAll() {
        try {
            if (file.exists()) {
                return objectMapper.readValue(file, new TypeReference<List<SavingGoal>>() {});
            } else {
                return new ArrayList<>(); // 文件不存在，返回空列表
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(); // 读取出错，返回空列表
        }
    }

    // 根据ID查找SavingGoal对象
    public Optional<SavingGoal> findById(String id) {
        return findAll().stream()
                .filter(goal -> goal.getId().equals(id))
                .findFirst();
    }

    // 保存或更新SavingGoal对象
    public void save(SavingGoal goal) {
        List<SavingGoal> goals = findAll();
        Optional<SavingGoal> existingGoal = findById(goal.getId());
        if (existingGoal.isPresent()) {
            int index = goals.indexOf(existingGoal.get());
            goals.set(index, goal); // 更新操作
        } else {
            goals.add(goal); // 新增操作
        }
        saveToFile(goals);
    }

    // 根据ID删除SavingGoal对象
    public void deleteById(String id) {
        List<SavingGoal> goals = findAll();
        goals.removeIf(goal -> goal.getId().equals(id));
        saveToFile(goals);
    }

    // 将所有SavingGoal对象保存到文件
    private void saveToFile(List<SavingGoal> goals) {
        try {
            objectMapper.writeValue(file, goals);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
