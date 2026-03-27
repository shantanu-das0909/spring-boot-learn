package com.spring.event.pubsub.listener;

import com.spring.event.pubsub.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailNotificationListener {

    @EventListener
    private void orderCreatedEventListener(OrderCreatedEvent orderCreatedEvent) {
        log.info("EmailNotificationListener: Email send for the order with id {}", orderCreatedEvent.getOrderId());
    }
}
