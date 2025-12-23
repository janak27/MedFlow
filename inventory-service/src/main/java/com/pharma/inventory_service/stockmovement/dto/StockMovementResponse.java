package com.pharma.inventory_service.stockmovement.dto;


import com.pharma.inventory_service.stockmovement.entity.StockMovementType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StockMovementResponse {

    private Long id;
    private StockMovementType type;
    private Integer quantity;
    private String referenceId;
    private LocalDateTime createdAt;
}
