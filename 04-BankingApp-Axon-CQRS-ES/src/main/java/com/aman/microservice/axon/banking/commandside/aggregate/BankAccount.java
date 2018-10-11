package com.aman.microservice.axon.banking.commandside.aggregate;

import java.io.Serializable;

import com.aman.microservice.axon.banking.commandside.command.CloseAccountCommand;
import com.aman.microservice.axon.banking.commandside.command.CreateAccountCommand;
import com.aman.microservice.axon.banking.commandside.command.DepositMoneyCommand;
import com.aman.microservice.axon.banking.commandside.command.WithdrawMoneyCommand;
import com.aman.microservice.axon.banking.commandside.event.AccountClosedEvent;
import com.aman.microservice.axon.banking.commandside.event.AccountCreatedEvent;
import com.aman.microservice.axon.banking.commandside.event.MoneyDepositedEvent;
import com.aman.microservice.axon.banking.commandside.event.MoneyWithdrawnEvent;
import com.aman.microservice.axon.banking.commandside.exception.InsufficientBalanceException;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
@Getter
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @AggregateIdentifier
    private String id;

    private double balance;

    private String owner;

    @CommandHandler
    public BankAccount(CreateAccountCommand createAccountCommand) {

        String id = createAccountCommand.getId();
        String name = createAccountCommand.getAccountCreator();

        AggregateLifecycle.apply(new AccountCreatedEvent(id, name, 0));
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent accountCreatedEvent) {
        this.id = accountCreatedEvent.getId();
        this.owner = accountCreatedEvent.getAccountCreator();
        this.balance = accountCreatedEvent.getBalance();
    }

    @CommandHandler
    protected void on(CloseAccountCommand closeAccountCommand) {
        AggregateLifecycle.apply(new AccountClosedEvent(id));
    }

    @EventSourcingHandler
    protected void on(AccountClosedEvent accountClosedEvent) {
        AggregateLifecycle.markDeleted();
    }

    @CommandHandler
    protected void on(DepositMoneyCommand depositMoneyCommand) {
        double depositAmount = depositMoneyCommand.getAmount();
        AggregateLifecycle.apply(new MoneyDepositedEvent(id, depositAmount));
    }

    @EventSourcingHandler
    protected void on(MoneyDepositedEvent moneyDepositedEvent) {
        this.balance += moneyDepositedEvent.getAmount();
    }

    @CommandHandler
    protected void on(WithdrawMoneyCommand withdrawMoneyCommand) {

        double withdrawAmount = withdrawMoneyCommand.getAmount();

        if (this.balance - withdrawAmount < 0) {
            throw new InsufficientBalanceException("Insufficient balance. Trying to withdraw: " + withdrawAmount
                    + ", but current balance is: " + this.balance);
        }

        AggregateLifecycle.apply(new MoneyWithdrawnEvent(this.id, withdrawAmount));
    }

    @EventSourcingHandler
    protected void on(MoneyWithdrawnEvent moneyWithdrawnEvent) {
        this.balance -= moneyWithdrawnEvent.getAmount();
    }

}
