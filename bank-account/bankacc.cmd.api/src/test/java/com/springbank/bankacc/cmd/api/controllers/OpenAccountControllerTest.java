package com.springbank.bankacc.cmd.api.controllers;

import com.springbank.bankacc.cmd.api.commands.OpenAccountCommand;
import com.springbank.bankacc.cmd.api.dto.OpenAccountResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class OpenAccountControllerTest {

    @Autowired
    private OpenAccountController openAccountController;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testOpenAccount() {
        OpenAccountController openAccountController = new OpenAccountController(mock(CommandGateway.class));
        OpenAccountCommand openAccountCommand = mock(OpenAccountCommand.class);
        doNothing().when(openAccountCommand).setId((String) any());
        ResponseEntity<OpenAccountResponse> actualOpenAccountResult = openAccountController.openAccount(openAccountCommand);
        assertTrue(actualOpenAccountResult.hasBody());
        assertTrue(actualOpenAccountResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.CREATED, actualOpenAccountResult.getStatusCode());
        verify(openAccountCommand).setId((String) any());
    }

    @Test
    void testOpenAccount2() {
        CommandGateway commandGateway = mock(CommandGateway.class);
        when(commandGateway.sendAndWait((Object) any())).thenReturn("Send And Wait");
        OpenAccountController openAccountController = new OpenAccountController(commandGateway);
        OpenAccountCommand openAccountCommand = mock(OpenAccountCommand.class);
        doNothing().when(openAccountCommand).setId((String) any());
        ResponseEntity<OpenAccountResponse> actualOpenAccountResult = openAccountController
                .openAccount(openAccountCommand);
        assertTrue(actualOpenAccountResult.hasBody());
        assertTrue(actualOpenAccountResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.CREATED, actualOpenAccountResult.getStatusCode());
        assertEquals("successfully opened a new bank account!", actualOpenAccountResult.getBody().getMessage());
        verify(commandGateway).sendAndWait((Object) any());
        verify(openAccountCommand).setId((String) any());
    }

    /**
     * Method under test: {@link OpenAccountController#openAccount(OpenAccountCommand)}
     */
    @Test
    void testOpenAccount3() {
        CommandGateway commandGateway = mock(CommandGateway.class);
        when(commandGateway.sendAndWait((Object) any())).thenReturn(1);
        OpenAccountController openAccountController = new OpenAccountController(commandGateway);
        OpenAccountCommand openAccountCommand = mock(OpenAccountCommand.class);
        doNothing().when(openAccountCommand).setId((String) any());
        ResponseEntity<OpenAccountResponse> actualOpenAccountResult = openAccountController
                .openAccount(openAccountCommand);
        assertTrue(actualOpenAccountResult.hasBody());
        assertTrue(actualOpenAccountResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualOpenAccountResult.getStatusCode());
        verify(commandGateway).sendAndWait((Object) any());
        verify(openAccountCommand).setId((String) any());
    }
}

