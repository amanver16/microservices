package com.aman.microservice.giftcard.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardIssuedEvent {

    private String id;
    private int amount;

}