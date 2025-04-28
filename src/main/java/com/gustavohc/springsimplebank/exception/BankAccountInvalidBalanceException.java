package com.gustavohc.springsimplebank.exception;

public class BankAccountInvalidBalanceException extends RuntimeException {

    public BankAccountInvalidBalanceException() {
        super("Invalid account balance");
    }

    public BankAccountInvalidBalanceException(String message) {
        super(message);
    }

}
