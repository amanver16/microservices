package com.aman.microservice.giftcard.aggregate;

import com.aman.microservice.giftcard.command.IssueCardCommand;
import com.aman.microservice.giftcard.event.CardIssuedEvent;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GiftCardTest {

    private FixtureConfiguration<?> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(GiftCard.class);
    }

    @Test
    public void issueCard() {
        fixture.given().when(new IssueCardCommand("Gift-001", 1000)).expectSuccessfulHandlerExecution()
                .expectEvents(new CardIssuedEvent("Gift-001", 1000));
    }

}