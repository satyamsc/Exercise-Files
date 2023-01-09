package com.springbank.bankacc.cmd.api.commands;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springbank.bankacc.core.models.AccountType;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenAccountCommand {
    @TargetAggregateIdentifier
    private String id;
    @NotNull
    private String accountHolderId;
    @NotNull
    private AccountType accountType;
    @NotNull
    private Double openingBalance;
    @NotNull
    private Double creditLimit;
}
