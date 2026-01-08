package com.pharma.order_service.order.dto;

import com.pharma.order_service.order.enumtype.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private String orderReference; // Added for client visibility
    private String customerId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt; // Added to track status changes
    private List<OrderItemResponse> items;

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemResponse {
        private Long productId;
        private Integer quantity;
        private BigDecimal price;
    }
}