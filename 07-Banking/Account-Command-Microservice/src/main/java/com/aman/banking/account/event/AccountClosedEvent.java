package com.aman.banking.account.event;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class AccountClosedEvent {

    @TargetAggregateIdentifier
    private String accountNumber;

}