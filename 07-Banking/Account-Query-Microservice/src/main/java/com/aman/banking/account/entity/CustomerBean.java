package com.aman.banking.account.entity;

import java.time.Instant;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "customers")
public class CustomerBean {

    @Id
    private String accountNumber;

    @Field("name")
    private String name;

    @Field("gender")
    private String gender;

    @Field("date-of-birth")
    private Date dateOfBirth;

    @Field("address")
    private String address;

    @Field("nominee-name")
    private String nomineeName;

    @Field("email")
    private String email;

    @Field("phone")
    private long phone;   

    @Field("account_type")
    private String accountType;

    @Field("balance")
    private double balance;

    @Field("created_date")
    private Instant createdDate;

}