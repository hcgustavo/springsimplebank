package com.gustavohc.springsimplebank.service;

import org.springframework.stereotype.Service;

import com.gustavohc.springsimplebank.dto.BankAccountCreateRequest;
import com.gustavohc.springsimplebank.exception.BankAccountNotFoundException;
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
