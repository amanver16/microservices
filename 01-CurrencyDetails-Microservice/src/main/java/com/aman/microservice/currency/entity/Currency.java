package com.aman.microservice.currency.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Document(collection = "currency")
public class Currency {
    
    @Id
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String objectId;

    @Field("currency_id")
    private int currencyId;

    @Field("currency_from")
    private String from;

    @Field("currency_to")
    private String to;

    @Field("conversion_factor")
    private int conversionFactor;

    @Field("port")
    private int port;

}