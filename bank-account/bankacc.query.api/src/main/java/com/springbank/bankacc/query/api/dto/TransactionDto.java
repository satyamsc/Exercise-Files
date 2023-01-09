package com.springbank.bankacc.query.api.dto;

import com.springbank.bankacc.core.models.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
@Builder
public class TransactionDto {
    private String transactionId;
    private double transactionAmount;
    private LocalDate transactionDate;
    private String transferTo;
    private String transferFrom;
    private TransactionType transactionType;
}
