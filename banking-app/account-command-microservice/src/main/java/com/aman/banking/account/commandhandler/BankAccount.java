package com.aman.banking.account.commandhandler;

import java.io.Serializable;

import com.aman.banking.account.bean.CustomerBean;
import com.aman.banking.account.command.CloseAccountCommand;
import com.aman.banking.account.command.CreateAccountCommand;
import com.aman.banking.account.command.DepositMoneyCommand;
import com.aman.banking.account.command.TransferMoneyCommand;
import com.aman.banking.account.command.WithdrawMoneyCommand;
import com.aman.banking.account.event.AccountClosedEvent;
import com.aman.banking.account.event.AccountCreatedEvent;
import com.aman.banking.account.event.MoneyDepositedEvent;
import com.aman.banking.account.event.MoneyTransferredEvent;
import com.aman.banking.account.event.MoneyWithdrawnEvent;
import com.aman.banking.account.exception.InsufficientBalanceException;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.commandhandling.model.ForwardMatchingInstances;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Aggregate(repository = "bankAccountRepository")
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @AggregateIdentifier
    private String accountNumber;

    private String accountType;
    private double balance;

    @AggregateMember(eventForwardingMode=ForwardMatchingInstances.class)
    private CustomerBean customerBean;

    @CommandHandler
    public BankAccount(CreateAccountCommand createAccountCommand) {

        Assert.hasLength(createAccountCommand.getAccountNumber(), "Account number must not be null !!!");
        Assert.hasLength(createAccountCommand.getAccountType(), "Please specify account type !!!");
        Assert.hasLength(createAccountCommand.getCustomerBean().getName(), "The name is required !!!");
        Assert.hasLength(createAccountCommand.getCustomerBean().getEmail(), "Email is required !!!");
        Assert.isTrue(createAccountCommand.getCustomerBean().getPhone() > 0, "Phone is required !!!");
        Assert.hasLength(createAccountCommand.getCustomerBean().getNomineeName(), "Nominee is required !!!");

        AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent();

        accountCreatedEvent.setAccountNumber(createAccountCommand.getAccountNumber());
        accountCreatedEvent.setAccountType(createAccountCommand.getAccountType());
        accountCreatedEvent.setBalance(createAccountCommand.getBalance());
        accountCreatedEvent.setCustomerBean(createAccountCommand.getCustomerBean());

        AggregateLifecycle.apply(accountCreatedEvent);
    }

    @CommandHandler
    public void closeAccount(CloseAccountCommand closeAccountCommand) {
        Assert.hasLength(closeAccountCommand.getAccountNumber(), "Account number is required !!!");
        AccountClosedEvent accountClosedEvent = new AccountClosedEvent();
        accountClosedEvent.setAccountNumber(closeAccountCommand.getAccountNumber());
        AggregateLifecycle.apply(accountClosedEvent);
    }

    @CommandHandler
    public void depositMoney(DepositMoneyCommand depositMoneyCommand) {

        Assert.hasLength(depositMoneyCommand.getAccountNumber(), "Account number is required !!!");
        Assert.isTrue(depositMoneyCommand.getDepositAmount() >= 0.0, "Deposit amount must be greater than 0 !!!");

        MoneyDepositedEvent moneyDepositedEvent = new MoneyDepositedEvent();

        moneyDepositedEvent.setAccountNumber(depositMoneyCommand.getAccountNumber());
        moneyDepositedEvent.setDepositAmount(depositMoneyCommand.getDepositAmount());

        AggregateLifecycle.apply(moneyDepositedEvent);
    }

    @CommandHandler
    public void withdrawAmount(WithdrawMoneyCommand withdrawMoneyCommand) {

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

    // @CommandHandler
    // public void transferMoney(TransferMoneyCommand transferMoneyCommand) {

    //     Assert.hasLength(transferMoneyCommand.getSenderAccountNo(), "Sender Account number is required !!!");
    //     Assert.hasLength(transferMoneyCommand.getRecieverAccountNo(), "Reciever Account number is required !!!");
    //     Assert.isTrue(transferMoneyCommand.getTransferAmount() >= 0.0, "Transfer amount must be greater than 0 !!!");

    //     if (this.balance - transferMoneyCommand.getTransferAmount() < 0.0) {
    //         throw new InsufficientBalanceException("Insufficient balance. Trying to transfer: "
    //                 + transferMoneyCommand.getTransferAmount() + ", but current balance is: " + this.balance);
    //     }

    //     MoneyTransferredEvent moneyTransferredEvent = new MoneyTransferredEvent();
    //     moneyTransferredEvent.setSenderAccountNo(transferMoneyCommand.getSenderAccountNo());
    //     moneyTransferredEvent.setRecieverAccountNo(transferMoneyCommand.getRecieverAccountNo());
    //     moneyTransferredEvent.setTransferAmount(transferMoneyCommand.getTransferAmount());

    //     AggregateLifecycle.apply(moneyTransferredEvent);
    // }

    @EventSourcingHandler
    public void accountCreated(AccountCreatedEvent accountCreatedEvent) {
        this.accountNumber = accountCreatedEvent.getAccountNumber();
        this.accountType = accountCreatedEvent.getAccountType();
        this.balance = accountCreatedEvent.getBalance();
        this.customerBean = accountCreatedEvent.getCustomerBean();
    }

    @EventSourcingHandler
    public void accountClosed(AccountClosedEvent accountClosedEvent) {
        AggregateLifecycle.markDeleted();
    }

    @EventSourcingHandler
    public void moneyDeposited(MoneyDepositedEvent moneyDepositedEvent) {
        this.balance += moneyDepositedEvent.getDepositAmount();
    }

    @EventSourcingHandler
    public void moneyWithdrawn(MoneyWithdrawnEvent moneyWithdrawnEvent) {
        this.balance -= moneyWithdrawnEvent.getWithdrawAmount();
    }

    // @EventSourcingHandler
    // public void moneyTransferred(MoneyTransferredEvent moneyTransferredEvent) {
    //     this.balance -= moneyTransferredEvent.getTransferAmount();
    // }

}
