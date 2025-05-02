package com.gustavohc.springsimplebank.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record BankAccountCreateRequest(@NotBlank(message = "Account holder name cannot be empty") String holderName, @Min(value = 0, message = "Balance cannot be negative") Double balance) {

}
