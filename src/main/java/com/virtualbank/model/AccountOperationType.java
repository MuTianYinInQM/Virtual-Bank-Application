package com.virtualbank.model;

public enum AccountOperationType {
    CONSUME("CONSUMPTION", "Spending money, can only be spent from the piggy bank, equivalent to money disappearing into the external real world from the virtual software"),
    SAVE("DEPOSIT", "Depositing money, can only deposit money into the piggy bank, equivalent to money magically increasing, from the external real world into the virtual software"),
    INITIAL_SAVE("INITIAL_DEPOSIT", "Initial deposit amount when creating a savings account, by default it is taken from the piggy bank"),
    TRANSFER("TRANSFER", "Transfer between two UUID cards, requires compliance checks"),
    TRANSFER_FROM("TRANSFER_FROM", "Transfer between two UUID cards, compliance check required, from is the provider of the money"),
    TRANSFER_TO("TRANSFER_TO", "Transfer between two UUID cards, compliance check required, to is the receiver of the money"),

    INTEREST("INTEREST_CALCULATION", "Calculate the interest rate for a specific account, need to specify UUID"),
    PRIZE("REWARD", "A special way of depositing money, represents money awarded to the user");

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


