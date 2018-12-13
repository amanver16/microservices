package com.aman.banking.account.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class TransferMoneyCommand {

    @TargetAggregateIdentifier
    private String senderAccountNo;

    private String recieverAccountNo;
    private double transferAmount;

}