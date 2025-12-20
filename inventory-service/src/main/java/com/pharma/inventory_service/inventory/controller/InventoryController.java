package com.pharma.inventory_service.inventory.controller;

import com.pharma.inventory_service.inventory.dto.CreateInventoryBatchRequest;
import com.pharma.inventory_service.inventory.dto.InventoryBatchResponse;
import com.pharma.inventory_service.inventory.entity.InventoryBatch;
import com.pharma.inventory_service.inventory.service.InventoryService;
import com.pharma.inventory_service.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // 1️⃣ Add inventory batch
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<InventoryBatchResponse> addInventory(
            @Valid @RequestBody CreateInventoryBatchRequest request) {

        InventoryBatch batch = inventoryService.addInventoryBatch(
                request.getProductId(),
                request.getWarehouseId(),
                request.getBatchNumber(),
                request.getQuantity(),
                request.getExpiryDate(),          // LocalDate
                request.getPurchasePrice(),       // BigDecimal
                request.getMrp()                  // BigDecimal
        );

        return ApiResponse.success(
                "Inventory batch added successfully",
                toResponse(batch)
        );
    }

    // 2️⃣ Get inventory by product
    @GetMapping("/product/{productId}")
    public ApiResponse<List<InventoryBatchResponse>> getInventoryByProduct(
            @PathVariable Long productId) {

        return ApiResponse.success(
                "Inventory fetched",
                inventoryService.getInventoryByProduct(productId)
                        .stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList())
        );
    }

    // 3️⃣ Get inventory by warehouse
    @GetMapping("/warehouse/{warehouseId}")
    public ApiResponse<List<InventoryBatchResponse>> getInventoryByWarehouse(
            @PathVariable Long warehouseId) {

        return ApiResponse.success(
                "Inventory fetched",
                inventoryService.getInventoryByWarehouse(warehouseId)
                        .stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList())
        );
    }

    // 4️⃣ Get inventory batch by ID
    @GetMapping("/{id}")
    public ApiResponse<InventoryBatchResponse> getInventoryById(
            @PathVariable Long id) {

        InventoryBatch batch = inventoryService.getInventoryById(id);

        return ApiResponse.success(
                "Inventory batch fetched",
                toResponse(batch)
        );
    }

    // ---- Manual mapper (intentional & safe) ----
    private InventoryBatchResponse toResponse(InventoryBatch batch) {
        return InventoryBatchResponse.builder()
                .id(batch.getId())
                .productId(batch.getProduct().getId())
                .productName(batch.getProduct().getName())
                .warehouseId(batch.getWarehouse().getId())
                .warehouseCode(batch.getWarehouse().getCode())
                .batchNumber(batch.getBatchNumber())
                .expiryDate(batch.getExpiryDate())
                .purchasePrice(batch.getPurchasePrice())
                .mrp(batch.getMrp())
                .quantityAvailable(batch.getQuantityAvailable())
                .quantityReserved(batch.getQuantityReserved())
                .status(batch.getStatus())
                .build();
    }
}
