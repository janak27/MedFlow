package com.pharma.inventory_service.stockmovement.service;

import com.pharma.inventory_service.stockmovement.dto.StockMovementRequest;
import com.pharma.inventory_service.stockmovement.dto.StockMovementResponse;

import java.util.List;

public interface StockMovementService {

    void addStock(StockMovementRequest request);

    void deductStock(StockMovementRequest request);

    void reserveStock(StockMovementRequest request);

    void releaseStock(StockMovementRequest request);

    List<StockMovementResponse> getHistoryByBatch(Long batchId);
}

