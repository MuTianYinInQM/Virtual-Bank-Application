package com.virtualbank.model;

/**
 * Represents a task model.
 * Contains details of a task such as task name, description, reward amount,
 * task target, start date, end date, and status.
 */
public class Task {
    private String id;               // Unique identifier for the task
    private String taskName;         // Name of the task
    private String description;      // Description of the task
    private double reward;           // Reward amount

    private String childName;        // Name of the child who is the target of the task
    private String startDate;        // Start date of the task in the format "YYYY-MM-DD HH:MM"
    private String endDate;          // End date of the task in the same format
    private String status;           // Status of the task

    /**
     * No-argument constructor.
     */
    public Task() {
    }

    /**
     * Constructor with arguments to initialize the task properties.
     *
     * @param id the unique identifier for the task
     * @param taskName the name of the task
     * @param description the description of the task
     * @param reward the reward amount for the task
     * @param childName the name of the child who is the target of the task
     * @param startDate the start date of the task
     * @param endDate the end date of the task
     * @param status the status of the task
     */
    public Task(String id, String taskName, String description, double reward, String childName,
                String startDate, String endDate, String status) {
        this.id = id;
        this.taskName = taskName;
        this.description = description;
        this.reward = reward;
        this.childName = childName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    /**
     * Gets the unique identifier of the task.
     *
     * @return the unique identifier of the task
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the task.
     *
     * @param id the unique identifier to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the task.
     *
     * @return the name of the task
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Sets the name of the task.
     *
     * @param taskName the name to set
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Gets the description of the task.
     *
     * @return the description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the reward amount for the task.
     *
     * @return the reward amount for the task
     */
    public double getReward() {
        return reward;
    }

    /**
     * Sets the reward amount for the task.
     *
     * @param reward the reward amount to set
     */
    public void setReward(double reward) {
        this.reward = reward;
    }

    /**
     * Gets the name of the child who is the target of the task.
     *
     * @return the name of the child
     */
    public String getChildName() {
        return childName;
    }

    /**
     * Sets the name of the child who is the target of the task.
     *
     * @param childName the name to set
     */
    public void setChildName(String childName) {
        this.childName = childName;
    }

    /**
     * Gets the start date of the task.
     *
     * @return the start date of the task
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the task.
     *
     * @param startDate the start date to set
     */
    public void setStartDate(String startDate) {
        // Validation logic can be added here to ensure correct date format
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the task.
     *
     * @return the end date of the task
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the task.
     *
     * @param endDate the end date to set
     */
    public void setEndDate(String endDate) {
        // Validation logic can be added here to ensure correct date format
        this.endDate = endDate;
    }

    /**
     * Gets the status of the task.
     *
     * @return the status of the task
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the task.
     *
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the task object.
     *
     * @return a string representation of the task object
     */
    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", reward=" + reward +
                ", childName='" + childName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                '}';
    }
}
