package com.pharma.inventory_service.warehouse.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateWarehouseRequest {

    @NotBlank(message = "Warehouse name is required")
    private String name;

    private String addressLine;
    private String city;
    private String state;
    private String country;
    private String pincode;
}
