package com.aman.microservice.currency.controller;

import com.aman.microservice.currency.entity.Currency;
import com.aman.microservice.currency.repository.CurrencyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = "com.aman.microservice.currency.repository")
@RequestMapping(value = "/currency")
public class CurrencyController {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private Environment environment;

    @GetMapping(value = "/get/{from}/{to}")
    public Currency getCurrencyExchangeDetails(@PathVariable String from, @PathVariable String to) {

        Currency currency = currencyRepository.findByFromAndTo(from, to);
        currency.setPort(Integer.parseInt(environment.getProperty("local.server.port")));

        return currency;

    }

}