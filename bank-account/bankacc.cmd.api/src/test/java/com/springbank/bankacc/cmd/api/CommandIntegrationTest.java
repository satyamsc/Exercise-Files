package com.springbank.bankacc.cmd.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.springbank.bankacc.cmd.api.commands.InitiatePaymentCommand;
import com.springbank.bankacc.cmd.api.commands.OpenAccountCommand;
import com.springbank.bankacc.core.models.AccountType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class CommandIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testOpenAccountEndpoint() throws Exception {
        final var jsonBody = new ObjectMapper().writeValueAsString(OpenAccountCommand.builder()
                .accountHolderId("xyz")
                .accountType(AccountType.SAVINGS)
                .creditLimit(3.5)
                .openingBalance(20.9).
                build());
        this.mockMvc.perform(post("/api/v1/openBankAccount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(isA(String.class)))
                .andExpect(jsonPath("$.message").value("successfully opened a new bank account!"));
    }

    @Test
    void testInitiatePaymentEndpoint() throws Exception {
        final var jsonBody = new ObjectMapper().writeValueAsString(OpenAccountCommand.builder()
                .accountHolderId("xyz")
                .accountType(AccountType.SAVINGS)
                .creditLimit(3.5)
                .openingBalance(20.9).
                build());
        var mvcResult = this.mockMvc.perform(post("/api/v1/openBankAccount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(isA(String.class)))
                .andExpect(jsonPath("$.message").value("successfully opened a new bank account!"))
                .andReturn();

        String accountId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.id");
        final var paymentJsonBody = new ObjectMapper().writeValueAsString(InitiatePaymentCommand.builder()
                .amount(20.0)
                .transferTo(UUID.randomUUID().toString())
                .build());
        this.mockMvc.perform(put("/api/v1/initiatePayment/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(paymentJsonBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Funds successfully deposited!"));
    }
}
