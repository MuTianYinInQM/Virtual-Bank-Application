package com.virtualbank.model;

/**
 * Enum representing the types of account operations.
 */
public enum AccountOperationType {
    CONSUME("CONSUME", "Spending money"),
    SAVE("SAVE", "Depositing money"),
    INITIAL_SAVE("INITIAL_SAVE", "Initial deposit amount when creating a savings account"),
    TRANSFER("TRANSFER", "Transfer between two cards"),
    TRANSFER_FROM("TRANSFER_FROM", "Transfer from another card"),
    TRANSFER_TO("TRANSFER_TO", "Transfer to another card"),

    INTEREST("INTEREST", "Calculate the interest rate"),
    PRIZE("PRIZE", "Get reward from tasks"),
    ;

    private final String description;
    private final String name;

    /**
     * Constructor for AccountOperationType enum.
     *
     * @param name        the name of the operation
     * @param description the description of the operation
     */
    AccountOperationType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Get the description of the operation.
     *
     * @return the description of the operation
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the name of the operation.
     *
     * @return the name of the operation
     */
    public String getName() {
        return name;
    }
}


