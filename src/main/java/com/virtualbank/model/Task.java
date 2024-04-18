package com.virtualbank.model;
public class Task {
    private String id;               // 任务的唯一标识符
    private String name;             // 任务名称
    private String description;      // 任务描述
    private double reward;           // 奖励金额
    private String startDate; // 使用字符串表示时间，如 "2024-04-01 10:00"
    private String endDate;   // 同上
    private String status;           // 任务状态，如："created", "published", "retracted"

    // 构造函数
    public Task(String id, String name, String description, double reward,
                String startDate, String endDate, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.reward = reward;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", reward=" + reward +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                '}';
    }
}
