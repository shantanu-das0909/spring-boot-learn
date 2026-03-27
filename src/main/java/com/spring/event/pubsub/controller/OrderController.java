package com.spring.event.pubsub.controller;

import com.spring.event.pubsub.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order/create/{orderId}")
    public ResponseEntity<?> createOrder(@PathVariable String orderId) {
        orderService.createOrder(orderId);
        return ResponseEntity.ok("Order Created with id " + orderId);
    }
}
