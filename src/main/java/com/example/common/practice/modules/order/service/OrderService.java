package com.example.common.practice.modules.order.service;

import com.example.common.practice.modules.order.dto.AllOrdersDto;
import com.example.common.practice.modules.order.dto.CreateOrderRequestDto;
import com.example.common.practice.modules.order.dto.OrderResponseDto;
import com.example.common.practice.modules.order.dto.UpdateOrderRequestDto;
import com.example.common.practice.modules.order.entity.Order;
import com.example.common.practice.modules.order.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final String USER_DATA = "userData";

    private final OrderRepo orderRepo;

    @CachePut(value = USER_DATA, key = "#result.id")
    public OrderResponseDto createOrder(CreateOrderRequestDto createOrderRequestDto) {
        Order order = Order.builder()
                .productName(createOrderRequestDto.getProductName())
                .price(createOrderRequestDto.getPrice())
                .address(createOrderRequestDto.getAddress()).build();
        Order savedOrder = orderRepo.save(order);
        return getOrderDto(savedOrder);
    }

    public AllOrdersDto getAllOrders() {
        List<Order> allOrders = orderRepo.findAll();
        return AllOrdersDto.builder()
                .orders(allOrders).build();
    }

    @Cacheable(value = USER_DATA, key = "#id")
    public OrderResponseDto getOrderById(int id) {
        return orderRepo.findById(id).map(this::getOrderDto).orElse(new OrderResponseDto());
    }

    @CachePut(value = USER_DATA, key = "#id")
    public OrderResponseDto updateOrder(int id, UpdateOrderRequestDto updateOrderRequestDto) {
        Optional<Order> optionalOrder = orderRepo.findById(id);
        optionalOrder.orElseThrow(() -> new RuntimeException("Order not found with id " + id));
        Order order = optionalOrder.get();
        order.setProductName(updateOrderRequestDto.getProductName());
        order.setPrice(updateOrderRequestDto.getPrice());
        order.setAddress(updateOrderRequestDto.getAddress());

        Order savedOrder = orderRepo.save(order);
        return getOrderDto(savedOrder);
    }

    @CacheEvict(value = USER_DATA, key = "#id")
    public void deleteOrder(int id) {
        Optional<Order> optionalOrder = orderRepo.findById(id);
        optionalOrder.orElseThrow(() -> new RuntimeException("Order not found with id " + id));
        Order order = optionalOrder.get();
        orderRepo.delete(order);
    }

    private OrderResponseDto getOrderDto(Order savedOrder) {
        return OrderResponseDto.builder()
                .id(savedOrder.getId())
                .productName(savedOrder.getProductName())
                .price(savedOrder.getPrice())
                .address(savedOrder.getAddress()).build();
    }
}
