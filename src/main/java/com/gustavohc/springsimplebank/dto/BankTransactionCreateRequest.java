package com.gustavohc.springsimplebank.dto;

public record BankTransactionCreateRequest(String accountNumber, Double amount) {

}
