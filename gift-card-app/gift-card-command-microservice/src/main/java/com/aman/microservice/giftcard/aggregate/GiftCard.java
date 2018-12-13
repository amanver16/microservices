package com.aman.microservice.giftcard.aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import java.io.Serializable;

import com.aman.microservice.giftcard.command.IssueCardCommand;
import com.aman.microservice.giftcard.command.RedeemCardCommand;
import com.aman.microservice.giftcard.event.CardIssuedEvent;
import com.aman.microservice.giftcard.event.CardRedeemedEvent;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aggregate(repository = "giftcardRepository")
@NoArgsConstructor
@Slf4j
public class GiftCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @AggregateIdentifier
    private String id;

    private int remainingValue;

    @CommandHandler
    public GiftCard(IssueCardCommand issueCardCommand) {

        log.debug("Handling command : " + issueCardCommand);

        if (issueCardCommand.getAmount() <= 0) {
            throw new IllegalArgumentException("Gift card amount must be greater than zero to be issued.");
        }

        apply(new CardIssuedEvent(issueCardCommand.getId(), issueCardCommand.getAmount()));
    }

    @CommandHandler
    public void handle(RedeemCardCommand redeemCardCommand) {

        log.debug("Handling command : " + redeemCardCommand);

        if (redeemCardCommand.getAmount() <= 0) {
            throw new IllegalArgumentException("Gift card amount must be greater than zero to be redeemed.");
        }

        if (redeemCardCommand.getAmount() > this.remainingValue) {
            throw new IllegalStateException("Redeem amount must be less than gift card remaining value.");
        }

        apply(new CardRedeemedEvent(redeemCardCommand.getId(), redeemCardCommand.getAmount()));
    }

    @EventSourcingHandler
    public void on(CardIssuedEvent cardIssuedEvent) {
        this.id = cardIssuedEvent.getId();
        this.remainingValue = cardIssuedEvent.getAmount();
    }

    @EventSourcingHandler
    public void on(CardRedeemedEvent cardRedeemedEvent) {
        this.remainingValue = this.remainingValue - cardRedeemedEvent.getAmount();
    }

}