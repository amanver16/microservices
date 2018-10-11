package com.aman.microservice.axon.banking.commandside.command;

import lombok.Getter;

@Getter
public class CreateAccountCommand extends AbstarctCommand {

    private String accountCreator;

    public CreateAccountCommand(String id, String accountCreator) {
        super(id);
        this.accountCreator = accountCreator;
    }

}