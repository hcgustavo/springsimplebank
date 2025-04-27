package com.gustavohc.springsimplebank.controller;

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
import com.gustavohc.springsimplebank.dto.BankAccountCreateResponse;
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
    public ResponseEntity<BankAccountCreateResponse> createBankAccount(@RequestBody BankAccountCreateRequest request) {
        try {
            BankAccount result = bankAccountService.createAccount(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new BankAccountCreateResponse(result.getNumber(), result.getHolderName(), result.getBalance()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<String> closeBankAccount(@PathVariable String accountNumber) {

        var account = bankAccountService.findByAccountNumber(accountNumber);

        if(account.isPresent()) {
            bankAccountService.closeAccount(account.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
