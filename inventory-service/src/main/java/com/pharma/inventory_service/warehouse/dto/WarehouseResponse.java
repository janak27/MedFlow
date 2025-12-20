package com.pharma.inventory_service.warehouse.dto;

import com.pharma.inventory_service.common.Status;
import com.pharma.inventory_service.warehouse.entity.WarehouseStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseResponse {

    private Long id;
    private String code;
    private String name;
    private String addressLine;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private WarehouseStatus status;
}
