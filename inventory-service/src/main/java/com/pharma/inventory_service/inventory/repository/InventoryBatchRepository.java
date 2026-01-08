package com.pharma.inventory_service.inventory.repository;

import com.pharma.inventory_service.inventory.entity.InventoryBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InventoryBatchRepository extends JpaRepository<InventoryBatch, Long> {

    // Uniqueness check for batch creation
    boolean existsByProduct_IdAndWarehouse_IdAndBatchNumber(
            Long productId,
            Long warehouseId,
            String batchNumber
    );

    // Fetch single inventory batch with product & warehouse
    @Query("""
        SELECT ib
        FROM InventoryBatch ib
        JOIN FETCH ib.product
        JOIN FETCH ib.warehouse
        WHERE ib.id = :id
    """)
    Optional<InventoryBatch> findByIdWithDetails(@Param("id") Long id);

    // Fetch by product + warehouse + batch (used internally)
    Optional<InventoryBatch> findByProduct_IdAndWarehouse_IdAndBatchNumber(
            Long productId,
            Long warehouseId,
            String batchNumber
    );

    // Fetch all inventory for a product (SAFE)
    @Query("""
        SELECT ib
        FROM InventoryBatch ib
        JOIN FETCH ib.product
        JOIN FETCH ib.warehouse
        WHERE ib.product.id = :productId
    """)
    List<InventoryBatch> findByProductIdWithDetails(@Param("productId") Long productId);

    // Fetch all inventory for a warehouse (SAFE)
    @Query("""
        SELECT ib
        FROM InventoryBatch ib
        JOIN FETCH ib.product
        JOIN FETCH ib.warehouse
        WHERE ib.warehouse.id = :warehouseId
    """)
    List<InventoryBatch> findByWarehouseIdWithDetails(@Param("warehouseId") Long warehouseId);

    @Query("""
    SELECT b FROM InventoryBatch b
    WHERE b.product.id = :productId
      AND b.quantityAvailable > 0
      AND b.status = 'ACTIVE'
    ORDER BY b.expiryDate ASC
""")
    List<InventoryBatch> findAvailableBatchesByProductId(Long productId);

}
