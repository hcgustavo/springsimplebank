package com.gustavohc.springsimplebank.exception;

public class BankAccountNotFoundException extends RuntimeException {
    
    public BankAccountNotFoundException() {
        super("Account not found");
    }

    public BankAccountNotFoundException(String message) {
        super(message);
    }
}
