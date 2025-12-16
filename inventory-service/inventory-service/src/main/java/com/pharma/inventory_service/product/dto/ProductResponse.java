package com.pharma.inventory_service.product.dto;

import com.pharma.inventory_service.common.Status;
import com.pharma.inventory_service.common.UnitOfMeasure;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String sku;
    private String manufacturer;
    private UnitOfMeasure unitOfMeasure;
    private BigDecimal costPrice;
    private BigDecimal sellingPrice;
    private Status status;
    private String description;
}
