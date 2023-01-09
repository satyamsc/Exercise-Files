package com.springbank.bankacc.cmd.api.commands;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InitiatePaymentCommand {
    @TargetAggregateIdentifier
    private String id;
    private double amount;
    private String transferTo;
    private LocalDate transactionDate;
}
