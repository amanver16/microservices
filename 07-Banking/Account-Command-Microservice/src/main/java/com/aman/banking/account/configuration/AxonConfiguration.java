package com.aman.banking.account.configuration;

import com.aman.banking.account.commandhandler.BankAccount;
import com.mongodb.MongoClient;

import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.mongo.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String mongoDatabseName;

    @Bean
    public EventStorageEngine eventStore(MongoClient mongoClient) {
        return new MongoEventStorageEngine(new DefaultMongoTemplate(mongoClient, mongoDatabseName));
    }

    @Bean
    public Repository<BankAccount> bankAccountRepository(EventStore eventStore) {
        return new EventSourcingRepository<>(BankAccount.class, eventStore);
    }

    @Bean
    public Exchange eventsExchange() {
        return ExchangeBuilder.fanoutExchange("Events").build();
    }

}