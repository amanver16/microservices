package com.aman.microservice.giftcard.projection;

import java.time.Instant;

import com.aman.microservice.giftcard.entity.CardSummary;
import com.aman.microservice.giftcard.event.CardIssuedEvent;
import com.aman.microservice.giftcard.event.CardRedeemedEvent;
import com.aman.microservice.giftcard.repository.GiftCardRepository;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@ProcessingGroup("ui-query")
@Service
@Slf4j
public class CardSummaryProjection {

    @Autowired
    private GiftCardRepository giftCardRepository;

    @EventHandler
    public void on(CardIssuedEvent cardIssuedEvent, @Timestamp Instant instant) {

        log.debug("Projecting event : ", cardIssuedEvent);
        CardSummary cardSummary = new CardSummary();

        cardSummary.setId(cardIssuedEvent.getId());
        cardSummary.setAmount(cardIssuedEvent.getAmount());
        cardSummary.setInstant(instant);
        cardSummary.setRemainingAmount(cardIssuedEvent.getAmount());

        giftCardRepository.save(cardSummary);
    }

    @EventHandler
    public void on(CardRedeemedEvent cardRedeemedEvent) {

        log.debug("Projecting event : ", cardRedeemedEvent);
        CardSummary cardSummary = giftCardRepository.findById(cardRedeemedEvent.getId()).orElse(null);
        cardSummary.setRemainingAmount(cardSummary.getRemainingAmount() - cardRedeemedEvent.getAmount());

        giftCardRepository.save(cardSummary);
    }

}