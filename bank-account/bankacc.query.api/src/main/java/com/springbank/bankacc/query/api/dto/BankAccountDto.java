package com.springbank.bankacc.query.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springbank.bankacc.core.models.AccountType;
import com.springbank.bankacc.core.models.Transaction;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankAccountDto {
    private String id;
    private String accountHolderId;
    private LocalDate creationDate;
    private AccountType accountType;
    private double balance;
    private double creditLimit;
    private List<TransactionDto> transactions;
}
