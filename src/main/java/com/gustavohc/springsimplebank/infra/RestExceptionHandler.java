package com.gustavohc.springsimplebank.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gustavohc.springsimplebank.exception.BankAccountConstraintViolationException;
import com.gustavohc.springsimplebank.exception.BankAccountInvalidBalanceException;
import com.gustavohc.springsimplebank.exception.BankAccountNotFoundException;
import com.gustavohc.springsimplebank.exception.BankTransactionInvalidAmountException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BankAccountNotFoundException.class)
    private ResponseEntity<String> bankAccountNotFoundExceptionHandler(BankAccountNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(BankAccountConstraintViolationException.class)
    private ResponseEntity<String> bankAccountConstraintViolationExceptionHandler(BankAccountConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(BankAccountInvalidBalanceException.class)
    private ResponseEntity<String> bankAccountInvalidBalanceExceptionHandler(BankAccountInvalidBalanceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(BankTransactionInvalidAmountException.class)
    private ResponseEntity<String> bankTransactionInvalidAmountExceptionHandler(BankTransactionInvalidAmountException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<String> runtimeExceptionHandler(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
