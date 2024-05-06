package com.virtualbank.model;

public enum OperationType {
    CONSUME("消费", "花钱，只能从存钱罐里面花钱，相当于钱凭空的消失了，从虚拟的软件转入外部的现实世界中"),
    SAVE("存款", "存钱，只能将钱存到存钱罐中，相当于钱凭空的增加了，从外部的现实世界中转入虚拟的软件"),
//    TRANSFER("转账", "两个UUID的卡之间的转账，需要检测是否合规"), // 只针对单个卡
    TRANSFER_FROM("转账", "两个UUID的卡之间的转账，需要检测是否合规，from 是提供钱的那一方"),
    TRANSFER_TO("转账", "两个UUID的卡之间的转账，需要检测是否合规,to 是接收钱的那一方"),

    INTEREST("计算利率", "计算特定账户的利率，需要指明UUID"),
    PRIZE("奖励", "特殊的存钱方式，表征奖励给用户的钱");

    private final String description;
    private final String name;

    OperationType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
