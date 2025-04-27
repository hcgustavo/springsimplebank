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
        try {
            BankAccount result = bankAccountService.createAccount(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new BankAccountDetailsResponse(result.getNumber(), result.getHolderName(), result.getBalance()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<String> closeBankAccount(@PathVariable String accountNumber) {

        var accountOptional = bankAccountService.findByAccountNumber(accountNumber);

        if(accountOptional.isPresent()) {
            bankAccountService.closeAccount(accountOptional.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> getDetails(@PathVariable String accountNumber) {

        var accountOptional = bankAccountService.findByAccountNumber(accountNumber);

        if(accountOptional.isPresent()) {
            var account = accountOptional.get();
            return ResponseEntity.ok().body(new BankAccountDetailsResponse(account.getNumber(), account.getHolderName(), account.getBalance()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
    }

    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<String> getBalance(@PathVariable String accountNumber) {

        var accountOptional = bankAccountService.findByAccountNumber(accountNumber);

        if(accountOptional.isPresent()) {
            var account = accountOptional.get();
            return ResponseEntity.ok().body(account.getBalance().toString());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
    }

    @GetMapping("/{accountNumber}/transactions")
    public ResponseEntity<?> getTransactions(@PathVariable String accountNumber) throws Exception {

        var accountOptional = bankAccountService.findByAccountNumber(accountNumber);

        if(accountOptional.isPresent()) {
            var account = accountOptional.get();
            
            return ResponseEntity.ok().body(
                account.getTransactions().stream()
                                         .map(t -> new BankTransactionDetailsResponse(t.getId().toString(), accountNumber, t.getType().toString(), t.getAmount(), t.getTimestamp()))
                                         .collect(Collectors.toList())
                );
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
    }
}
