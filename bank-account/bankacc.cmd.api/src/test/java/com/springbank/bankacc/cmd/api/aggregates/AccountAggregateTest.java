package com.springbank.bankacc.cmd.api.aggregates;

import com.springbank.bankacc.cmd.api.commands.InitiatePaymentCommand;
import com.springbank.bankacc.cmd.api.commands.OpenAccountCommand;
import com.springbank.bankacc.core.events.AccountOpenedEvent;
import com.springbank.bankacc.core.events.PaymentInitiatedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {AccountAggregate.class})
@ExtendWith(SpringExtension.class)
class AccountAggregateTest {

    @Test
    void testConstructor() {
        OpenAccountCommand openAccountCommand = mock(OpenAccountCommand.class);
        when(openAccountCommand.getAccountType()).thenThrow(new IllegalStateException());
        when(openAccountCommand.getCreditLimit()).thenThrow(new IllegalStateException());
        when(openAccountCommand.getOpeningBalance()).thenThrow(new IllegalStateException());
        when(openAccountCommand.getAccountHolderId()).thenThrow(new IllegalStateException());
        when(openAccountCommand.getId()).thenThrow(new IllegalStateException());
        assertThrows(IllegalStateException.class, () -> new AccountAggregate(openAccountCommand));
        verify(openAccountCommand).getId();
    }

    @Test
    void testOn() {
        AccountAggregate accountAggregate = new AccountAggregate();
        AccountOpenedEvent accountOpenedEvent = mock(AccountOpenedEvent.class);
        when(accountOpenedEvent.getCreditLimit()).thenReturn(10.0d);
        when(accountOpenedEvent.getOpeningBalance()).thenReturn(10.0d);
        when(accountOpenedEvent.getAccountHolderId()).thenReturn("42");
        when(accountOpenedEvent.getId()).thenReturn("42");
        accountAggregate.on(accountOpenedEvent);
        verify(accountOpenedEvent).getCreditLimit();
        verify(accountOpenedEvent).getOpeningBalance();
        verify(accountOpenedEvent).getAccountHolderId();
        verify(accountOpenedEvent).getId();
        assertEquals("42", accountAggregate.getAccountHolderId());
        assertEquals("42", accountAggregate.getId());
        assertEquals(10.0d, accountAggregate.getCreditLimit());
        assertEquals(10.0d, accountAggregate.getBalance());
    }

    @Test
    void testOn2() {
        AccountAggregate accountAggregate = new AccountAggregate();
        AccountOpenedEvent accountOpenedEvent = mock(AccountOpenedEvent.class);
        when(accountOpenedEvent.getCreditLimit()).thenThrow(new IllegalStateException());
        when(accountOpenedEvent.getOpeningBalance()).thenThrow(new IllegalStateException());
        when(accountOpenedEvent.getAccountHolderId()).thenThrow(new IllegalStateException());
        when(accountOpenedEvent.getId()).thenThrow(new IllegalStateException());
        assertThrows(IllegalStateException.class, () -> accountAggregate.on(accountOpenedEvent));
        verify(accountOpenedEvent).getId();
    }

    @Test
    void testOn3() {
        AccountAggregate accountAggregate = new AccountAggregate();
        PaymentInitiatedEvent paymentInitiatedEvent = mock(PaymentInitiatedEvent.class);
        when(paymentInitiatedEvent.getBalance()).thenReturn(10.0d);
        when(paymentInitiatedEvent.getCreditLimit()).thenReturn(10.0d);
        accountAggregate.on(paymentInitiatedEvent);
        verify(paymentInitiatedEvent).getBalance();
        verify(paymentInitiatedEvent).getCreditLimit();
        assertEquals(10.0d, accountAggregate.getCreditLimit());
        assertEquals(10.0d, accountAggregate.getBalance());
    }

    @Test
    void testOn4() {
        AccountAggregate accountAggregate = new AccountAggregate();
        PaymentInitiatedEvent paymentInitiatedEvent = mock(PaymentInitiatedEvent.class);
        when(paymentInitiatedEvent.getBalance()).thenThrow(new IllegalStateException());
        when(paymentInitiatedEvent.getCreditLimit()).thenThrow(new IllegalStateException());
        assertThrows(IllegalStateException.class, () -> accountAggregate.on(paymentInitiatedEvent));
        verify(paymentInitiatedEvent).getBalance();
    }

    @Test
    void testHandle() {
        AccountAggregate accountAggregate = new AccountAggregate();
        InitiatePaymentCommand initiatePaymentCommand = mock(InitiatePaymentCommand.class);
        when(initiatePaymentCommand.getAmount()).thenReturn(10.0d);
        when(initiatePaymentCommand.getId()).thenReturn("42");
        when(initiatePaymentCommand.getTransferTo()).thenReturn("alice.liddell@example.org");
        when(initiatePaymentCommand.getTransactionDate()).thenReturn(LocalDate.ofEpochDay(1L));
        assertThrows(IllegalStateException.class, () -> accountAggregate.handle(initiatePaymentCommand));
        verify(initiatePaymentCommand).getAmount();
    }

    @Test
    void testHandle3() {
        AccountAggregate accountAggregate = new AccountAggregate();
        InitiatePaymentCommand initiatePaymentCommand = mock(InitiatePaymentCommand.class);
        when(initiatePaymentCommand.getAmount()).thenThrow(new IllegalStateException());
        when(initiatePaymentCommand.getId()).thenThrow(new IllegalStateException());
        when(initiatePaymentCommand.getTransferTo()).thenThrow(new IllegalStateException());
        when(initiatePaymentCommand.getTransactionDate()).thenThrow(new IllegalStateException());
        assertThrows(IllegalStateException.class, () -> accountAggregate.handle(initiatePaymentCommand));
        verify(initiatePaymentCommand).getAmount();
    }
}
