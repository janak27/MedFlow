package com.pharma.inventory_service.inventory.dto;

import com.pharma.inventory_service.inventory.entity.InventoryStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class InventoryBatchResponse {

    private Long id;

    private Long productId;
    private String productName;

    private Long warehouseId;
    private String warehouseCode;

    private String batchNumber;
    private LocalDate expiryDate;

    private BigDecimal purchasePrice;
    private BigDecimal mrp;

    private Integer quantityAvailable;
    private Integer quantityReserved;

    private InventoryStatus status;
}
