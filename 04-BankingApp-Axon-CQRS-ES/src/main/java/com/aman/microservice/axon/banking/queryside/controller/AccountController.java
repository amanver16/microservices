package com.aman.microservice.axon.banking.queryside.controller;

import java.util.List;

import com.aman.microservice.axon.banking.queryside.entity.Account;
import com.aman.microservice.axon.banking.queryside.queryhandler.AccountQueryHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = "com.aman.microservice.axon.banking.queryside.repository")
@RequestMapping("/banking")
public class AccountController {

    @Autowired
    private AccountQueryHandler accountQueryHandler;

    @PostMapping("/accountInfo")
    public Account getAccountInformation(@RequestBody String accountId) {
        return accountQueryHandler.viewAccountInformation(accountId);
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return accountQueryHandler.getAvailableAccounts();
    }

}