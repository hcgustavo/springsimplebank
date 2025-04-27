package com.gustavohc.springsimplebank.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gustavohc.springsimplebank.dto.BankAccountCreateRequest;
import com.gustavohc.springsimplebank.dto.BankAccountDetailsResponse;
import com.gustavohc.springsimplebank.dto.BankTransactionDetailsResponse;
import com.gustavohc.springsimplebank.model.BankAccount;
import com.gustavohc.springsimplebank.service.BankAccountService;

@RestController
@RequestMapping("/accounts")
public class BankAccountController {

    private BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping
    public ResponseEntity<BankAccountDetailsResponse> createBankAccount(@RequestBody BankAccountCreateRequest request) {

        BankAccount result = bankAccountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BankAccountDetailsResponse(result.getNumber(), result.getHolderName(), result.getBalance()));
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<String> closeBankAccount(@PathVariable String accountNumber) {

        bankAccountService.closeAccount(accountNumber);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccountDetailsResponse> getDetails(@PathVariable String accountNumber) {

        var bankAccount = bankAccountService.findByAccountNumber(accountNumber);
        return ResponseEntity.ok().body(new BankAccountDetailsResponse(bankAccount.getNumber(), bankAccount.getHolderName(), bankAccount.getBalance()));
    }

    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<String> getBalance(@PathVariable String accountNumber) {

        var bankAccount = bankAccountService.findByAccountNumber(accountNumber);
        return ResponseEntity.ok().body(bankAccount.getBalance().toString());
    }

    @GetMapping("/{accountNumber}/transactions")
    public ResponseEntity<List<BankTransactionDetailsResponse>> getTransactions(@PathVariable String accountNumber) {

        var bankAccount = bankAccountService.findByAccountNumber(accountNumber);
        return ResponseEntity.ok().body(
            bankAccount.getTransactions().stream()
                                         .map(t -> new BankTransactionDetailsResponse(t.getId().toString(), accountNumber, t.getType().toString(), t.getAmount(), t.getTimestamp()))
                                         .collect(Collectors.toList())
                );
    }
}
