package com.springbank.bankacc.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BankAccount {
    @Id
    private String id;
    @Column(nullable = false)
    private String accountHolderId;
    @Column(nullable = false)
    private LocalDate creationDate;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;
    @Column(nullable = false)
    private double balance;
    @Column(nullable = false)
    private double creditLimit;
    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();
}
