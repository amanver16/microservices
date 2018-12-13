package com.aman.banking.account.command;

import com.aman.banking.account.bean.CustomerBean;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class CreateAccountCommand {

    @TargetAggregateIdentifier
    private String accountNumber;

    private String accountType;
    private double balance;
    private CustomerBean customerBean;

}