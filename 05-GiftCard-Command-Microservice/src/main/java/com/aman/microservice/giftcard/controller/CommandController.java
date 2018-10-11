package com.aman.microservice.giftcard.controller;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.aman.microservice.giftcard.command.IssueCardCommand;
import com.aman.microservice.giftcard.command.RedeemCardCommand;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/giftcard")
@EnableAutoConfiguration
public class CommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/issue/{amount}")
    public CompletableFuture<String> issueGiftCard(@PathVariable int amount) {

        String id = UUID.randomUUID().toString();
        return commandGateway.send(new IssueCardCommand(id, amount));
    }

    @PostMapping("/redeem/{id}/{amount}")
    public CompletableFuture<String> redeemGiftCard(@PathVariable String id, @PathVariable int amount) {
        return commandGateway.send(new RedeemCardCommand(id, amount));
    }

}