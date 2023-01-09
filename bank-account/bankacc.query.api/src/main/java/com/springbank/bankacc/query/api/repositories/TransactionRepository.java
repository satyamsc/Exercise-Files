package com.springbank.bankacc.query.api.repositories;

import com.springbank.bankacc.core.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByTransferFromAndTransactionDate(String fromAccount, LocalDate transactionDate);
}
