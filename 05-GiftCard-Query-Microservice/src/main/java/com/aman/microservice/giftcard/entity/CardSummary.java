package com.aman.microservice.giftcard.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection="gift-card")
public class CardSummary {

    @Id
    private String id;

    @Field("amount")
    private int amount;

    @Field("time")
    private Instant instant;

    @Field("remaining_amount")
    private int remainingAmount;

}