package com.gustavohc.springsimplebank.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.gustavohc.springsimplebank.dto.BankAccountCreateRequest;
import com.gustavohc.springsimplebank.exception.BankAccountConstraintViolationException;
import com.gustavohc.springsimplebank.exception.BankAccountNotFoundException;
import com.gustavohc.springsimplebank.model.BankAccount;
import com.gustavohc.springsimplebank.repository.BankAccountRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class BankAccountService {

    private BankAccountRepository bankAccountRepository;

    private Validator validator;

    public BankAccountService(BankAccountRepository bankAccountRepository, Validator validator) {
        this.bankAccountRepository = bankAccountRepository;
        this.validator = validator;
    }

    public BankAccount createAccount(BankAccountCreateRequest request) {
        var bankAccount = new BankAccount();
        bankAccount.setHolderName(request.holderName());
        bankAccount.setBalance(request.balance());

        Set<ConstraintViolation<BankAccount>> violations = validator.validate(bankAccount);
        if (!violations.isEmpty()) {
            List<String> violationsMessages = violations.stream().map(v -> v.getMessage()).toList();
            throw new BankAccountConstraintViolationException("Could not create account due to constraint violations: " + violationsMessages.toString());
        }

        return bankAccountRepository.save(bankAccount);
    }

    public void closeAccount(String accountNumber) {
        var bankAccount = findByAccountNumber(accountNumber);
        bankAccountRepository.delete(bankAccount);
    }

    public BankAccount findByAccountNumber(String accountNumber) {
        var bankAccount = bankAccountRepository.findById(accountNumber);
        if(bankAccount.isPresent()) {
            return bankAccount.get();
        }

        throw new BankAccountNotFoundException("Account not found: " + accountNumber);
    }

}
