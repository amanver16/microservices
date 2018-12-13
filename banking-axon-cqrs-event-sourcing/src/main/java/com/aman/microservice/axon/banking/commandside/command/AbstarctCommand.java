package com.aman.microservice.axon.banking.commandside.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.springframework.util.Assert;

import lombok.Getter;

@Getter
public class AbstarctCommand {

    @TargetAggregateIdentifier
    private String id;

    public AbstarctCommand(String id) {
        Assert.notNull(id, "Id cannot be null !!!");
        this.id = id;
    }

}