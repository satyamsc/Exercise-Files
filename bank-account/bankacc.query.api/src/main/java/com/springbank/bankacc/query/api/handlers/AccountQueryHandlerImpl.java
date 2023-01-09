package com.springbank.bankacc.query.api.handlers;

import com.springbank.bankacc.core.models.BankAccount;
import com.springbank.bankacc.query.api.dto.AccountLookupResponse;
import com.springbank.bankacc.query.api.dto.BankAccountDto;
import com.springbank.bankacc.query.api.dto.TransactionDto;
import com.springbank.bankacc.query.api.queries.FindAccountAllTransactionsForGivenDate;
import com.springbank.bankacc.query.api.queries.FindAccountByIdQuery;
import com.springbank.bankacc.query.api.queries.FindAllAccountsQuery;
import com.springbank.bankacc.query.api.repositories.AccountRepository;
import com.springbank.bankacc.query.api.repositories.TransactionRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountQueryHandlerImpl implements AccountQueryHandler {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public AccountQueryHandlerImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountById(FindAccountByIdQuery query) {
        var bankAccount = accountRepository.findById(query.getId());
        if (bankAccount.isPresent()) {
            var account = bankAccount.get();
            BankAccountDto dto = BankAccountDto.builder()
                    .id(account.getId())
                    .accountHolderId(account.getAccountHolderId())
                    .accountType(account.getAccountType())
                    .balance(account.getBalance())
                    .creationDate(account.getCreationDate())
                    .creditLimit(account.getCreditLimit())
                    .build();
            return new AccountLookupResponse("Bank Account Successfully Returned!", dto);
        }
        // if No Accounts Found
        return new AccountLookupResponse("No Bank Account Found for ID - " + query.getId());
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAllAccounts(FindAllAccountsQuery query) {
        List<BankAccount> bankAccounts = accountRepository.findByBalanceLessThan(0.0);
        // if No Accounts Found.
        if (bankAccounts.isEmpty()) {
            return new AccountLookupResponse("No Bank Accounts were Found!");
        }
        var accounts = new ArrayList<BankAccountDto>();
        bankAccounts.forEach(account -> {
            BankAccountDto bankAccount = BankAccountDto.builder()
                    .id(account.getId())
                    .accountHolderId(account.getAccountHolderId())
                    .accountType(account.getAccountType())
                    .creationDate(account.getCreationDate())
                    .creditLimit(account.getCreditLimit())
                    .balance(account.getBalance())
                    .build();
            accounts.add(bankAccount);
        });
        return new AccountLookupResponse("Successfully Returned " + accounts.size() + " Bank Account(s)!", accounts);
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findByAccountAndTransactionDate(FindAccountAllTransactionsForGivenDate query) {
        var accountTransactions = transactionRepository
                .findByTransferFromAndTransactionDate(query.getId(), query.getDate());
        // if No Accounts Found
        if (accountTransactions.isEmpty()) {
            return new AccountLookupResponse("No Bank Accounts were Found!");
        }
        BankAccountDto accounts = BankAccountDto.builder().build();
        List<TransactionDto> transDTOs = new ArrayList<>();
        accountTransactions.forEach(transaction -> {
            TransactionDto dto = TransactionDto.builder()
                    .transactionId(transaction.getTransactionId())
                    .transactionAmount(transaction.getTransactionAmount())
                    .transactionDate(transaction.getTransactionDate())
                    .transactionType(transaction.getTransactionType())
                    .transferTo(transaction.getTransferTo())
                    .transferFrom(transaction.getTransferFrom())
                    .build();
            transDTOs.add(dto);
        });
        var bankAccount = accountTransactions.get(0).getBankAccount();
        accounts.setId(bankAccount.getId());
        accounts.setAccountHolderId(bankAccount.getAccountHolderId());
        accounts.setBalance(bankAccount.getBalance());
        accounts.setCreditLimit(bankAccount.getCreditLimit());
        accounts.setTransactions(transDTOs);
        return new AccountLookupResponse("Successfully Returned " + accounts.getTransactions().size() + " Bank Account Transaction(s)!", accounts);
    }
}
