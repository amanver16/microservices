package com.aman.banking.account.event;

import lombok.Data;

@Data
public class MoneyTransferredEvent {

    private String senderAccountNo;
    private String recieverAccountNo;
    private double transferAmount;

}