package com.example.common.practice.modules.order.controller;

import com.example.common.practice.modules.order.dto.AllOrdersDto;
import com.example.common.practice.modules.order.dto.CreateOrderRequestDto;
import com.example.common.practice.modules.order.dto.OrderResponseDto;
import com.example.common.practice.modules.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public OrderResponseDto createOrder(@RequestBody CreateOrderRequestDto createOrderRequestDto) {
        return orderService.createOrder(createOrderRequestDto);
    }

    @GetMapping("{id}")
    public OrderResponseDto getOrderById(@PathVariable int id) {
        return orderService.getOrderById(id);
    }

    @GetMapping
    public AllOrdersDto getAllOrders() {
        return orderService.getAllOrders();
    }
}
