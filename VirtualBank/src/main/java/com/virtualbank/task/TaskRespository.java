package com.virtualbank.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualbank.model.Task;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRepository {
    private final String filePath = "src/main/resources/tasks.json"; // JSON文件路径
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson的ObjectMapper实例

    // 从JSON文件加载所有任务
    public List<Task> findAll() {
        try {
            return objectMapper.readValue(new File(filePath), new TypeReference<List<Task>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 根据ID查找任务
    public Optional<Task> findById(String id) {
        return findAll().stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    // 保存任务列表到JSON文件
    public void save(List<Task> tasks) {
        try {
            objectMapper.writeValue(new File(filePath), tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 添加新任务到列表并保存
    public void add(Task task) {
        List<Task> tasks = findAll();
        tasks.add(task);
        save(tasks);
    }

    // 根据ID更新任务状态并保存
    public void updateStatus(String id, String status) {
        List<Task> tasks = findAll();
        tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .ifPresent(task -> task.setStatus(status));
        save(tasks);
    }

    // 根据ID删除任务并保存
    public void deleteById(String id) {
        List<Task> tasks = findAll();
        tasks.removeIf(task -> task.getId().equals(id));
        save(tasks);
    }

    public void update(Task taskToUpdate) {
        List<Task> tasks = findAll();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(taskToUpdate.getId())) {
                tasks.set(i, taskToUpdate);  // 更新列表中的任务
                save(tasks);                // 保存更新后的列表到文件
                return;
            }
        }
        throw new IllegalArgumentException("Task with id " + taskToUpdate.getId() + " not found");
    }
}
