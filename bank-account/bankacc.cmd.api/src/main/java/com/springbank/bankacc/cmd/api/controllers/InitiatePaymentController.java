package com.springbank.bankacc.cmd.api.controllers;

import com.springbank.bankacc.cmd.api.commands.InitiatePaymentCommand;
import com.springbank.bankacc.core.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/initiatePayment")
@Slf4j
public class InitiatePaymentController {
    private final CommandGateway commandGateway;

    @Autowired
    public InitiatePaymentController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> initiatePayment(@PathVariable(value = "id") String id,
                                                     @Valid @RequestBody InitiatePaymentCommand command) {
        try {
            command.setId(id);
            commandGateway.sendAndWait(command);
            return new ResponseEntity<>(new BaseResponse("Funds successfully deposited!" ), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing request to deposit funds into bank account for id - " + id;
            log.error("Error while processing request to deposit funds", e);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
