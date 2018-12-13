package com.aman.microservice.axon.banking.commandside.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountOwner {

    private String id;
    private String name;
    private double accountBalance;
    private double depositAmount;
    private double withdrawAmount;

}