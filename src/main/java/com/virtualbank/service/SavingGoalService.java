package com.virtualbank.service;

import com.virtualbank.model.AccountManager;
import com.virtualbank.model.SavingGoal;
import com.virtualbank.repository.SavingGoalRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing saving goals.
 * Provides methods to add, update, delete, and retrieve saving goals, as well as get feedback on progress.
 */
public class SavingGoalService {

    private final SavingGoalRepository savingGoalRepository;
    private final AccountManager accountManager;

    /**
     * Constructs a SavingGoalService with the specified SavingGoalRepository and AccountManager.
     *
     * @param savingGoalRepository the repository for saving goals
     * @param accountManager the account manager for managing account balances
     */
    public SavingGoalService(SavingGoalRepository savingGoalRepository, AccountManager accountManager) {
        this.savingGoalRepository = savingGoalRepository;
        this.accountManager = accountManager;
    }

    /**
     * Gets the current account balance.
     *
     * @return the current account balance
     */
    public double getCurrentBalance() {
        return accountManager.getTotalBalance();
    }

    /**
     * Adds a new saving goal.
     *
     * @param childName the name of the child for whom the saving goal is set
     * @param goalName the name of the saving goal
     * @param targetAmount the target amount for the saving goal
     * @return the created SavingGoal object
     */
    public SavingGoal addSavingGoal(String childName, String goalName, double targetAmount) {
        double currentBalance = getCurrentBalance();
        SavingGoal newGoal = new SavingGoal(UUID.randomUUID().toString(), childName, targetAmount, currentBalance, goalName);
        savingGoalRepository.save(newGoal);
        return newGoal;
    }

    /**
     * Updates the target amount of an existing saving goal.
     *
     * @param goalId the ID of the saving goal to update
     * @param targetAmount the new target amount for the saving goal
     * @return the updated SavingGoal object
     * @throws IllegalArgumentException if the saving goal is not found
     */
    public SavingGoal updateSavingGoal(String goalId, double targetAmount) {
        SavingGoal existingGoal = savingGoalRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("Saving goal not found with ID: " + goalId));
        existingGoal.setTargetAmount(targetAmount);
        savingGoalRepository.save(existingGoal);
        return existingGoal;
    }

    /**
     * Retrieves all saving goals.
     *
     * @return a list of all SavingGoal objects
     */
    public List<SavingGoal> getAllSavingGoals() {
        return savingGoalRepository.findAll();
    }

    /**
     * Retrieves all saving goals for a specific child.
     *
     * @param childName the name of the child
     * @return a list of SavingGoal objects for the specified child
     */
    public List<SavingGoal> getSavingGoalsByChildName(String childName) {
        return savingGoalRepository.findAll().stream()
                .filter(goal -> goal.getChildName().equals(childName))
                .collect(Collectors.toList());
    }

    /**
     * Deletes a saving goal by its ID.
     *
     * @param goalId the ID of the saving goal to delete
     */
    public void deleteSavingGoal(String goalId) {
        savingGoalRepository.deleteById(goalId);
    }

    /**
     * Gets feedback message based on the progress of the saving goal.
     *
     * @param goal the saving goal for which feedback is to be generated
     * @return the feedback message
     */
    public String getSavingGoalFeedback(SavingGoal goal) {
        double progress = goal.getCurrentAmount() / goal.getTargetAmount();
        String feedback;
        if (progress >= 1) {
            feedback = "Congratulations! You've reached your savings goal!";
        } else if (progress >= 0.9) {
            feedback = "Great job! You're 90% of the way to your goal!";
        } else if (progress >= 0.75) {
            feedback = "You're three-quarters there! Keep it up!";
        } else if (progress >= 0.5) {
            feedback = "You've saved half of your goal! Well done!";
        } else if (progress >= 0.25) {
            feedback = "You've saved a quarter of your goal! Come on!";
        } else {
            feedback = "Keep saving to reach your goal!";
        }
        return feedback;
    }
}