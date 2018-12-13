package com.aman.banking.account.event;

import com.aman.banking.account.entity.CustomerBean;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class AccountCreatedEvent {

    @TargetAggregateIdentifier
    private String accountNumber;

    private String accountType;
    private double balance;
    private CustomerBean customerBean;

}