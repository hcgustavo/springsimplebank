package com.gustavohc.springsimplebank.dto;

import java.time.LocalDateTime;

public record BankTransactionDetailsResponse(String transactionId, String accountNumber, String type, Double amount, LocalDateTime operationDate) {

}
