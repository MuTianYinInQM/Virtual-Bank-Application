package com.virtualbank.task;

import com.virtualbank.model.Task;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class TaskService {
    protected TaskRepository taskRepository = new TaskRepository();

    // 创建任务并保存到存储中
    public void createTask(String name, String description, double reward, String startDate, String endDate) {
        Task newTask = new Task(UUID.randomUUID().toString(), name, description, reward, startDate, endDate, "created");
        taskRepository.add(newTask);
    }

    // 根据任务ID发布任务，改变任务状态为"published"
    public void publishTask(String taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            if ("created".equals(task.getStatus())) {
                task.setStatus("published");
                taskRepository.update(task);
            } else {
                throw new IllegalStateException("Only tasks with 'created' status can be published.");
            }
        } else {
            throw new IllegalArgumentException("Task with id " + taskId + " not found");
        }
    }

    // 根据任务ID撤回任务，改变任务状态为"retracted"
    public void retractTask(String taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            if ("published".equals(task.getStatus())) {
                task.setStatus("retracted");
                taskRepository.update(task);
            } else {
                throw new IllegalStateException("Only tasks with 'published' status can be retracted.");
            }
        } else {
            throw new IllegalArgumentException("Task with id " + taskId + " not found");
        }
    }

    // 获取所有任务
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // 根据任务ID获取特定任务
    public Task getTaskById(String taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + taskId + " not found"));
    }

    // 更新任务信息
    public void updateTask(String taskId, String name, String description, double reward, String startDate, String endDate) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setName(name);
            task.setDescription(description);
            task.setReward(reward);
            task.setStartDate(startDate);
            task.setEndDate(endDate);
            taskRepository.update(task);
        } else {
            throw new IllegalArgumentException("Task with id " + taskId + " not found");
        }
    }

    // 删除任务
    public void deleteTask(String taskId) {
        taskRepository.deleteById(taskId);
    }

    // 获取特定状态的任务
    public List<Task> getTasksByStatus(String status) {
        return taskRepository.findAll().stream()
                .filter(task -> status.equals(task.getStatus()))
                .collect(Collectors.toList());
    }

    // 儿童用户接受任务
    public void acceptTask(String taskId) {
        Task task = getTaskById(taskId);
        if ("created".equals(task.getStatus()) || "published".equals(task.getStatus())) {
            task.setStatus("accepted");
            taskRepository.update(task);
        } else {
            throw new IllegalStateException("Task can only be accepted if it is in 'created' or 'published' status.");
        }
    }

    // 儿童用户提交任务
    public void submitTask(String taskId) {
        Task task = getTaskById(taskId);
        if ("accepted".equals(task.getStatus())) {
            task.setStatus("submitted");
            taskRepository.update(task);
        } else {
            throw new IllegalStateException("Task can only be submitted if it is in 'accepted' status.");
        }
    }
}
