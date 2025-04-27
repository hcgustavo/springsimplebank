package com.gustavohc.springsimplebank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gustavohc.springsimplebank.dto.BankTransactionCreateRequest;
import com.gustavohc.springsimplebank.dto.BankTransactionDetailsResponse;
import com.gustavohc.springsimplebank.model.TransactionType;
import com.gustavohc.springsimplebank.service.BankTransactionService;

@RestController
@RequestMapping("/transactions")
public class BankTransactionController {

    private BankTransactionService bankTransactionService;

    public BankTransactionController(BankTransactionService bankTransactionService) {
        this.bankTransactionService = bankTransactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> createDeposit(@RequestBody BankTransactionCreateRequest request) {
        try {
            var bankTransaction = bankTransactionService.createTransaction(request, TransactionType.DEPOSIT);
            return ResponseEntity.status(HttpStatus.CREATED).body(new BankTransactionDetailsResponse(bankTransaction.getId().toString(), request.accountNumber(), TransactionType.DEPOSIT.toString(), bankTransaction.getBankAccount().getBalance(), bankTransaction.getTimestamp()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> createWithdraw(@RequestBody BankTransactionCreateRequest request) {
        try {
            var bankTransaction = bankTransactionService.createTransaction(request, TransactionType.WITHDRAWAL);
            return ResponseEntity.status(HttpStatus.CREATED).body(new BankTransactionDetailsResponse(bankTransaction.getId().toString(), request.accountNumber(), TransactionType.WITHDRAWAL.toString(), bankTransaction.getBankAccount().getBalance(), bankTransaction.getTimestamp()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
