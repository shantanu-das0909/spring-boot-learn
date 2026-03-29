package com.example.common.practice.modules.order.service;

import com.example.common.practice.modules.order.dto.AllOrdersDto;
import com.example.common.practice.modules.order.dto.CreateOrderRequestDto;
import com.example.common.practice.modules.order.dto.OrderResponseDto;
import com.example.common.practice.modules.order.entity.Order;
import com.example.common.practice.modules.order.repo.OrderRepo;
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

    public OrderResponseDto getOrderById(int id) {
        OrderResponseDto orderResponseDto = null;
        Optional<Order> optionalOrder = orderRepo.findById(id);
        if(optionalOrder.isPresent()) {
            orderResponseDto = OrderResponseDto.builder()
                    .order(optionalOrder.get()).build();
        }
        return orderResponseDto;
    }
}
