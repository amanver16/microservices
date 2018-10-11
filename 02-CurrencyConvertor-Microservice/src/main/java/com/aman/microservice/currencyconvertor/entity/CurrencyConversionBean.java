package com.aman.microservice.currencyconvertor.entity;

import lombok.Data;

@Data
public class CurrencyConversionBean {
  
    private int currencyId;    
    private String from;   
    private String to;   
    private int conversionFactor;    
    private long quantity;   
    private long convertedAmount;   
    private int port;

}