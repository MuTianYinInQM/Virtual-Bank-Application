package com.virtualbank.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualbank.model.SavingGoal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository class for managing saving goals.
 * Provides methods to interact with the saving goal data stored in a JSON file.
 */
public class SavingGoalRepository {

    private final File file = new File("src/main/resources/savingGoals.json"); // Path to the JSON file
    private final ObjectMapper objectMapper = new ObjectMapper(); // Instance of Jackson's ObjectMapper

    /**
     * Retrieves all SavingGoal objects from the JSON file.
     *
     * @return a list of all SavingGoal objects
     */
    public List<SavingGoal> findAll() {
        try {
            if (file.exists()) {
                return objectMapper.readValue(file, new TypeReference<List<SavingGoal>>() {});
            } else {
                return new ArrayList<>(); // If file does not exist, return an empty list
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(); // If there is an error reading the file, return an empty list
        }
    }

    /**
     * Finds a SavingGoal object by its ID.
     *
     * @param goalId the ID of the SavingGoal to find
     * @return an Optional containing the SavingGoal if found, or empty if not found
     */
    public Optional<SavingGoal> findById(String goalId) {
        Optional<SavingGoal> result = findAll().stream()
                .filter(goal -> goal.getGoalId().equals(goalId))
                .findFirst();
        System.out.println("findById - Found: " + result.isPresent());  // Log whether the goal was found
        return result;
    }

    /**
     * Saves or updates a SavingGoal object.
     *
     * @param goal the SavingGoal object to save or update
     */
    public void save(SavingGoal goal) {
        List<SavingGoal> goals = findAll();
        int index = -1;  // Initialize index to -1
        for (int i = 0; i < goals.size(); i++) {
            if (goals.get(i).getGoalId().equals(goal.getGoalId())) {
                index = i;  // If a matching goal is found, update the index
            }
        }

        if (index != -1) {
            goals.set(index, goal);  // If found, update the goal
            System.out.println("Updated goal: " + goal);
        } else {
            goals.add(goal);  // If not found, add as a new goal
            System.out.println("Added new goal: " + goal);
        }
        saveToFile(goals);  // Save the modified list of goals to the file
    }

    /**
     * Deletes a SavingGoal object by its ID.
     *
     * @param goalId the ID of the SavingGoal to delete
     */
    public void deleteById(String goalId) {
        List<SavingGoal> goals = findAll();
        goals.removeIf(goal -> goal.getGoalId().equals(goalId));
        saveToFile(goals);
    }

    /**
     * Saves a list of SavingGoal objects to the JSON file.
     *
     * @param goals the list of SavingGoal objects to save
     */
    private void saveToFile(List<SavingGoal> goals) {
        try {
            objectMapper.writeValue(file, goals);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
