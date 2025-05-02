package com.gustavohc.springsimplebank.service;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gustavohc.springsimplebank.exception.BankAccountNotFoundException;
import com.gustavohc.springsimplebank.model.BankAccount;
import com.gustavohc.springsimplebank.repository.BankAccountRepository;

import jakarta.validation.Validator;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceTest {

    @Mock BankAccountRepository bankAccountRepository;
    @Mock  Validator validator;
    @InjectMocks BankAccountService bankAccountService;

    @Test
    void findByAccountNumberShouldFindAccount() {
        given(bankAccountRepository.findById("1234567890")).willReturn(Optional.of(new BankAccount("1234567890", "John Doe", 1000.0, null)));
        BankAccount actualAccount = bankAccountService.findByAccountNumber("1234567890");
        assertThat(actualAccount.getNumber()).isEqualTo("1234567890");
    }

    @Test
    void findByAccountNumberShouldThrowExceptionWhenAccountNotFound() {
        given(bankAccountRepository.findById("0000000000")).willReturn(Optional.empty());
        assertThatThrownBy(() -> bankAccountService.findByAccountNumber("0000000000"))
                .isInstanceOf(BankAccountNotFoundException.class);
    }
}
