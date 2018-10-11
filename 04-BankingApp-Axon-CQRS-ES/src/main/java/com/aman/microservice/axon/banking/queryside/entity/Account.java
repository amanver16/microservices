package com.aman.microservice.axon.banking.queryside.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "account")
public class Account {

    @Id
    private String accountId;

    @Field("name")
    private String name;

    @Field("balance")
    private double balance;
}