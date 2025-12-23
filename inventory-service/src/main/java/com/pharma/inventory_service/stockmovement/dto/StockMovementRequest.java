package com.pharma.inventory_service.stockmovement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockMovementRequest {

    @NotNull
    private Long inventoryBatchId;

    @NotNull
    @Min(1)
    private Integer quantity;

    private String referenceId;
}
