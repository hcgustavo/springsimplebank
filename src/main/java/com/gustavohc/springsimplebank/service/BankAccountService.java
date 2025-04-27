package com.gustavohc.springsimplebank.service;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.gustavohc.springsimplebank.dto.BankAccountCreateRequest;
import com.gustavohc.springsimplebank.model.BankAccount;
import com.gustavohc.springsimplebank.repository.BankAccountRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
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
        bankAccount.setNumber(generateAccountNumber());

        Set<ConstraintViolation<BankAccount>> violations = validator.validate(bankAccount);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        return bankAccountRepository.save(bankAccount);
    }

    public void closeAccount(BankAccount account) {
        bankAccountRepository.delete(account);
    }

    public Optional<BankAccount> findByAccountNumber(String accountNumber) {
        return bankAccountRepository.findById(accountNumber);
    }

    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < 8; i++) {
            result.append(random.nextInt(10));
        }

        return result.toString();
    }

}
