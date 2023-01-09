package com.springbank.bankacc.query.api.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.springbank.bankacc.core.models.AccountType;
import com.springbank.bankacc.core.models.BankAccount;
import com.springbank.bankacc.core.models.Transaction;
import com.springbank.bankacc.core.models.TransactionType;
import com.springbank.bankacc.query.api.dto.AccountLookupResponse;
import com.springbank.bankacc.query.api.queries.FindAccountAllTransactionsForGivenDate;
import com.springbank.bankacc.query.api.queries.FindAccountByIdQuery;
import com.springbank.bankacc.query.api.queries.FindAllAccountsQuery;
import com.springbank.bankacc.query.api.repositories.AccountRepository;
import com.springbank.bankacc.query.api.repositories.TransactionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AccountQueryHandlerImpl.class})
@ExtendWith(SpringExtension.class)
class AccountQueryHandlerImplTest {
    @Autowired
    private AccountQueryHandlerImpl accountQueryHandlerImpl;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    /**
     * Method under test: {@link AccountQueryHandlerImpl#findAccountById(FindAccountByIdQuery)}
     */
    @Test
    void testFindAccountById() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountHolderId("42");
        bankAccount.setAccountType(AccountType.SAVINGS);
        bankAccount.setBalance(10.0d);
        bankAccount.setCreationDate(LocalDate.ofEpochDay(1L));
        bankAccount.setCreditLimit(10.0d);
        bankAccount.setId("42");
        bankAccount.setTransactions(new ArrayList<>());
        Optional<BankAccount> ofResult = Optional.of(bankAccount);
        when(accountRepository.findById((String) any())).thenReturn(ofResult);
        AccountLookupResponse actualFindAccountByIdResult = accountQueryHandlerImpl
                .findAccountById(new FindAccountByIdQuery("42"));
        assertEquals(1, actualFindAccountByIdResult.getAccounts().size());
        assertEquals("Bank Account Successfully Returned!", actualFindAccountByIdResult.getMessage());
        verify(accountRepository).findById((String) any());
    }

    /**
     * Method under test: {@link AccountQueryHandlerImpl#findAccountById(FindAccountByIdQuery)}
     */
    @Test
    void testFindAccountById2() {
        when(accountRepository.findById((String) any())).thenReturn(Optional.empty());
        assertEquals("No Bank Account Found for ID - 42",
                accountQueryHandlerImpl.findAccountById(new FindAccountByIdQuery("42")).getMessage());
        verify(accountRepository).findById((String) any());
    }

    /**
     * Method under test: {@link AccountQueryHandlerImpl#findAccountById(FindAccountByIdQuery)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindAccountById3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.springbank.bankacc.query.api.queries.FindAccountByIdQuery.getId()" because "query" is null
        //       at com.springbank.bankacc.query.api.handlers.AccountQueryHandlerImpl.findAccountById(AccountQueryHandlerImpl.java:33)
        //   See https://diff.blue/R013 to resolve this issue.

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountHolderId("42");
        bankAccount.setAccountType(AccountType.SAVINGS);
        bankAccount.setBalance(10.0d);
        bankAccount.setCreationDate(LocalDate.ofEpochDay(1L));
        bankAccount.setCreditLimit(10.0d);
        bankAccount.setId("42");
        bankAccount.setTransactions(new ArrayList<>());
        Optional<BankAccount> ofResult = Optional.of(bankAccount);
        when(accountRepository.findById((String) any())).thenReturn(ofResult);
        accountQueryHandlerImpl.findAccountById(null);
    }

    /**
     * Method under test: {@link AccountQueryHandlerImpl#findAllAccounts(FindAllAccountsQuery)}
     */
    @Test
    void testFindAllAccounts() {
        when(accountRepository.findByBalanceLessThan(anyDouble())).thenReturn(new ArrayList<>());
        assertEquals("No Bank Accounts were Found!",
                accountQueryHandlerImpl.findAllAccounts(new FindAllAccountsQuery()).getMessage());
        verify(accountRepository).findByBalanceLessThan(anyDouble());
    }

    /**
     * Method under test: {@link AccountQueryHandlerImpl#findAllAccounts(FindAllAccountsQuery)}
     */
    @Test
    void testFindAllAccounts2() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountHolderId("42");
        bankAccount.setAccountType(AccountType.SAVINGS);
        bankAccount.setBalance(10.0d);
        bankAccount.setCreationDate(LocalDate.ofEpochDay(1L));
        bankAccount.setCreditLimit(10.0d);
        bankAccount.setId("42");
        bankAccount.setTransactions(new ArrayList<>());

        ArrayList<BankAccount> bankAccountList = new ArrayList<>();
        bankAccountList.add(bankAccount);
        when(accountRepository.findByBalanceLessThan(anyDouble())).thenReturn(bankAccountList);
        AccountLookupResponse actualFindAllAccountsResult = accountQueryHandlerImpl
                .findAllAccounts(new FindAllAccountsQuery());
        assertEquals(1, actualFindAllAccountsResult.getAccounts().size());
        assertEquals("Successfully Returned 1 Bank Account(s)!", actualFindAllAccountsResult.getMessage());
        verify(accountRepository).findByBalanceLessThan(anyDouble());
    }

    /**
     * Method under test: {@link AccountQueryHandlerImpl#findAllAccounts(FindAllAccountsQuery)}
     */
    @Test
    void testFindAllAccounts3() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountHolderId("42");
        bankAccount.setAccountType(AccountType.SAVINGS);
        bankAccount.setBalance(10.0d);
        bankAccount.setCreationDate(LocalDate.ofEpochDay(1L));
        bankAccount.setCreditLimit(10.0d);
        bankAccount.setId("42");
        bankAccount.setTransactions(new ArrayList<>());

        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setAccountHolderId("42");
        bankAccount1.setAccountType(AccountType.SAVINGS);
        bankAccount1.setBalance(10.0d);
        bankAccount1.setCreationDate(LocalDate.ofEpochDay(1L));
        bankAccount1.setCreditLimit(10.0d);
        bankAccount1.setId("42");
        bankAccount1.setTransactions(new ArrayList<>());

        ArrayList<BankAccount> bankAccountList = new ArrayList<>();
        bankAccountList.add(bankAccount1);
        bankAccountList.add(bankAccount);
        when(accountRepository.findByBalanceLessThan(anyDouble())).thenReturn(bankAccountList);
        AccountLookupResponse actualFindAllAccountsResult = accountQueryHandlerImpl
                .findAllAccounts(new FindAllAccountsQuery());
        assertEquals(2, actualFindAllAccountsResult.getAccounts().size());
        assertEquals("Successfully Returned 2 Bank Account(s)!", actualFindAllAccountsResult.getMessage());
        verify(accountRepository).findByBalanceLessThan(anyDouble());
    }

    /**
     * Method under test: {@link AccountQueryHandlerImpl#findByAccountAndTransactionDate(FindAccountAllTransactionsForGivenDate)}
     */
    @Test
    void testFindByAccountAndTransactionDate() {
        when(transactionRepository.findByTransferFromAndTransactionDate((String) any(), (LocalDate) any()))
                .thenReturn(new ArrayList<>());
        assertEquals("No Bank Accounts were Found!",
                accountQueryHandlerImpl
                        .findByAccountAndTransactionDate(
                                new FindAccountAllTransactionsForGivenDate("42", LocalDate.ofEpochDay(1L)))
                        .getMessage());
        verify(transactionRepository).findByTransferFromAndTransactionDate((String) any(), (LocalDate) any());
    }

    /**
     * Method under test: {@link AccountQueryHandlerImpl#findByAccountAndTransactionDate(FindAccountAllTransactionsForGivenDate)}
     */
    @Test
    void testFindByAccountAndTransactionDate2() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountHolderId("42");
        bankAccount.setAccountType(AccountType.SAVINGS);
        bankAccount.setBalance(10.0d);
        bankAccount.setCreationDate(LocalDate.ofEpochDay(1L));
        bankAccount.setCreditLimit(10.0d);
        bankAccount.setId("42");
        bankAccount.setTransactions(new ArrayList<>());

        Transaction transaction = new Transaction();
        transaction.setBankAccount(bankAccount);
        transaction.setTransactionAmount(10.0d);
        transaction.setTransactionDate(LocalDate.ofEpochDay(1L));
        transaction.setTransactionId("42");
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setTransferFrom("jane.doe@example.org");
        transaction.setTransferTo("alice.liddell@example.org");

        ArrayList<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);
        when(transactionRepository.findByTransferFromAndTransactionDate((String) any(), (LocalDate) any()))
                .thenReturn(transactionList);
        AccountLookupResponse actualFindByAccountAndTransactionDateResult = accountQueryHandlerImpl
                .findByAccountAndTransactionDate(new FindAccountAllTransactionsForGivenDate("42", LocalDate.ofEpochDay(1L)));
        assertEquals(1, actualFindByAccountAndTransactionDateResult.getAccounts().size());
        assertEquals("Successfully Returned 1 Bank Account Transaction(s)!",
                actualFindByAccountAndTransactionDateResult.getMessage());
        verify(transactionRepository).findByTransferFromAndTransactionDate((String) any(), (LocalDate) any());
    }

    /**
     * Method under test: {@link AccountQueryHandlerImpl#findByAccountAndTransactionDate(FindAccountAllTransactionsForGivenDate)}
     */
    @Test
    void testFindByAccountAndTransactionDate3() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountHolderId("42");
        bankAccount.setAccountType(AccountType.SAVINGS);
        bankAccount.setBalance(10.0d);
        bankAccount.setCreationDate(LocalDate.ofEpochDay(1L));
        bankAccount.setCreditLimit(10.0d);
        bankAccount.setId("42");
        bankAccount.setTransactions(new ArrayList<>());

        Transaction transaction = new Transaction();
        transaction.setBankAccount(bankAccount);
        transaction.setTransactionAmount(10.0d);
        transaction.setTransactionDate(LocalDate.ofEpochDay(1L));
        transaction.setTransactionId("42");
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setTransferFrom("jane.doe@example.org");
        transaction.setTransferTo("alice.liddell@example.org");

        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setAccountHolderId("42");
        bankAccount1.setAccountType(AccountType.SAVINGS);
        bankAccount1.setBalance(10.0d);
        bankAccount1.setCreationDate(LocalDate.ofEpochDay(1L));
        bankAccount1.setCreditLimit(10.0d);
        bankAccount1.setId("42");
        bankAccount1.setTransactions(new ArrayList<>());

        Transaction transaction1 = new Transaction();
        transaction1.setBankAccount(bankAccount1);
        transaction1.setTransactionAmount(10.0d);
        transaction1.setTransactionDate(LocalDate.ofEpochDay(1L));
        transaction1.setTransactionId("42");
        transaction1.setTransactionType(TransactionType.CREDIT);
        transaction1.setTransferFrom("jane.doe@example.org");
        transaction1.setTransferTo("alice.liddell@example.org");

        ArrayList<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction1);
        transactionList.add(transaction);
        when(transactionRepository.findByTransferFromAndTransactionDate((String) any(), (LocalDate) any()))
                .thenReturn(transactionList);
        AccountLookupResponse actualFindByAccountAndTransactionDateResult = accountQueryHandlerImpl
                .findByAccountAndTransactionDate(new FindAccountAllTransactionsForGivenDate("42", LocalDate.ofEpochDay(1L)));
        assertEquals(1, actualFindByAccountAndTransactionDateResult.getAccounts().size());
        assertEquals("Successfully Returned 2 Bank Account Transaction(s)!",
                actualFindByAccountAndTransactionDateResult.getMessage());
        verify(transactionRepository).findByTransferFromAndTransactionDate((String) any(), (LocalDate) any());
    }

    /**
     * Method under test: {@link AccountQueryHandlerImpl#findByAccountAndTransactionDate(FindAccountAllTransactionsForGivenDate)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindByAccountAndTransactionDate4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.springbank.bankacc.query.api.queries.FindAccountAllTransactionsForGivenDate.getId()" because "query" is null
        //       at com.springbank.bankacc.query.api.handlers.AccountQueryHandlerImpl.findByAccountAndTransactionDate(AccountQueryHandlerImpl.java:77)
        //   See https://diff.blue/R013 to resolve this issue.

        when(transactionRepository.findByTransferFromAndTransactionDate((String) any(), (LocalDate) any()))
                .thenReturn(new ArrayList<>());
        accountQueryHandlerImpl.findByAccountAndTransactionDate(null);
    }
}

