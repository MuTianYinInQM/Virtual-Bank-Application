package com.virtualbank.service;

import com.virtualbank.model.SavingGoal;
import com.virtualbank.repository.SavingGoalRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SavingGoalService {

    private final SavingGoalRepository savingGoalRepository;

    public SavingGoalService(SavingGoalRepository savingGoalRepository) {
        this.savingGoalRepository = savingGoalRepository;
    }

    // 添加新的储蓄目标
    public SavingGoal addSavingGoal(String childId, String name, double targetAmount) {
        SavingGoal newGoal = new SavingGoal(UUID.randomUUID().toString(), childId, targetAmount, 0, name);
        savingGoalRepository.save(newGoal);
        return newGoal;
    }

    // 更新储蓄目标的目标金额
    public SavingGoal updateSavingGoal(String goalId, double targetAmount) {
        SavingGoal existingGoal = savingGoalRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("Saving goal not found with ID: " + goalId));
        existingGoal.setTargetAmount(targetAmount);
        savingGoalRepository.save(existingGoal);
        return existingGoal;
    }

    // 为指定的储蓄目标贡献资金
    public SavingGoal contributeToGoal(String goalId, double contribution) {
        SavingGoal goal = savingGoalRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("Saving goal not found with ID: " + goalId));
        double newCurrentAmount = goal.getCurrentAmount() + contribution;
        goal.setCurrentAmount(newCurrentAmount);
        savingGoalRepository.save(goal);
        return goal;
    }

    // 获取所有的储蓄目标
    public List<SavingGoal> getAllSavingGoals() {
        return savingGoalRepository.findAll();
    }

    // 获取特定孩子的所有储蓄目标
    public List<SavingGoal> getSavingGoalsByChildId(String childId) {
        return savingGoalRepository.findAll().stream()
                .filter(goal -> goal.getChildId().equals(childId))
                .collect(Collectors.toList());
    }

    // 删除储蓄目标
    public void deleteSavingGoal(String goalId) {
        savingGoalRepository.deleteById(goalId);
    }

    // 获取储蓄目标的反馈消息
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
