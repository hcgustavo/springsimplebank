package com.gustavohc.springsimplebank.exception;

public class BankTransactionInvalidAmountException extends RuntimeException {

    public BankTransactionInvalidAmountException() {
        super("Amount for transaction not valid");
    }

    public BankTransactionInvalidAmountException(String message) {
        super(message);
    }
}
