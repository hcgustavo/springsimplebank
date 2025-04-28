package com.gustavohc.springsimplebank.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.gustavohc.springsimplebank.dto.BankTransactionCreateRequest;
import com.gustavohc.springsimplebank.exception.BankAccountInvalidBalanceException;
import com.gustavohc.springsimplebank.exception.BankAccountNotFoundException;
import com.gustavohc.springsimplebank.exception.BankTransactionInvalidAmountException;
import com.gustavohc.springsimplebank.model.BankAccount;
import com.gustavohc.springsimplebank.model.BankTransaction;
import com.gustavohc.springsimplebank.model.TransactionType;
import com.gustavohc.springsimplebank.repository.BankAccountRepository;
import com.gustavohc.springsimplebank.repository.BankTransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class BankTransactionService {

    private BankTransactionRepository bankTransactionRepository;
    private BankAccountRepository bankAccountRepository;

    public BankTransactionService(BankTransactionRepository bankTransactionRepository, BankAccountRepository bankAccountRepository) {
        this.bankTransactionRepository = bankTransactionRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional
    public BankTransaction createTransaction(BankTransactionCreateRequest request, TransactionType type) {
        var bankAccountOptional = bankAccountRepository.findById(request.accountNumber());
        BankAccount bankAccount = null;

        if(!bankAccountOptional.isPresent()) {
            throw new BankAccountNotFoundException("Account not found: " + request.accountNumber());
        }

        if(request.amount() < 0) {
            throw new BankTransactionInvalidAmountException("Amount for bank transactions should be positive. Received: " + request.amount());
        }

        bankAccount = bankAccountOptional.get();

        var bankTransaction = new BankTransaction();
        bankTransaction.setBankAccount(bankAccount);
        bankTransaction.setAmount(request.amount());
        bankTransaction.setType(type);
        bankTransaction.setTimestamp(LocalDateTime.now());

        if(bankTransaction.getType().equals(TransactionType.WITHDRAWAL)) {
            bankAccount.setBalance(bankAccount.getBalance() - bankTransaction.getAmount());
        }
        else if(bankTransaction.getType().equals(TransactionType.DEPOSIT)) {
            bankAccount.setBalance(bankAccount.getBalance() + bankTransaction.getAmount());
        }

        if(bankAccount.getBalance() < 0) {
            throw new BankAccountInvalidBalanceException("Operation not allowed because account balance would be negative");
        }

        bankAccountRepository.save(bankAccount);

        return bankTransactionRepository.save(bankTransaction);
    }

}
