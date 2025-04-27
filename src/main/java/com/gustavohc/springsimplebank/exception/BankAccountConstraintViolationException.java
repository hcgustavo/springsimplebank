package com.gustavohc.springsimplebank.exception;

public class BankAccountConstraintViolationException extends RuntimeException {

    public BankAccountConstraintViolationException() {
        super("Could not create account due to constraint violations");
    }

    public BankAccountConstraintViolationException(String message) {
        super(message);
    }

}
