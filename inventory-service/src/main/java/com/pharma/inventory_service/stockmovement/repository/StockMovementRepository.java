package com.pharma.inventory_service.stockmovement.repository;


import com.pharma.inventory_service.stockmovement.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    List<StockMovement> findByInventoryBatch_IdOrderByCreatedAtDesc(Long batchId);
}
