package com.gustavohc.springsimplebank.service;

import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gustavohc.springsimplebank.dto.BankAccountCreateRequest;
import com.gustavohc.springsimplebank.exception.BankAccountNotFoundException;
import com.gustavohc.springsimplebank.model.BankAccount;
import com.gustavohc.springsimplebank.repository.BankAccountRepository;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceTest {

    @Mock BankAccountRepository bankAccountRepository;
    @InjectMocks BankAccountService bankAccountService;
    @Captor ArgumentCaptor<BankAccount> bankAccountArgumentCaptor;

    @Nested
    class FindByAccountNumber {
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

    @Nested
    class CloseAccount {
        @Test
        void closeAccountShouldDeleteAccount() {
            String accountNumber = "1234567890";
            BankAccount bankAccount = new BankAccount(accountNumber, "John Doe", 1000.0, null);
            given(bankAccountRepository.findById(accountNumber)).willReturn(Optional.of(bankAccount));
            
            bankAccountService.closeAccount(accountNumber);

            verify(bankAccountRepository, times(1)).findById(accountNumber);
            verify(bankAccountRepository, times(1)).delete(bankAccount);
        }

        @Test
        void closeAccountShouldThrowExceptionWhenAccountNotFound() {
            given(bankAccountRepository.findById("0000000000")).willReturn(Optional.empty());
            assertThatThrownBy(() -> bankAccountService.closeAccount("0000000000"))
                    .isInstanceOf(BankAccountNotFoundException.class);
        }
    }

    @Nested
    class CreateAccount {
        @Test
        void createAccountShouldSaveAccount() {
            BankAccountCreateRequest request = new BankAccountCreateRequest("John Doe", 1000.0);

            bankAccountService.createAccount(request);

            verify(bankAccountRepository).save(bankAccountArgumentCaptor.capture());

            BankAccount savedAccount = bankAccountArgumentCaptor.getValue();
            assertThat(savedAccount.getHolderName()).isEqualTo(request.holderName());
        }
    }

}
