package com.example.common.practice.modules.order.service;

import com.example.common.practice.modules.order.dto.AllOrdersDto;
import com.example.common.practice.modules.order.dto.CreateOrderRequestDto;
import com.example.common.practice.modules.order.dto.OrderResponseDto;
import com.example.common.practice.modules.order.dto.UpdateOrderRequestDto;
import com.example.common.practice.modules.order.entity.Order;
import com.example.common.practice.modules.order.repo.OrderRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public OrderResponseDto createOrder(CreateOrderRequestDto createOrderRequestDto) {
        Order order = Order.builder()
                .productName(createOrderRequestDto.getProductName())
                .price(createOrderRequestDto.getPrice())
                .address(createOrderRequestDto.getAddress()).build();
        Order savedOrder = orderRepo.save(order);
        return OrderResponseDto.builder()
                .order(savedOrder).build();
    }
    public AllOrdersDto getAllOrders() {
        List<Order> allOrders = orderRepo.findAll();
        return AllOrdersDto.builder()
                .orders(allOrders).build();
    }

    @Cacheable(value = "userData", key = "#id")
    public OrderResponseDto getOrderById(int id) {
        OrderResponseDto orderResponseDto = null;
        Optional<Order> optionalOrder = orderRepo.findById(id);
        if(optionalOrder.isPresent()) {
            orderResponseDto = OrderResponseDto.builder()
                    .order(optionalOrder.get()).build();
        }
        return orderResponseDto;
    }

    @CachePut(value = "userData", key = "#id")
    public OrderResponseDto updateOrder(int id, UpdateOrderRequestDto updateOrderRequestDto) {
        Optional<Order> optionalOrder = orderRepo.findById(id);
        optionalOrder.orElseThrow(() -> new RuntimeException("Order not found with id " + id));
        Order order = optionalOrder.get();
        order.setProductName(updateOrderRequestDto.getProductName());
        order.setPrice(updateOrderRequestDto.getPrice());
        order.setAddress(updateOrderRequestDto.getAddress());

        Order savedOrder = orderRepo.save(order);
        return OrderResponseDto.builder()
                .order(savedOrder).build();
    }

    @CacheEvict(value = "userData", key = "#id")
    public String deleteOrder(int id) {
        Optional<Order> optionalOrder = orderRepo.findById(id);
        optionalOrder.orElseThrow(() -> new RuntimeException("Order not found with id " + id));
        Order order = optionalOrder.get();
        orderRepo.delete(order);
        return "Success";
    }
}
