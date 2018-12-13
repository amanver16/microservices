package com.aman.microservice.axon.banking.queryside.queryhandler;

import java.util.List;

import com.aman.microservice.axon.banking.queryside.entity.Account;
import com.aman.microservice.axon.banking.queryside.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountQueryHandler {

    @Autowired
    private AccountRepository accountRepository;

    public Account viewAccountInformation(String accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    public List<Account> getAvailableAccounts() {
        return accountRepository.findAll();
    }

}