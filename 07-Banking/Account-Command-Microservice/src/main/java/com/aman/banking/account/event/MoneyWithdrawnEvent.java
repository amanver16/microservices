package com.aman.banking.account.event;

import lombok.Data;

@Data
public class MoneyWithdrawnEvent {

    private String accountNumber;
    private double withdrawAmount;

}