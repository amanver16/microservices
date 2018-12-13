package com.aman.microservice.axon.banking.queryside.eventhandler;

import com.aman.microservice.axon.banking.commandside.event.AccountClosedEvent;
import com.aman.microservice.axon.banking.commandside.event.AccountCreatedEvent;
import com.aman.microservice.axon.banking.commandside.event.MoneyDepositedEvent;
import com.aman.microservice.axon.banking.commandside.event.MoneyWithdrawnEvent;
import com.aman.microservice.axon.banking.queryside.entity.Account;
import com.aman.microservice.axon.banking.queryside.repository.AccountRepository;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEventHandler {

    @Autowired
    private AccountRepository accountRepository;

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {

        Account account = new Account();

        account.setAccountId(accountCreatedEvent.getId());
        account.setName(accountCreatedEvent.getAccountCreator());
        account.setBalance(accountCreatedEvent.getBalance());

        accountRepository.save(account);
    }

    @EventHandler
    public void on(MoneyDepositedEvent moneyDepositedEvent) {

        Account account = accountRepository.findById(moneyDepositedEvent.getId()).orElse(null);

        double depositAmount = moneyDepositedEvent.getAmount();
        double currentBalance = account.getBalance() + depositAmount;

        account.setBalance(currentBalance);
        accountRepository.save(account);

    }

    @EventHandler
    public void on(MoneyWithdrawnEvent moneyWithdrawnEvent) {

        Account account = accountRepository.findById(moneyWithdrawnEvent.getId()).orElse(null);

        double withdrawAmount = moneyWithdrawnEvent.getAmount();
        double currentBalance = account.getBalance() - withdrawAmount;

        account.setBalance(currentBalance);
        accountRepository.save(account);

    }

    @EventHandler
    public void on(AccountClosedEvent accountClosedEvent) {
        Account account = accountRepository.findById(accountClosedEvent.getId()).orElse(null);
        accountRepository.delete(account);
    }

}