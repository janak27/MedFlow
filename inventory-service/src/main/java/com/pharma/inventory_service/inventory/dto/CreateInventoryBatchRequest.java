package com.pharma.inventory_service.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CreateInventoryBatchRequest {

    @NotNull
    private Long productId;

    @NotNull
    private Long warehouseId;

    @NotBlank
    private String batchNumber;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    private LocalDate expiryDate;

    @NotNull
    private BigDecimal purchasePrice;

    @NotNull
    private BigDecimal mrp;
}
