package com.aman.microservice.axon.banking.commandside.controller;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.aman.microservice.axon.banking.commandside.command.CloseAccountCommand;
import com.aman.microservice.axon.banking.commandside.command.CreateAccountCommand;
import com.aman.microservice.axon.banking.commandside.command.DepositMoneyCommand;
import com.aman.microservice.axon.banking.commandside.command.WithdrawMoneyCommand;
import com.aman.microservice.axon.banking.commandside.model.AccountOwner;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/banking")
public class BankController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/createAccount")
    public CompletableFuture<String> createAccount(@RequestBody AccountOwner accountOwner) {

        String id = UUID.randomUUID().toString();
        String name = accountOwner.getName();

        Assert.hasLength(id, "ID cannot be null !!!");
        Assert.hasLength(name, "Missig account creator name !!!");

        CreateAccountCommand createAccountCommand = new CreateAccountCommand(id, name);
        return commandGateway.send(createAccountCommand);
    }

    @PostMapping("/depositMoney")
    public CompletableFuture<String> depositMoney(@RequestBody AccountOwner accountOwner) {

        String accountId = accountOwner.getId();
        double depositAmount = accountOwner.getDepositAmount();

        Assert.hasLength(accountId, "ID cannot be null !!!");
        Assert.isTrue(depositAmount >= 0.0, "Deposit Amount must be greater than zero.");

        DepositMoneyCommand depositMoneyCommand = new DepositMoneyCommand(accountId, depositAmount);
        return commandGateway.send(depositMoneyCommand);
    }

    @PostMapping("/withdrawMoney")
    public CompletableFuture<String> withdrawMoney(@RequestBody AccountOwner accountOwner) {

        String accountId = accountOwner.getId();
        double withdrawAmount = accountOwner.getWithdrawAmount();

        Assert.hasLength(accountId, "ID cannot be null !!!");
        Assert.isTrue(withdrawAmount >= 0.0, "Withdraw amount must be a positive number.");

        WithdrawMoneyCommand withdrawMoneyCommand = new WithdrawMoneyCommand(accountId, withdrawAmount);
        return commandGateway.send(withdrawMoneyCommand);
    }

    @PostMapping("/deleteAccount")
    public CompletableFuture<String> delete(@RequestBody String id) {
        return commandGateway.send(new CloseAccountCommand(id));
    }

}
