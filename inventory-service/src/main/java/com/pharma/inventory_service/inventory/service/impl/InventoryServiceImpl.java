package com.pharma.inventory_service.inventory.service.impl;

import com.pharma.inventory_service.exception.BadRequestException;
import com.pharma.inventory_service.exception.ResourceNotFoundException;
import com.pharma.inventory_service.inventory.dto.InventoryItemRequest;
import com.pharma.inventory_service.inventory.dto.InventoryReservationRequest;
import com.pharma.inventory_service.inventory.dto.InventoryReservationResponse;
import com.pharma.inventory_service.inventory.entity.InventoryBatch;
import com.pharma.inventory_service.inventory.entity.InventoryStatus;
import com.pharma.inventory_service.inventory.repository.InventoryBatchRepository;
import com.pharma.inventory_service.inventory.service.InventoryService;
import com.pharma.inventory_service.product.entity.Product;
import com.pharma.inventory_service.product.repository.ProductRepository;
import com.pharma.inventory_service.warehouse.entity.Warehouse;
import com.pharma.inventory_service.warehouse.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryBatchRepository inventoryBatchRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    @Transactional
    public InventoryBatch addInventoryBatch(
            Long productId,
            Long warehouseId,
            String batchNumber,
            Integer quantity,
            LocalDate expiryDate,
            BigDecimal purchasePrice,
            BigDecimal mrp
    ) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        if (inventoryBatchRepository.existsByProduct_IdAndWarehouse_IdAndBatchNumber(
                productId, warehouseId, batchNumber)) {
            throw new BadRequestException("Inventory batch already exists for this product and warehouse");
        }

        if (expiryDate.isBefore(LocalDate.now())) {
            throw new BadRequestException("Expiry date cannot be in the past");
        }

        if (purchasePrice.compareTo(mrp) > 0) {
            throw new BadRequestException("Purchase price cannot be greater than MRP");
        }

        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than zero");
        }

        InventoryBatch batch = InventoryBatch.builder()
                .product(product)
                .warehouse(warehouse)
                .batchNumber(batchNumber)
                .expiryDate(expiryDate)
                .purchasePrice(purchasePrice)
                .mrp(mrp)
                .quantityAvailable(quantity)
                .quantityReserved(0)
                .status(InventoryStatus.ACTIVE)
                .build();

        return inventoryBatchRepository.save(batch);
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryBatch getInventoryById(Long id) {
        return inventoryBatchRepository.findByIdWithDetails(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory batch not found with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryBatch> getInventoryByProduct(Long productId) {
        return inventoryBatchRepository.findByProductIdWithDetails(productId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryBatch> getInventoryByWarehouse(Long warehouseId) {
        return inventoryBatchRepository.findByWarehouseIdWithDetails(warehouseId);
    }

    @Transactional
    public InventoryReservationResponse reserveStock(
            InventoryReservationRequest request) {

        for (InventoryItemRequest item : request.getItems()) {

            int remaining = item.getQuantity();

            List<InventoryBatch> batches =
                    inventoryBatchRepository
                            .findAvailableBatchesByProductId(item.getProductId());

            for (InventoryBatch batch : batches) {
                if (remaining <= 0) break;

                int available = batch.getQuantityAvailable();
                if (available <= 0) continue;

                int toReserve = Math.min(available, remaining);
                batch.reserve(toReserve);
                remaining -= toReserve;
            }

            if (remaining > 0) {
                return new InventoryReservationResponse(
                        false,
                        "INSUFFICIENT_STOCK for productId=" + item.getProductId()
                );
            }
        }

        return new InventoryReservationResponse(true, "RESERVED");
    }




}
