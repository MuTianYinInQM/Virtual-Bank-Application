package com.virtualbank.model;

public class SavingGoal {
    private String goalId;             // 唯一标识符
    private String childName;        // 孩子的账户ID
    private double targetAmount;   // 目标存款金额
    private double currentAmount;  // 当前存款金额
    private String goalName;           // 存款目标的名称

    public SavingGoal() {
    }

    // 带所有属性的构造函数
    public SavingGoal(String goalId, String childName, double targetAmount, double currentAmount, String goalName) {
        this.goalId = goalId;
        this.childName = childName;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.goalName = goalName;
    }

    // Getter 和 Setter 方法
    public String getGoalId() {
        return goalId;
    }

    public void setGoalId(String goalId) {
        this.goalId = goalId;
    }

    public String getChildName() {
        return childName;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    // toString方法用于打印和调试
    @Override
    public String toString() {
        return "SavingGoal{" +
                "goalId='" + goalId + '\'' +
                ", childName='" + childName + '\'' +
                ", targetAmount=" + targetAmount +
                ", currentAmount=" + currentAmount +
                ", goalName='" + goalName + '\'' +
                '}';
    }
}
