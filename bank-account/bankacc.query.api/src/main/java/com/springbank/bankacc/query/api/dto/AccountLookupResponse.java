package com.springbank.bankacc.query.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.springbank.bankacc.core.dto.BaseResponse;
import com.springbank.bankacc.core.models.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class AccountLookupResponse extends BaseResponse {
    private List<BankAccountDto> accounts;

    public AccountLookupResponse(String message) {
        super(message);
    }

    public AccountLookupResponse(String message, List<BankAccountDto> accounts) {
        super(message);
        this.accounts = accounts;
    }

    public AccountLookupResponse(String message, BankAccountDto account) {
        super(message);
        this.accounts = new ArrayList<>();
        this.accounts.add(account);
    }

    public void setAccounts(List<BankAccountDto> accounts) {
        this.accounts = accounts;
    }

    public List<BankAccountDto> getAccounts() {
        return this.accounts;
    }
}
