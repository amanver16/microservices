package com.aman.microservice.axon.banking.queryside.repository;

import com.aman.microservice.axon.banking.queryside.entity.Account;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account,String> {

}