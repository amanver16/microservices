package com.aman.microservice.giftcard.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardRedeemedEvent {

    private String id;
    private int amount;

}