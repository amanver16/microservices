package com.aman.microservice.currencyconvertor.controller;

import com.aman.microservice.currencyconvertor.entity.CurrencyConversionBean;
import com.aman.microservice.currencyconvertor.proxy.CurrencyConversionProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/convert/currency")
public class CurrencyConversionController {

    @Autowired
    private CurrencyConversionProxy currencyConversionProxy;

    @GetMapping(value = "/{from}/{to}/{quantity}")
    public CurrencyConversionBean getConvertedCurrencyValues(@PathVariable String from, @PathVariable String to,
            @PathVariable long quantity) {

        CurrencyConversionBean currencyConversionBean = currencyConversionProxy.getCurrencyExchangeDetails(from, to);
        currencyConversionBean.setConvertedAmount(quantity * currencyConversionBean.getConversionFactor());
        currencyConversionBean.setQuantity(quantity);

        return currencyConversionBean;
    }

}