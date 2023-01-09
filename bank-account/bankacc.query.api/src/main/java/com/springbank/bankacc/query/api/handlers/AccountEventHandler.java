package com.springbank.bankacc.query.api.handlers;

import com.springbank.bankacc.core.events.AccountOpenedEvent;
import com.springbank.bankacc.core.events.PaymentInitiatedEvent;

public interface AccountEventHandler {
    void on(AccountOpenedEvent event);
    void on(PaymentInitiatedEvent event);

}
