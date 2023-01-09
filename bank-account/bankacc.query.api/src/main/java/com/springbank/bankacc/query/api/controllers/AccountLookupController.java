package com.springbank.bankacc.query.api.controllers;

import com.springbank.bankacc.query.api.dto.AccountLookupResponse;
import com.springbank.bankacc.query.api.queries.FindAccountAllTransactionsForGivenDate;
import com.springbank.bankacc.query.api.queries.FindAccountByIdQuery;
import com.springbank.bankacc.query.api.queries.FindAllAccountsQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/api/v1/bankAccountLookup")
@Slf4j
public class AccountLookupController {
    private final QueryGateway queryGateway;

    @Autowired
    public AccountLookupController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping(path = "/")
    public ResponseEntity<AccountLookupResponse> getAllAccounts() {
        try {
            var query = new FindAllAccountsQuery();
            var response = queryGateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();
            if (response == null || response.getAccounts() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            String msg = "Failed to complete get all accounts request";
            log.error(msg, e);
            return new ResponseEntity<>(new AccountLookupResponse(msg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/account/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable(value = "id") String id) {
        try {
            var query = new FindAccountByIdQuery(id);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();
            if (response == null || response.getAccounts() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get account by ID request";
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/account/{id}/{date}")
    public ResponseEntity<AccountLookupResponse> getAccountByHolderId(@PathVariable(value = "id") String id,
                                                                      @PathVariable(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        try {
            var query = new FindAccountAllTransactionsForGivenDate(id, date);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();
            if (response == null || response.getAccounts() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get accounts with balance request";
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
