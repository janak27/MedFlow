package com.pharma.inventory_service.warehouse.service.Impl;

import com.pharma.inventory_service.exception.ResourceNotFoundException;
import com.pharma.inventory_service.exception.BadRequestException;
import com.pharma.inventory_service.warehouse.dto.CreateWarehouseRequest;
import com.pharma.inventory_service.warehouse.dto.UpdateWarehouseRequest;
import com.pharma.inventory_service.warehouse.dto.WarehouseResponse;
import com.pharma.inventory_service.warehouse.entity.Warehouse;
import com.pharma.inventory_service.warehouse.entity.WarehouseStatus;
import com.pharma.inventory_service.warehouse.repository.WarehouseRepository;
import com.pharma.inventory_service.warehouse.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    @Override
    public WarehouseResponse createWarehouse(CreateWarehouseRequest request) {

        // Rule 1: Unique warehouse code
        if (warehouseRepository.existsByCode(request.getCode())) {
            throw new BadRequestException("Warehouse code already exists");
        }

        Warehouse warehouse = Warehouse.builder()
                .code(request.getCode())
                .name(request.getName())
                .addressLine(request.getAddressLine())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .pincode(request.getPincode())
                .status(WarehouseStatus.ACTIVE)
                .build();

        Warehouse saved = warehouseRepository.save(warehouse);

        return toResponse(saved);
    }

    @Override
    public WarehouseResponse updateWarehouse(Long id, UpdateWarehouseRequest request) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        // Business decision: code & status are NOT updatable here
        warehouse.setName(request.getName());
        warehouse.setAddressLine(request.getAddressLine());
        warehouse.setCity(request.getCity());
        warehouse.setState(request.getState());
        warehouse.setCountry(request.getCountry());
        warehouse.setPincode(request.getPincode());

        Warehouse updated = warehouseRepository.save(warehouse);

        return toResponse(updated);
    }

    @Override
    public WarehouseResponse getWarehouseById(Long id) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        return toResponse(warehouse);
    }

    @Override
    public WarehouseResponse getWarehouseByCode(String code) {

        Warehouse warehouse = warehouseRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        return toResponse(warehouse);
    }

    @Override
    public List<WarehouseResponse> getAllWarehouses() {

        return warehouseRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public WarehouseResponse changeWarehouseStatus(Long id, WarehouseStatus status) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        warehouse.setStatus(status);

        Warehouse updated = warehouseRepository.save(warehouse);

        return toResponse(updated);
    }

    // ---- Manual mapping (intentional) ----
    private WarehouseResponse toResponse(Warehouse warehouse) {
        return WarehouseResponse.builder()
                .id(warehouse.getId())
                .code(warehouse.getCode())
                .name(warehouse.getName())
                .addressLine(warehouse.getAddressLine())
                .city(warehouse.getCity())
                .state(warehouse.getState())
                .country(warehouse.getCountry())
                .pincode(warehouse.getPincode())
                .status(warehouse.getStatus())
                .build();
    }
}

