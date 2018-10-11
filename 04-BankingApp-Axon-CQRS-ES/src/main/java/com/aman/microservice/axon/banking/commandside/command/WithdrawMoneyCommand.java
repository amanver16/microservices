package com.aman.microservice.axon.banking.commandside.command;

import lombok.Getter;

@Getter
public class WithdrawMoneyCommand extends AbstarctCommand {

    private double amount;

    public WithdrawMoneyCommand(String id, double amount) {
        super(id);
        this.amount = amount;
    }

}