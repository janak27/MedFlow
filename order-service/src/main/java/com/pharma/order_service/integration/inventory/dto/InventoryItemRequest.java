package com.pharma.order_service.integration.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // Essential for Jackson deserialization
@AllArgsConstructor
public class InventoryItemRequest {
    private Long productId;
    private Integer quantity;
}
