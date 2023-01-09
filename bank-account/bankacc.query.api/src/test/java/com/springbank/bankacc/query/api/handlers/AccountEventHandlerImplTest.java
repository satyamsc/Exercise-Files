package com.springbank.bankacc.query.api.handlers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.springbank.bankacc.core.events.AccountOpenedEvent;
import com.springbank.bankacc.core.events.PaymentInitiatedEvent;
import com.springbank.bankacc.core.models.AccountType;
import com.springbank.bankacc.core.models.BankAccount;
import com.springbank.bankacc.core.models.Transaction;
import com.springbank.bankacc.query.api.repositories.AccountRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AccountEventHandlerImpl.class})
@ExtendWith(SpringExtension.class)
class AccountEventHandlerImplTest {
    @Autowired
    private AccountEventHandlerImpl accountEventHandlerImpl;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    void testOn() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountHolderId("42");
        bankAccount.setAccountType(AccountType.SAVINGS);
        bankAccount.setBalance(10.0d);
        bankAccount.setCreationDate(LocalDate.ofEpochDay(1L));
        bankAccount.setCreditLimit(10.0d);
        bankAccount.setId("42");
        bankAccount.setTransactions(new ArrayList<>());
        when(accountRepository.save((BankAccount) any())).thenReturn(bankAccount);
        AccountOpenedEvent accountOpenedEvent = mock(AccountOpenedEvent.class);
        when(accountOpenedEvent.getAccountType()).thenReturn(AccountType.SAVINGS);
        when(accountOpenedEvent.getCreditLimit()).thenReturn(10.0d);
        when(accountOpenedEvent.getOpeningBalance()).thenReturn(10.0d);
        when(accountOpenedEvent.getAccountHolderId()).thenReturn("42");
        when(accountOpenedEvent.getId()).thenReturn("42");
        when(accountOpenedEvent.getCreationDate()).thenReturn(LocalDate.ofEpochDay(1L));
        accountEventHandlerImpl.on(accountOpenedEvent);
        verify(accountRepository).save((BankAccount) any());
        verify(accountOpenedEvent).getAccountType();
        verify(accountOpenedEvent).getCreditLimit();
        verify(accountOpenedEvent, atLeast(1)).getOpeningBalance();
        verify(accountOpenedEvent).getAccountHolderId();
        verify(accountOpenedEvent, atLeast(1)).getId();
        verify(accountOpenedEvent, atLeast(1)).getCreationDate();
    }

    /**
     * Method under test: {@link AccountEventHandlerImpl#on(PaymentInitiatedEvent)}
     */
    @Test
    void testOn2() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountHolderId("42");
        bankAccount.setAccountType(AccountType.SAVINGS);
        bankAccount.setBalance(10.0d);
        bankAccount.setCreationDate(LocalDate.ofEpochDay(1L));
        bankAccount.setCreditLimit(10.0d);
        bankAccount.setId("42");
        bankAccount.setTransactions(new ArrayList<>());
        Optional<BankAccount> ofResult = Optional.of(bankAccount);

        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setAccountHolderId("42");
        bankAccount1.setAccountType(AccountType.SAVINGS);
        bankAccount1.setBalance(10.0d);
        bankAccount1.setCreationDate(LocalDate.ofEpochDay(1L));
        bankAccount1.setCreditLimit(10.0d);
        bankAccount1.setId("42");
        bankAccount1.setTransactions(new ArrayList<>());
        when(accountRepository.save((BankAccount) any())).thenReturn(bankAccount1);
        when(accountRepository.findById((String) any())).thenReturn(ofResult);
        PaymentInitiatedEvent paymentInitiatedEvent = mock(PaymentInitiatedEvent.class);
        when(paymentInitiatedEvent.getAmount()).thenReturn(10.0d);
        when(paymentInitiatedEvent.getBalance()).thenReturn(10.0d);
        when(paymentInitiatedEvent.getCreditLimit()).thenReturn(10.0d);
        when(paymentInitiatedEvent.getId()).thenReturn("42");
        when(paymentInitiatedEvent.getTransferTo()).thenReturn("alice.liddell@example.org");
        when(paymentInitiatedEvent.getTransactionDate()).thenReturn(LocalDate.ofEpochDay(1L));
        accountEventHandlerImpl.on(paymentInitiatedEvent);
        verify(accountRepository, atLeast(1)).save((BankAccount) any());
        verify(accountRepository, atLeast(1)).findById((String) any());
        verify(paymentInitiatedEvent, atLeast(1)).getAmount();
        verify(paymentInitiatedEvent).getBalance();
        verify(paymentInitiatedEvent).getCreditLimit();
        verify(paymentInitiatedEvent, atLeast(1)).getId();
        verify(paymentInitiatedEvent, atLeast(1)).getTransferTo();
        verify(paymentInitiatedEvent, atLeast(1)).getTransactionDate();
    }

    /**
     * Method under test: {@link AccountEventHandlerImpl#on(PaymentInitiatedEvent)}
     */
    @Test
    void testOn3() {
        BankAccount bankAccount = mock(BankAccount.class);
        when(bankAccount.getBalance()).thenReturn(10.0d);
        when(bankAccount.getTransactions()).thenReturn(new ArrayList<>());
        doNothing().when(bankAccount).setAccountHolderId((String) any());
        doNothing().when(bankAccount).setAccountType((AccountType) any());
        doNothing().when(bankAccount).setBalance(anyDouble());
        doNothing().when(bankAccount).setCreationDate((LocalDate) any());
        doNothing().when(bankAccount).setCreditLimit(anyDouble());
        doNothing().when(bankAccount).setId((String) any());
        doNothing().when(bankAccount).setTransactions((List<Transaction>) any());
        bankAccount.setAccountHolderId("42");
        bankAccount.setAccountType(AccountType.SAVINGS);
        bankAccount.setBalance(10.0d);
        bankAccount.setCreationDate(LocalDate.ofEpochDay(1L));
        bankAccount.setCreditLimit(10.0d);
        bankAccount.setId("42");
        bankAccount.setTransactions(new ArrayList<>());
        Optional<BankAccount> ofResult = Optional.of(bankAccount);

        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setAccountHolderId("42");
        bankAccount1.setAccountType(AccountType.SAVINGS);
        bankAccount1.setBalance(10.0d);
        bankAccount1.setCreationDate(LocalDate.ofEpochDay(1L));
        bankAccount1.setCreditLimit(10.0d);
        bankAccount1.setId("42");
        bankAccount1.setTransactions(new ArrayList<>());
        when(accountRepository.save((BankAccount) any())).thenReturn(bankAccount1);
        when(accountRepository.findById((String) any())).thenReturn(ofResult);
        PaymentInitiatedEvent paymentInitiatedEvent = mock(PaymentInitiatedEvent.class);
        when(paymentInitiatedEvent.getAmount()).thenReturn(10.0d);
        when(paymentInitiatedEvent.getBalance()).thenReturn(10.0d);
        when(paymentInitiatedEvent.getCreditLimit()).thenReturn(10.0d);
        when(paymentInitiatedEvent.getId()).thenReturn("42");
        when(paymentInitiatedEvent.getTransferTo()).thenReturn("alice.liddell@example.org");
        when(paymentInitiatedEvent.getTransactionDate()).thenReturn(LocalDate.ofEpochDay(1L));
        accountEventHandlerImpl.on(paymentInitiatedEvent);
        verify(accountRepository, atLeast(1)).save((BankAccount) any());
        verify(accountRepository, atLeast(1)).findById((String) any());
        verify(bankAccount).getBalance();
        verify(bankAccount, atLeast(1)).getTransactions();
        verify(bankAccount).setAccountHolderId((String) any());
        verify(bankAccount).setAccountType((AccountType) any());
        verify(bankAccount, atLeast(1)).setBalance(anyDouble());
        verify(bankAccount).setCreationDate((LocalDate) any());
        verify(bankAccount, atLeast(1)).setCreditLimit(anyDouble());
        verify(bankAccount).setId((String) any());
        verify(bankAccount).setTransactions((List<Transaction>) any());
        verify(paymentInitiatedEvent, atLeast(1)).getAmount();
        verify(paymentInitiatedEvent).getBalance();
        verify(paymentInitiatedEvent).getCreditLimit();
        verify(paymentInitiatedEvent, atLeast(1)).getId();
        verify(paymentInitiatedEvent, atLeast(1)).getTransferTo();
        verify(paymentInitiatedEvent, atLeast(1)).getTransactionDate();
    }

    /**
     * Method under test: {@link AccountEventHandlerImpl#on(PaymentInitiatedEvent)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testOn4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.util.NoSuchElementException: No value present
        //       at java.util.Optional.orElseThrow(Optional.java:377)
        //       at com.springbank.bankacc.query.api.handlers.AccountEventHandlerImpl.on(AccountEventHandlerImpl.java:55)
        //   See https://diff.blue/R013 to resolve this issue.

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountHolderId("42");
        bankAccount.setAccountType(AccountType.SAVINGS);
        bankAccount.setBalance(10.0d);
        bankAccount.setCreationDate(LocalDate.ofEpochDay(1L));
        bankAccount.setCreditLimit(10.0d);
        bankAccount.setId("42");
        bankAccount.setTransactions(new ArrayList<>());
        when(accountRepository.save((BankAccount) any())).thenReturn(bankAccount);
        when(accountRepository.findById((String) any())).thenReturn(Optional.empty());
        BankAccount bankAccount1 = mock(BankAccount.class);
        when(bankAccount1.getBalance()).thenReturn(10.0d);
        when(bankAccount1.getTransactions()).thenReturn(new ArrayList<>());
        doNothing().when(bankAccount1).setAccountHolderId((String) any());
        doNothing().when(bankAccount1).setAccountType((AccountType) any());
        doNothing().when(bankAccount1).setBalance(anyDouble());
        doNothing().when(bankAccount1).setCreationDate((LocalDate) any());
        doNothing().when(bankAccount1).setCreditLimit(anyDouble());
        doNothing().when(bankAccount1).setId((String) any());
        doNothing().when(bankAccount1).setTransactions((List<Transaction>) any());
        bankAccount1.setAccountHolderId("42");
        bankAccount1.setAccountType(AccountType.SAVINGS);
        bankAccount1.setBalance(10.0d);
        bankAccount1.setCreationDate(LocalDate.ofEpochDay(1L));
        bankAccount1.setCreditLimit(10.0d);
        bankAccount1.setId("42");
        bankAccount1.setTransactions(new ArrayList<>());
        PaymentInitiatedEvent paymentInitiatedEvent = mock(PaymentInitiatedEvent.class);
        when(paymentInitiatedEvent.getAmount()).thenReturn(10.0d);
        when(paymentInitiatedEvent.getBalance()).thenReturn(10.0d);
        when(paymentInitiatedEvent.getCreditLimit()).thenReturn(10.0d);
        when(paymentInitiatedEvent.getId()).thenReturn("42");
        when(paymentInitiatedEvent.getTransferTo()).thenReturn("alice.liddell@example.org");
        when(paymentInitiatedEvent.getTransactionDate()).thenReturn(LocalDate.ofEpochDay(1L));
        accountEventHandlerImpl.on(paymentInitiatedEvent);
    }
}

