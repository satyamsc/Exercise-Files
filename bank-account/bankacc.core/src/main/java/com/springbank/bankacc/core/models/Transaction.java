package com.springbank.bankacc.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Transaction {
    @Id
    private String transactionId;
    @Column(nullable = false)
    private double transactionAmount;
    @Column(nullable = false)
    private LocalDate transactionDate;
    @Column(nullable = false)
    private String transferTo;
    @Column(nullable = false)
    private String transferFrom;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;
    @ManyToOne
    private BankAccount bankAccount;
}
