package com.springbank.bankacc.cmd.api.aggregates;

import com.springbank.bankacc.cmd.api.commands.InitiatePaymentCommand;
import com.springbank.bankacc.cmd.api.commands.OpenAccountCommand;
import com.springbank.bankacc.core.events.AccountOpenedEvent;
import com.springbank.bankacc.core.events.PaymentInitiatedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDate;

@Aggregate
@NoArgsConstructor
@Data
public class AccountAggregate {
    @AggregateIdentifier
    private String id;
    private String accountHolderId;
    private double balance;
    private double creditLimit;

    @CommandHandler
    public AccountAggregate(OpenAccountCommand command) {
        var event = AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolderId(command.getAccountHolderId())
                .accountType(command.getAccountType())
                .creationDate(LocalDate.now())
                .openingBalance(command.getOpeningBalance())
                .creditLimit(command.getCreditLimit())
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountOpenedEvent event) {
        this.id = event.getId();
        this.accountHolderId = event.getAccountHolderId();
        this.creditLimit = event.getCreditLimit();
        this.balance = event.getOpeningBalance();
    }

    @CommandHandler
    public void handle(InitiatePaymentCommand command) {
        var amount = command.getAmount();
        if (this.balance + this.creditLimit - amount < 0) {
            throw new IllegalStateException("Payment is declined, insufficient funds!");
        }
        var event = PaymentInitiatedEvent.builder()
                .id(command.getId())
                .amount(amount)
                .balance(this.balance - amount)
                .transferTo(command.getTransferTo())
                .creditLimit(this.balance - amount < 0 ? this.balance + this.creditLimit - amount : this.creditLimit)
                .transactionDate(command.getTransactionDate())
                .build();
        AggregateLifecycle.apply(event);
    }
    @EventSourcingHandler
    public void on(PaymentInitiatedEvent event) {
        this.balance = event.getBalance();
        this.creditLimit = event.getCreditLimit();
    }
}
