package com.aman.banking.account.event;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class MoneyDepositedEvent {

    @TargetAggregateIdentifier
    private String accountNumber;

    private double depositAmount;

}