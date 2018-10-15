package com.aman.banking.account.controller;

import com.aman.banking.account.entity.CustomerBean;
import com.aman.banking.account.queryhandler.AccountQueryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/banking")
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = "com.aman.banking.account.repository")
public class AccountQueryController {

    @Autowired
    private AccountQueryService accountQueryService;

    @GetMapping("/account/info/{accountNumber}")
    public CustomerBean getAccountInfo(@PathVariable String accountNumber) {
        return accountQueryService.getAccountInformation(accountNumber);
    }

    @GetMapping("/account/balance/{accountNumber}")
    public double getBalanceInfo(@PathVariable String accountNumber) {
        return accountQueryService.getBalanceInformation(accountNumber);
    }
}