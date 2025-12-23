package com.pharma.inventory_service.product.dto;

import com.pharma.inventory_service.common.UnitOfMeasure;
import com.pharma.inventory_service.product.entity.ProductStatus;
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
    private ProductStatus status;
    private String description;
}
