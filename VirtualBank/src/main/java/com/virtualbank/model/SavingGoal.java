package com.virtualbank.model;

public class SavingGoal {
    private String id;             // 唯一标识符
    private String childId;        // 孩子的账户ID
    private double targetAmount;   // 目标存款金额
    private double currentAmount;  // 当前存款金额
    private String name;           // 存款目标的名称

    public SavingGoal() {
    }

    // 带所有属性的构造函数
    public SavingGoal(String id, String childId, double targetAmount, double currentAmount, String name) {
        this.id = id;
        this.childId = childId;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.name = name;
    }

    // Getter 和 Setter 方法
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // toString方法用于打印和调试
    @Override
    public String toString() {
        return "SavingGoal{" +
                "id='" + id + '\'' +
                ", childId='" + childId + '\'' +
                ", targetAmount=" + targetAmount +
                ", currentAmount=" + currentAmount +
                ", name='" + name + '\'' +
                '}';
    }
}
