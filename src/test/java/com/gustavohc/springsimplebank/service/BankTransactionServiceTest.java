package com.gustavohc.springsimplebank.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gustavohc.springsimplebank.dto.BankTransactionCreateRequest;
import com.gustavohc.springsimplebank.exception.BankAccountInvalidBalanceException;
import com.gustavohc.springsimplebank.exception.BankAccountNotFoundException;
import com.gustavohc.springsimplebank.exception.BankTransactionInvalidAmountException;
import com.gustavohc.springsimplebank.model.BankAccount;
import com.gustavohc.springsimplebank.model.TransactionType;
import com.gustavohc.springsimplebank.repository.BankAccountRepository;
import com.gustavohc.springsimplebank.repository.BankTransactionRepository;

@ExtendWith(MockitoExtension.class)
public class BankTransactionServiceTest {
    @Mock BankTransactionRepository bankTransactionRepository;
    @Mock BankAccountRepository bankAccountRepository;
    @InjectMocks BankTransactionService bankTransactionService;

    @Test
    void createTransactionWillThrownAccountNotFoundException() {
        BankTransactionCreateRequest request = new BankTransactionCreateRequest("1234567890", 100.0);
        given(bankAccountRepository.findById(request.accountNumber())).willReturn(Optional.empty());

        assertThatThrownBy(() -> bankTransactionService.createTransaction(request, TransactionType.WITHDRAWAL))
                .isInstanceOf(BankAccountNotFoundException.class);
    }

    @Test
    void createTransactionWillThrownInvalidAmountException() {
        BankTransactionCreateRequest request = new BankTransactionCreateRequest("1234567890", -100.0);
        given(bankAccountRepository.findById(request.accountNumber())).willReturn(Optional.of(new BankAccount(request.accountNumber(), "John Doe", 1000.0, null)));

        assertThatThrownBy(() -> bankTransactionService.createTransaction(request, TransactionType.WITHDRAWAL))
                .isInstanceOf(BankTransactionInvalidAmountException.class);
    }

    @Test
    void createWithdrawalTransactionWillThrownInvalidBalanceException() {
        BankTransactionCreateRequest request = new BankTransactionCreateRequest("1234567890", 2000.0);
        BankAccount bankAccount = new BankAccount(request.accountNumber(), "John Doe", 1000.0, null);
        given(bankAccountRepository.findById(request.accountNumber())).willReturn(Optional.of(bankAccount));

        assertThatThrownBy(() -> bankTransactionService.createTransaction(request, TransactionType.WITHDRAWAL))
                .isInstanceOf(BankAccountInvalidBalanceException.class);
    }

    @Test
    void createWithdrawalTransactionWillBeSuccessful() {
        BankTransactionCreateRequest request = new BankTransactionCreateRequest("1234567890", 100.0);
        BankAccount bankAccount = new BankAccount(request.accountNumber(), "John Doe", 1000.0, null);
        given(bankAccountRepository.findById(request.accountNumber())).willReturn(Optional.of(bankAccount));

        bankTransactionService.createTransaction(request, TransactionType.WITHDRAWAL);

        assertThat(bankAccount.getBalance()).isEqualTo(900.0);
    }
    
    @Test
    void createDepositTransactionWillBeSuccessful() {
        BankTransactionCreateRequest request = new BankTransactionCreateRequest("1234567890", 100.0);
        BankAccount bankAccount = new BankAccount(request.accountNumber(), "John Doe", 1000.0, null);
        given(bankAccountRepository.findById(request.accountNumber())).willReturn(Optional.of(bankAccount));

        bankTransactionService.createTransaction(request, TransactionType.DEPOSIT);

        assertThat(bankAccount.getBalance()).isEqualTo(1100.0);
    }
}
