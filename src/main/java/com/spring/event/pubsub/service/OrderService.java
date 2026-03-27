package com.spring.event.pubsub.service;

import com.spring.event.pubsub.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

    private final ApplicationEventPublisher applicationEventPublisher;

    public OrderService(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void createOrder(String orderId) {
        OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder()
                .orderId(orderId)
                .build();
        applicationEventPublisher.publishEvent(orderCreatedEvent);
        log.info("OrderService: Order created with id {}", orderId);
    }
}
