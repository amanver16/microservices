package com.aman.banking.account.queryhandler;

import com.aman.banking.account.entity.CustomerBean;
import com.aman.banking.account.repository.CustomerRepository;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("account-query")
public class AccountQueryService {

    @Autowired
    private CustomerRepository customerRepository;

    @QueryHandler
    public CustomerBean getAccountInformation(String accountNumber) {
        return customerRepository.findById(accountNumber).orElse(null);
    }

    @QueryHandler
    public double getBalanceInformation(String accountNumber) {
        return customerRepository.findById(accountNumber).orElse(null).getBalance();
    }

}