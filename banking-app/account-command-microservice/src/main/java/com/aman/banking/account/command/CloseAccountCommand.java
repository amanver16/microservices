package com.aman.banking.account.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class CloseAccountCommand {

    @TargetAggregateIdentifier
    private String accountNumber;

}