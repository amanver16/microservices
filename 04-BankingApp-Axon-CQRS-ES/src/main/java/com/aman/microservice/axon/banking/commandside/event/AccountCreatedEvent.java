package com.aman.microservice.axon.banking.commandside.event;

import lombok.Getter;

@Getter
public class AccountCreatedEvent extends AbstractEvent {

    private static final long serialVersionUID = 1L;

    private String accountCreator;
    private double balance;

    public AccountCreatedEvent(String id, String accountCreator, double balance) {
        super(id);
        this.accountCreator = accountCreator;
        this.balance = balance;
    }

}