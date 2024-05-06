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

    private final File file = new File("src/main/resources/savingGoals.json"); // JSON文件的路径
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
    public Optional<SavingGoal> findById(String goalId) {
        Optional<SavingGoal> result = findAll().stream()
                .filter(goal -> goal.getGoalId().equals(goalId))
                .findFirst();
        System.out.println("findById - Found: " + result.isPresent());  // 打印是否找到目标
        return result;
    }

    // 保存或更新SavingGoal对象
    public void save(SavingGoal goal) {
        List<SavingGoal> goals = findAll();
        int index = -1;  // 初始化索引为-1
        for (int i = 0; i < goals.size(); i++) {
            if (goals.get(i).getGoalId().equals(goal.getGoalId())) {
                index = i;  // 找到匹配的目标索引
                break;
            }
        }

        if (index != -1) {
            goals.set(index, goal);  // 如果找到了，更新该目标
            System.out.println("Updated goal: " + goal);
        } else {
            goals.add(goal);  // 如果未找到，视为新目标添加
            System.out.println("Added new goal: " + goal);
        }
        saveToFile(goals);  // 保存修改后的目标列表到文件
    }

    // 根据ID删除SavingGoal对象
    public void deleteById(String goalId) {
        List<SavingGoal> goals = findAll();
        goals.removeIf(goal -> goal.getGoalId().equals(goalId));
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
