package com.aman.microservice.giftcard.queryhandler;

import java.util.List;

import com.aman.microservice.giftcard.entity.CardSummary;
import com.aman.microservice.giftcard.repository.GiftCardRepository;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@ProcessingGroup("ui-query")
public class GiftCardQueryService {

    @Autowired
    private GiftCardRepository giftCardRepository;

    @QueryHandler
    public List<CardSummary> handle(){
        log.debug("Handling Query...");
        return giftCardRepository.findAll();
    }

}