package com.springbank.bankacc.query.api.handlers;

import com.springbank.bankacc.query.api.dto.AccountLookupResponse;
import com.springbank.bankacc.query.api.queries.FindAccountByIdQuery;
import com.springbank.bankacc.query.api.queries.FindAccountAllTransactionsForGivenDate;
import com.springbank.bankacc.query.api.queries.FindAllAccountsQuery;

public interface AccountQueryHandler {
    AccountLookupResponse findAccountById(FindAccountByIdQuery query);
    AccountLookupResponse findAllAccounts(FindAllAccountsQuery query);
    AccountLookupResponse findByAccountAndTransactionDate(FindAccountAllTransactionsForGivenDate query);
}
