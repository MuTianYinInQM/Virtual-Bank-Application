package com.virtualbank.model;

public enum AccountOperationType {
    CONSUME("CONSUMPTION", "Spending money"),
    SAVE("DEPOSIT", "Depositing money"),
    INITIAL_SAVE("INITIAL DEPOSIT", "Initial deposit amount when creating a savings account"),
    TRANSFER("TRANSFER", "Transfer between two cards"),
    TRANSFER_FROM("TRANSFER", "Transfer from another card"),
    TRANSFER_TO("TRANSFER", "Transfer to another card"),

    INTEREST("INTEREST CALCULATION", "Calculate the interest rate"),
    PRIZE("REWARD", "Get reward from tasks"),;

    private final String description;
    private final String name;

    AccountOperationType(String name, String description) {
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


