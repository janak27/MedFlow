package com.pharma.inventory_service.inventory.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryReservationResponse {

    private boolean success;
    private String reason;
}
