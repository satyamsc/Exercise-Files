package com.springbank.bankacc.query.api.handlers;

import com.springbank.bankacc.core.events.AccountOpenedEvent;
import com.springbank.bankacc.core.events.PaymentInitiatedEvent;
import com.springbank.bankacc.core.models.BankAccount;
import com.springbank.bankacc.core.models.Transaction;
import com.springbank.bankacc.core.models.TransactionType;
import com.springbank.bankacc.query.api.repositories.AccountRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class AccountEventHandlerImpl implements AccountEventHandler {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountEventHandlerImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @EventHandler
    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolderId(event.getAccountHolderId())
                .creationDate(event.getCreationDate())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .creditLimit(event.getCreditLimit())
                .build();
        bankAccount.setTransactions(List.of(Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .transactionDate(event.getCreationDate())
                .transactionAmount(event.getOpeningBalance())
                .transactionType(TransactionType.CREDIT)
                .transferTo(event.getId())
                .transferFrom(event.getId())
                .bankAccount(bankAccount)
                .build()));
        accountRepository.save(bankAccount);
    }

    @EventHandler
    @Override
    @Transactional
    public void on(PaymentInitiatedEvent event) {
        var bankAccount = accountRepository.findById(event.getId());
       BankAccount bankAcc= bankAccount.orElseThrow();
        bankAcc.setBalance(event.getBalance());
        bankAcc.setCreditLimit(event.getCreditLimit());
        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .transactionDate(event.getTransactionDate())
                .transactionAmount(event.getAmount())
                .transferTo(event.getTransferTo())
                .transferFrom(event.getId())
                .transactionType(TransactionType.DEBIT)
                .bankAccount(bankAcc)
                .transactionDate(LocalDate.now())
                .build();
        bankAcc.getTransactions().add(transaction);
        var toBankAccountOpt = accountRepository.findById(event.getTransferTo());
        BankAccount toBankAccount= toBankAccountOpt.orElseThrow();
        toBankAccount.setBalance(toBankAccount.getBalance() + event.getAmount() );
        Transaction toAccTransaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .transactionDate(event.getTransactionDate())
                .transactionAmount(event.getAmount())
                .transferTo(event.getTransferTo())
                .transferFrom(event.getId())
                .transactionType(TransactionType.CREDIT)
                .bankAccount(toBankAccount)
                .transactionDate(LocalDate.now())
                .build();
        toBankAccount.getTransactions().add(toAccTransaction);
        accountRepository.save(bankAcc);
        accountRepository.save(toBankAccount);
    }
}
