package com.virtualbank.model;

/**
 * Represents a saving goal.
 * Contains details of a saving goal such as goal ID, child's account ID, target amount,
 * current amount, and goal name.
 */
public class SavingGoal {
    private String goalId;             // Unique identifier for the saving goal
    private String childName;        // Child's account ID
    private double targetAmount;   // Target saving amount
    private double currentAmount;  // Current saving amount
    private String goalName;           // Name of the saving goal

    /**
     * No-argument constructor.
     */
    public SavingGoal() {
    }

    /**
     * Constructor with all properties.
     *
     * @param goalId        the unique identifier for the saving goal
     * @param childName     the child's account ID
     * @param targetAmount  the target saving amount
     * @param currentAmount the current saving amount
     * @param goalName      the name of the saving goal
     */
    public SavingGoal(String goalId, String childName, double targetAmount, double currentAmount, String goalName) {
        this.goalId = goalId;
        this.childName = childName;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.goalName = goalName;
    }

    /**
     * Gets the unique identifier of the saving goal.
     *
     * @return the unique identifier of the saving goal
     */
    public String getGoalId() {
        return goalId;
    }

    /**
     * Sets the unique identifier of the saving goal.
     *
     * @param goalId the unique identifier to set
     */
    public void setGoalId(String goalId) {
        this.goalId = goalId;
    }

    /**
     * Gets the child's account ID.
     *
     * @return the child's account ID
     */
    public String getChildName() {
        return childName;
    }

    /**
     * Gets the target saving amount.
     *
     * @return the target saving amount
     */
    public double getTargetAmount() {
        return targetAmount;
    }

    /**
     * Sets the target saving amount.
     *
     * @param targetAmount the target saving amount to set
     */
    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    /**
     * Gets the current saving amount.
     *
     * @return the current saving amount
     */
    public double getCurrentAmount() {
        return currentAmount;
    }

    /**
     * Sets the current saving amount.
     *
     * @param currentAmount the current saving amount to set
     */
    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    /**
     * Gets the name of the saving goal.
     *
     * @return the name of the saving goal
     */
    public String getGoalName() {
        return goalName;
    }

    /**
     * Sets the name of the saving goal.
     *
     * @param goalName the name of the saving goal to set
     */
    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    /**
     * Returns a string representation of the saving goal object.
     *
     * @return a string representation of the saving goal object
     */
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
