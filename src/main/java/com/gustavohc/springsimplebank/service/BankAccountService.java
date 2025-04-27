package com.gustavohc.springsimplebank.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.gustavohc.springsimplebank.dto.BankAccountCreateRequest;
import com.gustavohc.springsimplebank.model.BankAccount;
import com.gustavohc.springsimplebank.repository.BankAccountRepository;

@Service
public class BankAccountService {

    private BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public BankAccount createAccount(BankAccountCreateRequest request) {
        var bankAccount = new BankAccount();
        bankAccount.setHolderName(request.holderName());
        bankAccount.setBalance(request.balance());
        bankAccount.setNumber(generateAccountNumber());
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
