package com.aman.microservice.giftcard.repository;

import com.aman.microservice.giftcard.entity.CardSummary;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCardRepository extends MongoRepository<CardSummary, String> {

}