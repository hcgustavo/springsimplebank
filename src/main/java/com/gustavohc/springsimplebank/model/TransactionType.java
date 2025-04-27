package com.gustavohc.springsimplebank.model;

import java.util.Arrays;

public enum TransactionType {
    DEPOSIT("deposit"),
    WITHDRAWAL("withdrawal");

    private String type;

    private TransactionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static TransactionType fromString(String text) {
        return Arrays.stream(TransactionType.values())
                     .filter(t -> t.type.equalsIgnoreCase(text))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Invalid TransactionType: " + text));
    }
}
