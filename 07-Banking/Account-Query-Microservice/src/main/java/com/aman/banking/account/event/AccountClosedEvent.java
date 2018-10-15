package com.aman.banking.account.event;

import com.aman.banking.account.entity.CustomerBean;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class AccountClosedEvent {

    @TargetAggregateIdentifier
    private String accountNumber;

    private CustomerBean customerBean;

}