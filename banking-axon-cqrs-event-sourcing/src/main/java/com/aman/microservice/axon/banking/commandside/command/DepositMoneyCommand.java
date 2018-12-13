package com.aman.microservice.axon.banking.commandside.command;

import lombok.Getter;

@Getter
public class DepositMoneyCommand extends AbstarctCommand {

    private double amount;

    public DepositMoneyCommand(String id, double amount) {
        super(id);
        this.amount = amount;
    }

}