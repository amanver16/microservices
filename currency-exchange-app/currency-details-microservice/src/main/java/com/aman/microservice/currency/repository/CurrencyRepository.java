package com.aman.microservice.currency.repository;

import com.aman.microservice.currency.entity.Currency;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends MongoRepository<Currency, String> {

    public Currency findByFromAndTo(String from, String to);

}