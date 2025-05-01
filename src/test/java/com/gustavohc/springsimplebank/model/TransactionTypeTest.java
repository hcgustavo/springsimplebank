package com.gustavohc.springsimplebank.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TransactionTypeTest {

    @Test
    @DisplayName("Should return correct types for valid strings")
    void validStringShouldReturnValidType() {
        assertThat(TransactionType.fromString("deposit")).isEqualTo(TransactionType.DEPOSIT);
        assertThat(TransactionType.fromString("Deposit")).isEqualTo(TransactionType.DEPOSIT);
        
        assertThat(TransactionType.fromString("withdrawal")).isEqualTo(TransactionType.WITHDRAWAL);
        assertThat(TransactionType.fromString("Withdrawal")).isEqualTo(TransactionType.WITHDRAWAL);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for invalid strings")
    void invalidStringShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> TransactionType.fromString("invalidString"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid TransactionType: invalidString");
    }

}
