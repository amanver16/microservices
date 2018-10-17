package com.aman.banking.account.controller;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.aman.banking.account.command.CloseAccountCommand;
import com.aman.banking.account.command.CreateAccountCommand;
import com.aman.banking.account.command.DepositMoneyCommand;
import com.aman.banking.account.command.WithdrawMoneyCommand;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/banking")
@EnableAutoConfiguration
public class AccountController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountCommand createAccountCommand) {
        String accountNumber = UUID.randomUUID().toString();
        createAccountCommand.setAccountNumber(accountNumber);
        return commandGateway.send(createAccountCommand);
    }

    @PostMapping("/close")
    public CompletableFuture<String> closeAccount(@RequestBody CloseAccountCommand CloseAccountCommand) {
        return commandGateway.send(CloseAccountCommand);
    }

    @PostMapping("/deposit")
    public CompletableFuture<String> deposit(@RequestBody DepositMoneyCommand depositMoneyCommand) {
        return commandGateway.send(depositMoneyCommand);
    }

    @PostMapping("/withdraw")
    public CompletableFuture<String> withdraw(@RequestBody WithdrawMoneyCommand withdrawMoneyCommand) {
        return commandGateway.send(withdrawMoneyCommand);
    }

}