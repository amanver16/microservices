package com.aman.banking.account.bean;

import java.util.Date;

import org.axonframework.commandhandling.model.EntityId;

import lombok.Data;

@Data
public class CustomerBean {

    @EntityId(routingKey="customerId")
    private String customerId;
    
    private String name;
    private String gender;
    private Date dateOfBirth;
    private String address;
    private String nomineeName;
    private String email;
    private long phone;

}