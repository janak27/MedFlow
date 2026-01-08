package com.pharma.order_service.integration.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // Essential for Jackson JSON deserialization
@AllArgsConstructor
public class InventoryReservationResponse {
    private boolean success;
    private String reason;
}