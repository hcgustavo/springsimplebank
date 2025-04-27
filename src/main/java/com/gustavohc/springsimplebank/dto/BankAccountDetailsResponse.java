package com.gustavohc.springsimplebank.dto;

public record BankAccountDetailsResponse(String accountNumber, String holderName, Double balance) {

}
