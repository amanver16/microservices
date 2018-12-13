package com.aman.microservice.axon.banking.commandside.aggregate;

import com.aman.microservice.axon.banking.commandside.command.CloseAccountCommand;
import com.aman.microservice.axon.banking.commandside.command.CreateAccountCommand;
import com.aman.microservice.axon.banking.commandside.command.DepositMoneyCommand;
import com.aman.microservice.axon.banking.commandside.command.WithdrawMoneyCommand;
import com.aman.microservice.axon.banking.commandside.event.AccountClosedEvent;
import com.aman.microservice.axon.banking.commandside.event.AccountCreatedEvent;
import com.aman.microservice.axon.banking.commandside.event.MoneyDepositedEvent;
import com.aman.microservice.axon.banking.commandside.event.MoneyWithdrawnEvent;
import com.aman.microservice.axon.banking.commandside.exception.InsufficientBalanceException;

import org.axonframework.commandhandling.model.AggregateNotFoundException;
import org.axonframework.eventsourcing.eventstore.EventStoreException;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BankAccountTest {

        private FixtureConfiguration<?> fixtureConfiguration;

        @Before
        public void setUp() {
                fixtureConfiguration = new AggregateTestFixture<>(BankAccount.class);
        }

        @Test
        public void createAccount() {
                fixtureConfiguration.given().when(new CreateAccountCommand("ACC-000009", "Dheeraj"))
                                .expectSuccessfulHandlerExecution()
                                .expectEvents(new AccountCreatedEvent("ACC-000009", "Dheeraj", 0.0));
        }

        @Test
        public void createExistingAccount() {
                fixtureConfiguration.given(new AccountCreatedEvent("ACC-000009", "Dheeraj", 0.0))
                                .when(new CreateAccountCommand("ACC-000009", "Dheeraj"))
                                .expectException(EventStoreException.class);
        }

        @Test
        public void depositMoney() {
                fixtureConfiguration.given(new AccountCreatedEvent("ACC-000009", "Dheeraj", 0.0))
                                .when(new DepositMoneyCommand("ACC-000009", 5000)).expectSuccessfulHandlerExecution()
                                .expectEvents(new MoneyDepositedEvent("ACC-000009", 5000));
        }

        @Test
        public void depositMoneyInNonExistingAccount() {
                fixtureConfiguration.given().when(new DepositMoneyCommand("ACC-000008", 5000))
                                .expectException(AggregateNotFoundException.class);
        }

        @Test
        public void withdrawMoney() {
                fixtureConfiguration
                                .given(new AccountCreatedEvent("ACC-000009", "Dheeraj", 0.0),
                                                new MoneyDepositedEvent("ACC-000009", 5000))
                                .when(new WithdrawMoneyCommand("ACC-000009", 2000)).expectSuccessfulHandlerExecution()
                                .expectEvents(new MoneyWithdrawnEvent("ACC-000009", 2000));
        }

        @Test
        public void overdrawMoney() {
                fixtureConfiguration
                                .given(new AccountCreatedEvent("ACC-000009", "Dheeraj", 0.0),
                                                new MoneyDepositedEvent("ACC-000009", 5000))
                                .when(new WithdrawMoneyCommand("ACC-000009", 10000))
                                .expectException(InsufficientBalanceException.class);
        }

        @Test
        public void closeAccount() {
                fixtureConfiguration
                                .given(new AccountCreatedEvent("ACC-000009", "Dheeraj", 0.0),
                                                new MoneyDepositedEvent("ACC-000009", 5000))
                                .when(new CloseAccountCommand("ACC-000009")).expectSuccessfulHandlerExecution()
                                .expectEvents(new AccountClosedEvent("ACC-000009"));
        }

}