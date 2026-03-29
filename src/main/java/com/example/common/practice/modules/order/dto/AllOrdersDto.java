package com.example.common.practice.modules.order.dto;

import com.example.common.practice.modules.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllOrdersDto {
    List<Order> orders;
}
