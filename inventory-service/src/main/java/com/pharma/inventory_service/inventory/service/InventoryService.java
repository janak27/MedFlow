package com.pharma.inventory_service.inventory.service;

import com.pharma.inventory_service.inventory.dto.InventoryReservationRequest;
import com.pharma.inventory_service.inventory.dto.InventoryReservationResponse;
import com.pharma.inventory_service.inventory.entity.InventoryBatch;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface InventoryService {

    InventoryBatch addInventoryBatch(
            Long productId,
            Long warehouseId,
            String batchNumber,
            Integer quantity,
            LocalDate expiryDate,
            BigDecimal purchasePrice,
            BigDecimal mrp
    );

    InventoryBatch getInventoryById(Long id);

    List<InventoryBatch> getInventoryByProduct(Long productId);

    List<InventoryBatch> getInventoryByWarehouse(Long warehouseId);

    InventoryReservationResponse reserveStock(InventoryReservationRequest request);

}
