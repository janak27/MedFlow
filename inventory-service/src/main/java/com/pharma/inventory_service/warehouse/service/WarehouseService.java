package com.pharma.inventory_service.warehouse.service;

import com.pharma.inventory_service.common.Status;
import com.pharma.inventory_service.warehouse.dto.CreateWarehouseRequest;
import com.pharma.inventory_service.warehouse.dto.UpdateWarehouseRequest;
import com.pharma.inventory_service.warehouse.dto.WarehouseResponse;
import com.pharma.inventory_service.warehouse.entity.WarehouseStatus;

import java.util.List;

public interface WarehouseService {

    WarehouseResponse createWarehouse(CreateWarehouseRequest request);

    WarehouseResponse updateWarehouse(Long id, UpdateWarehouseRequest request);

    WarehouseResponse getWarehouseById(Long id);

    WarehouseResponse getWarehouseByCode(String code);

    List<WarehouseResponse> getAllWarehouses();

    WarehouseResponse changeWarehouseStatus(Long id, WarehouseStatus status);
}