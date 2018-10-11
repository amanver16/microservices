package com.aman.microservice.axon.banking.commandside.event;

import java.io.Serializable;

import org.springframework.util.Assert;

import lombok.Getter;

@Getter
public class AbstractEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String id;

    public AbstractEvent(String id) {
        Assert.notNull(id, "Id cannot be null !!!");
        this.id = id;
    }

}