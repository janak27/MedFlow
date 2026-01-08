package com.pharma.order_service.integration.inventory.mapper;

import com.pharma.order_service.integration.inventory.dto.InventoryItemRequest;
import com.pharma.order_service.integration.inventory.dto.InventoryReservationRequest;
import com.pharma.order_service.order.entity.Order;

import java.util.Collections;

public class InventoryMapper {

    public static InventoryReservationRequest toReservationRequest(Order order) {

        return InventoryReservationRequest.builder()
                .orderId(order.getId())
                .orderReference(order.getOrderReference())
                .items(
                        order.getItems().stream()
                                .map(item -> InventoryItemRequest.builder()
                                        .productId(item.getProductId())
                                        .quantity(item.getQuantity())
                                        .build())
                                .toList()
                )
                .build();
    }
}
