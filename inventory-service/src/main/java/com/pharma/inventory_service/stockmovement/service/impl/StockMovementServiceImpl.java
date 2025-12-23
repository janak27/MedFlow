package com.pharma.inventory_service.stockmovement.service.impl;


import com.pharma.inventory_service.exception.BadRequestException;
import com.pharma.inventory_service.exception.ResourceNotFoundException;
import com.pharma.inventory_service.inventory.entity.InventoryBatch;
import com.pharma.inventory_service.inventory.repository.InventoryBatchRepository;
import com.pharma.inventory_service.stockmovement.dto.StockMovementRequest;
import com.pharma.inventory_service.stockmovement.dto.StockMovementResponse;
import com.pharma.inventory_service.stockmovement.entity.StockMovement;
import com.pharma.inventory_service.stockmovement.entity.StockMovementType;
import com.pharma.inventory_service.stockmovement.repository.StockMovementRepository;
import com.pharma.inventory_service.stockmovement.service.StockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockMovementServiceImpl implements StockMovementService {

    private final InventoryBatchRepository inventoryBatchRepository;
    private final StockMovementRepository stockMovementRepository;

    @Override
    @Transactional
    public void addStock(StockMovementRequest request) {
        InventoryBatch batch = getBatch(request.getInventoryBatchId());

        batch.setQuantityAvailable(
                batch.getQuantityAvailable() + request.getQuantity()
        );

        saveMovement(batch, request, StockMovementType.IN);
    }

    @Override
    @Transactional
    public void deductStock(StockMovementRequest request) {
        InventoryBatch batch = getBatch(request.getInventoryBatchId());

        if (batch.getQuantityAvailable() < request.getQuantity()) {
            throw new BadRequestException("Insufficient available stock");
        }

        batch.setQuantityAvailable(
                batch.getQuantityAvailable() - request.getQuantity()
        );

        saveMovement(batch, request, StockMovementType.OUT);
    }

    @Override
    @Transactional
    public void reserveStock(StockMovementRequest request) {
        InventoryBatch batch = getBatch(request.getInventoryBatchId());

        if (batch.getQuantityAvailable() < request.getQuantity()) {
            throw new BadRequestException("Not enough stock to reserve");
        }

        batch.setQuantityAvailable(batch.getQuantityAvailable() - request.getQuantity());
        batch.setQuantityReserved(batch.getQuantityReserved() + request.getQuantity());

        saveMovement(batch, request, StockMovementType.RESERVE);
    }

    @Override
    @Transactional
    public void releaseStock(StockMovementRequest request) {
        InventoryBatch batch = getBatch(request.getInventoryBatchId());

        if (batch.getQuantityReserved() < request.getQuantity()) {
            throw new BadRequestException("Not enough reserved stock to release");
        }

        batch.setQuantityReserved(batch.getQuantityReserved() - request.getQuantity());
        batch.setQuantityAvailable(batch.getQuantityAvailable() + request.getQuantity());

        saveMovement(batch, request, StockMovementType.RELEASE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockMovementResponse> getHistoryByBatch(Long batchId) {
        return stockMovementRepository
                .findByInventoryBatch_IdOrderByCreatedAtDesc(batchId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ---------------- HELPERS ----------------

    private InventoryBatch getBatch(Long id) {
        return inventoryBatchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory batch not found"));
    }

    private void saveMovement(
            InventoryBatch batch,
            StockMovementRequest request,
            StockMovementType type
    ) {
        stockMovementRepository.save(
                StockMovement.builder()
                        .inventoryBatch(batch)
                        .type(type)
                        .quantity(request.getQuantity())
                        .referenceId(request.getReferenceId())
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    private StockMovementResponse toResponse(StockMovement movement) {
        return StockMovementResponse.builder()
                .id(movement.getId())
                .type(movement.getType())
                .quantity(movement.getQuantity())
                .referenceId(movement.getReferenceId())
                .createdAt(movement.getCreatedAt())
                .build();
    }
}
