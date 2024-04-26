package com.virtualbank.model;
public class Task {
    private String id;               // 任务的唯一标识符
    private String taskName;             // 任务名称
    private String description;      // 任务描述
    private double reward;           // 奖励金额

    private String childName;        // 任务对象/小孩名字
    private String startDate; // 使用字符串表示时间，如 "2024-04-01 10:00"
    private String endDate;   // 同上
    private String status;           // 任务状态，如："created", "published", "retracted"

    // 无参构造函数
    public Task() {

    }

    // 构造函数
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

    // Getter和Setter方法
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public String getChildName() { return childName; }

    public void setChildName(String childName) { this.childName = childName; }
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        // 可以在这里添加验证逻辑以确保日期格式正确
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        // 同上
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString方法，用于打印任务信息
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
