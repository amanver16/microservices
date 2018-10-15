package com.aman.banking.account.commandhandler;

import java.io.Serializable;

import com.aman.banking.account.bean.CustomerBean;
import com.aman.banking.account.command.CloseAccountCommand;
import com.aman.banking.account.command.CreateAccountCommand;
import com.aman.banking.account.command.DepositMoneyCommand;
import com.aman.banking.account.command.WithdrawMoneyCommand;
import com.aman.banking.account.event.AccountClosedEvent;
import com.aman.banking.account.event.AccountCreatedEvent;
import com.aman.banking.account.event.MoneyDepositedEvent;
import com.aman.banking.account.event.MoneyWithdrawnEvent;
import com.aman.banking.account.exception.InsufficientBalanceException;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Aggregate(repository = "bankAccountRepository")
@NoArgsConstructor
@Getter
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @AggregateIdentifier
    private String accountNumber;

    private String accountType;
    private double balance;
    private CustomerBean customerBean;

    @CommandHandler
    public BankAccount(CreateAccountCommand createAccountCommand) {

        Assert.hasLength(createAccountCommand.getAccountNumber(), "Account number must not be null !!!");
        Assert.hasLength(createAccountCommand.getAccountType(), "Please specify account type !!!");
        Assert.hasLength(createAccountCommand.getCustomerBean().getName(), "The name is required !!!");
        Assert.hasLength(createAccountCommand.getCustomerBean().getEmail(), "Email is required !!!");
        Assert.isNull(createAccountCommand.getCustomerBean().getPhone(), "Phone is required !!!");
        Assert.hasLength(createAccountCommand.getCustomerBean().getNomineeName(), "Nominee is required !!!");
        Assert.isTrue(createAccountCommand.getBalance() > 500.0,
                "500 minimum balance is required to open an account !!!");

        AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent();

        accountCreatedEvent.setAccountNumber(createAccountCommand.getAccountNumber());
        accountCreatedEvent.setAccountType(createAccountCommand.getAccountType());
        accountCreatedEvent.setBalance(createAccountCommand.getBalance());
        accountCreatedEvent.setCustomerBean(createAccountCommand.getCustomerBean());

        AggregateLifecycle.apply(accountCreatedEvent);
    }

    @CommandHandler
    protected void closeAccount(CloseAccountCommand closeAccountCommand) {
        Assert.hasLength(closeAccountCommand.getAccountNumber(), "Account number is required !!!");
        AccountClosedEvent accountClosedEvent = new AccountClosedEvent();
        accountClosedEvent.setAccountNumber(closeAccountCommand.getAccountNumber());
        accountClosedEvent.setCustomerBean(closeAccountCommand.getCustomerBean());
        AggregateLifecycle.apply(accountClosedEvent);
    }

    @CommandHandler
    protected void depositMoney(DepositMoneyCommand depositMoneyCommand) {

        Assert.hasLength(depositMoneyCommand.getAccountNumber(), "Account number is required !!!");
        Assert.isTrue(depositMoneyCommand.getDepositAmount() >= 0.0, "Deposit amount must be greater than 0 !!!");

        MoneyDepositedEvent moneyDepositedEvent = new MoneyDepositedEvent();

        moneyDepositedEvent.setAccountNumber(depositMoneyCommand.getAccountNumber());
        moneyDepositedEvent.setDepositAmount(depositMoneyCommand.getDepositAmount());

        AggregateLifecycle.apply(moneyDepositedEvent);
    }

    @CommandHandler
    protected void withdrawAmount(WithdrawMoneyCommand withdrawMoneyCommand) {

        Assert.hasLength(withdrawMoneyCommand.getAccountNumber(), "Account number is required !!!");
        Assert.isTrue(withdrawMoneyCommand.getWithdrawAmount() >= 0.0, "Withdraw amount must be greater than 0 !!!");

        if (this.balance - withdrawMoneyCommand.getWithdrawAmount() < 0.0) {
            throw new InsufficientBalanceException("Insufficient balance. Trying to withdraw: "
                    + withdrawMoneyCommand.getWithdrawAmount() + ", but current balance is: " + this.balance);
        }

        MoneyWithdrawnEvent moneyWithdrawnEvent = new MoneyWithdrawnEvent();

        moneyWithdrawnEvent.setAccountNumber(withdrawMoneyCommand.getAccountNumber());
        moneyWithdrawnEvent.setWithdrawAmount(withdrawMoneyCommand.getWithdrawAmount());

        AggregateLifecycle.apply(moneyWithdrawnEvent);
    }

    @EventSourcingHandler
    protected void accountCreated(AccountCreatedEvent accountCreatedEvent) {
        this.accountNumber = accountCreatedEvent.getAccountNumber();
        this.accountType = accountCreatedEvent.getAccountType();
        this.balance = accountCreatedEvent.getBalance();
        this.customerBean = accountCreatedEvent.getCustomerBean();
    }

    @EventSourcingHandler
    protected void accountClosed(AccountClosedEvent accountClosedEvent) {
        AggregateLifecycle.markDeleted();
    }

    @EventSourcingHandler
    protected void moneyDeposited(MoneyDepositedEvent moneyDepositedEvent) {
        this.balance += moneyDepositedEvent.getDepositAmount();
    }

    @EventSourcingHandler
    protected void moneyWithdrawn(MoneyWithdrawnEvent moneyWithdrawnEvent) {
        this.balance -= moneyWithdrawnEvent.getWithdrawAmount();
    }

}
