package com.aman.banking.account.eventhandler;

import java.time.Instant;

import com.aman.banking.account.entity.CustomerBean;
import com.aman.banking.account.event.AccountClosedEvent;
import com.aman.banking.account.event.AccountCreatedEvent;
import com.aman.banking.account.event.MoneyDepositedEvent;
import com.aman.banking.account.event.MoneyWithdrawnEvent;
import com.aman.banking.account.repository.CustomerRepository;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@ProcessingGroup("account-query")
@Service
@Slf4j
public class AccountProjection {

    @Autowired
    private CustomerRepository customerRepository;

    @EventHandler
    public void handleAccountCreated(AccountCreatedEvent accountCreatedEvent, @Timestamp Instant createdTime) {

        log.info("Handling event: " + accountCreatedEvent);
        CustomerBean customerBean = new CustomerBean();

        customerBean.setAccountNumber(accountCreatedEvent.getAccountNumber());
        customerBean.setAccountType(accountCreatedEvent.getAccountType());
        customerBean.setBalance(accountCreatedEvent.getBalance());
        customerBean.setName(accountCreatedEvent.getCustomerBean().getName());
        customerBean.setGender(accountCreatedEvent.getCustomerBean().getGender());
        customerBean.setDateOfBirth(accountCreatedEvent.getCustomerBean().getDateOfBirth());
        customerBean.setAddress(accountCreatedEvent.getCustomerBean().getAddress());
        customerBean.setEmail(accountCreatedEvent.getCustomerBean().getEmail());
        customerBean.setPhone(accountCreatedEvent.getCustomerBean().getPhone());
        customerBean.setNomineeName(accountCreatedEvent.getCustomerBean().getNomineeName());
        customerBean.setCreatedDate(createdTime);

        customerRepository.save(customerBean);

    }

    @EventHandler
    public void handleAccountClosed(AccountClosedEvent accountClosedEvent) {
        log.info("Handling event: " + accountClosedEvent);
        CustomerBean customerBean = customerRepository.findById(accountClosedEvent.getAccountNumber()).orElse(null);
        customerRepository.delete(customerBean);
    }

    @EventHandler
    public void handleMoneyDeposited(MoneyDepositedEvent moneyDepositedEvent) {

        log.info("Handling event: " + moneyDepositedEvent);
        CustomerBean customerBean = customerRepository.findById(moneyDepositedEvent.getAccountNumber()).orElse(null);

        double currentBalance = customerBean.getBalance();
        double depositAmount = currentBalance + moneyDepositedEvent.getDepositAmount();

        customerBean.setBalance(depositAmount);
        customerRepository.save(customerBean);
    }

    @EventHandler
    public void handleMoneyWithdrawn(MoneyWithdrawnEvent moneyWithdrawnEvent) {

        log.info("Handling event: " + moneyWithdrawnEvent);
        CustomerBean customerBean = customerRepository.findById(moneyWithdrawnEvent.getAccountNumber()).orElse(null);

        double withdrawAmount = moneyWithdrawnEvent.getWithdrawAmount();
        double currentBalance = customerBean.getBalance();
        double finalBalance = currentBalance - withdrawAmount;

        customerBean.setBalance(finalBalance);
        customerRepository.save(customerBean);
    }

}