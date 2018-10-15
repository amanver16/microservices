package com.aman.banking.account.repository;

import com.aman.banking.account.entity.CustomerBean;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<CustomerBean,String> {

}