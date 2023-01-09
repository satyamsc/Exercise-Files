package com.springbank.bankacc.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class FindAccountAllTransactionsForGivenDate {
    private String id;
    private LocalDate date;
}
