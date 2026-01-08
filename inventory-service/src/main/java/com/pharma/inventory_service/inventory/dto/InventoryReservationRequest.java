package com.pharma.inventory_service.inventory.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class InventoryReservationRequest {

    @NotNull
    private Long orderId;

    @NotNull
    private String orderReference;

    @NotNull
    private List<InventoryItemRequest> items;
}