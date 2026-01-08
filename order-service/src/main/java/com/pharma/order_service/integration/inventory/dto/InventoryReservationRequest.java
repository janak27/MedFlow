package com.pharma.order_service.integration.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
public class InventoryReservationRequest {

    private Long orderId;
    private String orderReference;
    private List<InventoryItemRequest> items;
}