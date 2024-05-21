package com.virtualbank.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualbank.model.Task;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository class for managing tasks.
 * Provides methods to interact with the task data stored in a JSON file.
 */
public class TaskRepository {
    private final String filePath = "src/main/resources/tasks.json"; // Path to the JSON file
    private final ObjectMapper objectMapper = new ObjectMapper(); // Instance of Jackson's ObjectMapper

    /**
     * Loads all tasks from the JSON file.
     *
     * @return a list of all tasks
     */
    public List<Task> findAll() {
        try {
            return objectMapper.readValue(new File(filePath), new TypeReference<List<Task>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Finds a task by its ID.
     *
     * @param id the ID of the task to find
     * @return an Optional containing the task if found, or empty if not found
     */
    public Optional<Task> findById(String id) {
        return findAll().stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    /**
     * Saves a list of tasks to the JSON file.
     *
     * @param tasks the list of tasks to save
     */
    public void save(List<Task> tasks) {
        try {
            objectMapper.writeValue(new File(filePath), tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new task to the list and saves it.
     *
     * @param task the task to add
     */
    public void add(Task task) {
        List<Task> tasks = findAll();
        tasks.add(task);
        save(tasks);
    }

    /**
     * Updates the status of a task by its ID and saves it.
     *
     * @param id the ID of the task to update
     * @param status the new status of the task
     */
    public void updateStatus(String id, String status) {
        List<Task> tasks = findAll();
        tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .ifPresent(task -> task.setStatus(status));
        save(tasks);
    }

    /**
     * Deletes a task by its ID and saves the changes.
     *
     * @param id the ID of the task to delete
     */
    public void deleteById(String id) {
        List<Task> tasks = findAll();
        tasks.removeIf(task -> task.getId().equals(id));
        save(tasks);
    }

    /**
     * Updates a task and saves the changes.
     *
     * @param taskToUpdate the task with updated details
     * @throws IllegalArgumentException if the task is not found
     */
    public void update(Task taskToUpdate) {
        List<Task> tasks = findAll();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(taskToUpdate.getId())) {
                tasks.set(i, taskToUpdate);  /// Updates the task in the list
                save(tasks);                // Saves the updated list to the file
                return;
            }
        }
        throw new IllegalArgumentException("Task with id " + taskToUpdate.getId() + " not found");
    }
}
