package com.springbank.bankacc.cmd.api.controllers;

import com.springbank.bankacc.cmd.api.commands.InitiatePaymentCommand;
import com.springbank.bankacc.core.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


class InitiatePaymentControllerTest {
    @Test
    void testInitiatePayment() {

        InitiatePaymentController initiatePaymentController = new InitiatePaymentController(null);
        InitiatePaymentCommand initiatePaymentCommand = mock(InitiatePaymentCommand.class);
        doNothing().when(initiatePaymentCommand).setId((String) any());
        ResponseEntity<BaseResponse> actualInitiatePaymentResult = initiatePaymentController.initiatePayment("42",
                initiatePaymentCommand);
        assertTrue(actualInitiatePaymentResult.hasBody());
        assertTrue(actualInitiatePaymentResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualInitiatePaymentResult.getStatusCode());
        assertEquals("Error while processing request to deposit funds into bank account for id - 42",
                actualInitiatePaymentResult.getBody().getMessage());
        verify(initiatePaymentCommand).setId((String) any());
    }

    @Test
    void testInitiatePayment2() {
        CommandGateway commandGateway = mock(CommandGateway.class);
        when(commandGateway.sendAndWait((Object) any())).thenReturn("Send And Wait");
        InitiatePaymentController initiatePaymentController = new InitiatePaymentController(commandGateway);
        InitiatePaymentCommand initiatePaymentCommand = mock(InitiatePaymentCommand.class);
        doNothing().when(initiatePaymentCommand).setId((String) any());
        ResponseEntity<BaseResponse> actualInitiatePaymentResult = initiatePaymentController.initiatePayment("42",
                initiatePaymentCommand);
        assertTrue(actualInitiatePaymentResult.hasBody());
        assertTrue(actualInitiatePaymentResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualInitiatePaymentResult.getStatusCode());
        assertEquals("Funds successfully deposited!", actualInitiatePaymentResult.getBody().getMessage());
        verify(commandGateway).sendAndWait((Object) any());
        verify(initiatePaymentCommand).setId((String) any());
    }
}

