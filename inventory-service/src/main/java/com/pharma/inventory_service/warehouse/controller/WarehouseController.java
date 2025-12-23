package com.pharma.inventory_service.warehouse.controller;

import com.pharma.inventory_service.response.ApiResponse;
import com.pharma.inventory_service.warehouse.dto.CreateWarehouseRequest;
import com.pharma.inventory_service.warehouse.dto.UpdateWarehouseRequest;
import com.pharma.inventory_service.warehouse.dto.WarehouseResponse;
import com.pharma.inventory_service.warehouse.entity.WarehouseStatus;
import com.pharma.inventory_service.warehouse.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<WarehouseResponse> createWarehouse(
            @Valid @RequestBody CreateWarehouseRequest request) {

        return ApiResponse.success(
                "Warehouse created successfully",
                warehouseService.createWarehouse(request)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<WarehouseResponse> updateWarehouse(
            @PathVariable Long id,
            @Valid @RequestBody UpdateWarehouseRequest request) {

        return ApiResponse.success(
                "Warehouse updated successfully",
                warehouseService.updateWarehouse(id, request)
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<WarehouseResponse> getWarehouseById(
            @PathVariable Long id) {

        return ApiResponse.success(
                "Warehouse fetched",
                warehouseService.getWarehouseById(id)
        );
    }

    @GetMapping("/code/{code}")
    public ApiResponse<WarehouseResponse> getWarehouseByCode(
            @PathVariable String code) {

        return ApiResponse.success(
                "Warehouse fetched",
                warehouseService.getWarehouseByCode(code)
        );
    }

    @GetMapping
    public ApiResponse<List<WarehouseResponse>> getAllWarehouses() {

        return ApiResponse.success(
                "Warehouses fetched",
                warehouseService.getAllWarehouses()
        );
    }

    @PatchMapping("/{id}/status/{status}")
    public ApiResponse<WarehouseResponse> changeStatus(
            @PathVariable Long id,
            @PathVariable WarehouseStatus status
    ) {
        warehouseService.changeWarehouseStatus(id, status);
        WarehouseResponse updatedWarehouse = warehouseService.getWarehouseById(id);
        return ApiResponse.success("Warehouse status updated", updatedWarehouse);
    }
}
