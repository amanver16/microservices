package com.aman.microservice.currencyconvertor.proxy;

import com.aman.microservice.currencyconvertor.entity.CurrencyConversionBean;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "01-CurrencyDetails-Microservice")
@RibbonClient(name = "01-CurrencyDetails-Microservice")
public interface CurrencyConversionProxy {

    @GetMapping(value = "/currency/get/{from}/{to}")
    public CurrencyConversionBean getCurrencyExchangeDetails(@PathVariable String from, @PathVariable String to);

}