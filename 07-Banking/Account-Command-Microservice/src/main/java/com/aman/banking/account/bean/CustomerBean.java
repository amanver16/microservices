package com.aman.banking.account.bean;

import java.util.Date;

import lombok.Data;

@Data
public class CustomerBean {

    private String customerId;
    private String name;
    private String gender;
    private Date dateOfBirth;
    private String address;
    private String nomineeName;
    private String email;
    private long phone;

}