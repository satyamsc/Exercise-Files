package com.springbank.bankacc.core.events;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PaymentInitiatedEvent {
    private String id;
    private double amount;
    private double balance;
    private double creditLimit;
    private String transferTo;
    private String transferFrom;
    private LocalDate transactionDate;
}
