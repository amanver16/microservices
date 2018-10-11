package com.aman.microservice.giftcard.controller;

import java.util.List;

import com.aman.microservice.giftcard.entity.CardSummary;
import com.aman.microservice.giftcard.queryhandler.GiftCardQueryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/giftcard")
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages="com.aman.microservice.giftcard.repository")
public class QueryController {

    @Autowired
    private GiftCardQueryService giftCardQueryService;

    @GetMapping("/summary")
    public List<CardSummary> getCardSummary() {
        return giftCardQueryService.handle();
    }

}