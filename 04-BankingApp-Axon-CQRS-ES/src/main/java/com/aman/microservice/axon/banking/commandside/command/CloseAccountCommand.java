package com.aman.microservice.axon.banking.commandside.command;

import lombok.Getter;

@Getter
public class CloseAccountCommand extends AbstarctCommand {

    public CloseAccountCommand(String id) {
        super(id);
    }

}