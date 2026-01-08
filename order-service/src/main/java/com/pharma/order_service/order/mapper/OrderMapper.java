package com.pharma.order_service.order.mapper;

import com.pharma.order_service.order.dto.OrderResponse;
import com.pharma.order_service.order.entity.Order;

import java.util.Collections;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponse toResponse(Order order) {
        if (order == null) {
            return null;
        }

        return OrderResponse.builder()
                .orderId(order.getId())
                .customerId(order.getCustomerId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                // Defensive check for items
                .items(order.getItems() == null ? Collections.emptyList() :
                        order.getItems().stream()
                                .map(OrderMapper::toItemResponse)
                                .toList() // Immutable is fine for DTOs
                )
                .build();
    }

    // Extracted for readability
    private static OrderResponse.OrderItemResponse toItemResponse(com.pharma.order_service.order.entity.OrderItem item) {
        return OrderResponse.OrderItemResponse.builder()
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build();
    }
}