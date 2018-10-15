package com.aman.banking.account.command;

import com.aman.banking.account.bean.CustomerBean;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class CloseAccountCommand {

    @TargetAggregateIdentifier
    private String accountNumber;

    private CustomerBean customerBean;

}