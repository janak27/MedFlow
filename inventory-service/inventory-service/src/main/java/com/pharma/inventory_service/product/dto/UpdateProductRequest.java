package com.pharma.inventory_service.product.dto;
import com.pharma.inventory_service.common.UnitOfMeasure;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private String name;
    private String manufacturer;
    private UnitOfMeasure unitOfMeasure;
    private BigDecimal costPrice;
    private BigDecimal sellingPrice;
    private String description;
}
