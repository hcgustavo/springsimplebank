package com.gustavohc.springsimplebank.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gustavohc.springsimplebank.exception.BankAccountConstraintViolationException;
import com.gustavohc.springsimplebank.exception.BankAccountNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BankAccountNotFoundException.class)
    private ResponseEntity<String> bankAccountNotFoundHandler(BankAccountNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(BankAccountConstraintViolationException.class)
    private ResponseEntity<String> bankAccountConstraintViolations(BankAccountConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
