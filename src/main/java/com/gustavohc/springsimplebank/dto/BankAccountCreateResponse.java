package com.gustavohc.springsimplebank.dto;

public record BankAccountCreateResponse(String accountNumber, String holderName, Double balance) {

}
