package com.aman.microservice.axon.banking.commandside.event;

import lombok.Getter;

@Getter
public class MoneyDepositedEvent extends AbstractEvent {

    private static final long serialVersionUID = 1L;
    
    private double amount;

    public MoneyDepositedEvent(String id, double amount) {
        super(id);
        this.amount = amount;
    }

}