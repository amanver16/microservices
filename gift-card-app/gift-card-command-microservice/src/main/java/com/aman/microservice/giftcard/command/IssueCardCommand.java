package com.aman.microservice.giftcard.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IssueCardCommand {

    @TargetAggregateIdentifier
    private String id;

    private int amount;

}