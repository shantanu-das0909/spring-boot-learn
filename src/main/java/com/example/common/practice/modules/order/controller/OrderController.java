package com.example.common.practice.modules.order.controller;

import com.example.common.practice.modules.order.dto.AllOrdersDto;
import com.example.common.practice.modules.order.dto.CreateOrderRequestDto;
import com.example.common.practice.modules.order.dto.OrderResponseDto;
import com.example.common.practice.modules.order.dto.UpdateOrderRequestDto;
import com.example.common.practice.modules.order.service.CacheInspectionService;
import com.example.common.practice.modules.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CacheInspectionService cacheInspectionService;

    @PostMapping("/create")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto createOrderRequestDto) {
        return ResponseEntity.ok().body(orderService.createOrder(createOrderRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable int id) {
        return ResponseEntity.ok().body(orderService.getOrderById(id));
    }

    @GetMapping
    public ResponseEntity<AllOrdersDto> getAllOrders() {
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @RequestBody UpdateOrderRequestDto updateOrderRequestDto,
            @PathVariable int id
    ) {
        return ResponseEntity.ok().body(orderService.updateOrder(id, updateOrderRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable int id){
        orderService.deleteOrder(id);
        return ResponseEntity.ok().body("Success");
    }

    @GetMapping("/cache/{cacheName}")
    public void getCacheData(@PathVariable String cacheName) {
        cacheInspectionService.printCacheContent(cacheName);
    }
}
