package com.aman.banking.account.event;

import lombok.Data;

@Data
public class MoneyDepositedEvent {

    private String accountNumber;
    private double depositAmount;

}