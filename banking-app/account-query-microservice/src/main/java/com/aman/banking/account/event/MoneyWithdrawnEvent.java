package com.aman.banking.account.event;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class MoneyWithdrawnEvent {

    @TargetAggregateIdentifier
    private String accountNumber;

    private double withdrawAmount;

}