package com.virtualbank.service;

import com.virtualbank.model.Task;
import com.virtualbank.repository.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
/**
 * Service class for managing tasks.
 * Provides methods to create, update, delete, and manage tasks and their statuses.
 */
public class TaskService {
    protected TaskRepository taskRepository = new TaskRepository();

    /**
     * Creates a new task and saves it to the repository.
     *
     * @param taskName the name of the task
     * @param description the description of the task
     * @param reward the reward amount for the task
     * @param childName the name of the child who is the target of the task
     * @param startDate the start date of the task
     * @param endDate the end date of the task
     */
    public void createTask(String taskName, String description, double reward, String childName, String startDate, String endDate) {
        Task newTask = new Task(UUID.randomUUID().toString(), taskName, description, reward, childName, startDate, endDate, "not_accepted");
        taskRepository.add(newTask);
    }

    /**
     * Publishes a task by changing its status to "published".
     *
     * @param taskId the ID of the task to publish
     * @throws IllegalStateException if the task status is not "created"
     * @throws IllegalArgumentException if the task is not found
     */
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

    /**
     * Retracts a task by changing its status to "retracted".
     *
     * @param taskId the ID of the task to retract
     * @throws IllegalStateException if the task status is not "published"
     * @throws IllegalArgumentException if the task is not found
     */
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

    /**
     * Gets all tasks.
     *
     * @return a list of all tasks
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Gets a specific task by its ID.
     *
     * @param taskId the ID of the task to retrieve
     * @return the task with the specified ID
     * @throws IllegalArgumentException if the task is not found
     */
    public Task getTaskById(String taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + taskId + " not found"));
    }

    /**
     * Updates the details of a specific task.
     *
     * @param taskId the ID of the task to update
     * @param taskName the new name of the task
     * @param description the new description of the task
     * @param reward the new reward amount for the task
     * @param childName the new name of the child who is the target of the task
     * @param startDate the new start date of the task
     * @param endDate the new end date of the task
     * @throws IllegalArgumentException if the task is not found
     */
    public void updateTask(String taskId, String taskName, String description, double reward, String childName, String startDate, String endDate) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setTaskName(taskName);
            task.setDescription(description);
            task.setReward(reward);
            task.setChildName(childName);
            task.setStartDate(startDate);
            task.setEndDate(endDate);
            taskRepository.update(task);
        } else {
            throw new IllegalArgumentException("Task with id " + taskId + " not found");
        }
    }

    /**
     * Deletes a specific task.
     *
     * @param taskId the ID of the task to delete
     */
    public void deleteTask(String taskId) {
        taskRepository.deleteById(taskId);
    }

    /**
     * Gets tasks with a specific status.
     *
     * @param status the status to filter tasks by
     * @return a list of tasks with the specified status
     */
    public List<Task> getTasksByStatus(String status) {
        return taskRepository.findAll().stream()
                .filter(task -> status.equals(task.getStatus()))
                .collect(Collectors.toList());
    }

    /**
     * Allows a child user to accept a task.
     *
     * @param taskId the ID of the task to accept
     * @throws IllegalStateException if the task status is not "not_accepted" or "published"
     * @throws IllegalArgumentException if the task is not found
     */
    public void acceptTask(String taskId) {
        Task task = getTaskById(taskId);
        if ("not_accepted".equals(task.getStatus()) || "published".equals(task.getStatus())) {
            task.setStatus("ongoing");
            taskRepository.update(task);
        } else {
            throw new IllegalStateException("Task can only be accepted if it is in 'not_accepted' status.");
        }
    }

    /**
     * Allows a child user to submit a task.
     *
     * @param taskId the ID of the task to submit
     * @return the updated task
     * @throws IllegalStateException if the task status is not "ongoing"
     * @throws IllegalArgumentException if the task is not found
     */
    public Task submitTask(String taskId) {
        Task task = getTaskById(taskId);
        if ("ongoing".equals(task.getStatus())) {
            task.setStatus("finished");
            taskRepository.update(task);
        } else {
            throw new IllegalStateException("Task can only be submitted if it is in 'ongoing' status.");
        }
        return task;
    }

    /**
     * Allows a child user to give up a task.
     *
     * @param taskId the ID of the task to give up
     * @throws IllegalStateException if the task status is not "ongoing" or "not_accepted"
     * @throws IllegalArgumentException if the task is not found
     */
    public void giveUpTask(String taskId) {
        Task task = getTaskById(taskId);
        if ("ongoing".equals(task.getStatus()) || "not_accepted".equals(task.getStatus())) {
            task.setStatus("terminated");
            taskRepository.update(task);
        } else {
            throw new IllegalStateException("Task can only be abandoned if it is in 'ongoing' or 'not_accepted' status.");
        }
    }

    /**
     * Allows a parent user to terminate a task.
     *
     * @param taskId the ID of the task to terminate
     * @throws IllegalStateException if the task status is not "ongoing" or "not_accepted"
     * @throws IllegalArgumentException if the task is not found
     */
    public void terminateTask(String taskId){
        Task task = getTaskById(taskId);
        if ("ongoing".equals(task.getStatus()) || "not_accepted".equals(task.getStatus())) {
            task.setStatus("terminated");
            taskRepository.update(task);
        } else {
            throw new IllegalStateException("Task can only be terminated if it is in 'ongoing' or 'not_accepted' status.");
        }
    }
    /**
     * Allows a parent user to confirm a task as completed.
     *
     * @param taskId the ID of the task to confirm
     * @throws IllegalStateException if the task status is not "finished"
     * @throws IllegalArgumentException if the task is not found
     */
    public void confirmTask(String taskId){
        Task task = getTaskById(taskId);
        if ("finished".equals(task.getStatus())) {
            task.setStatus("Confirmed");
            taskRepository.update(task);
        } else {
            throw new IllegalStateException("Task can only be confirmed if it is in 'finished' status.");
        }
    }
}

