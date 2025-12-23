package com.pharma.inventory_service.stockmovement.controller;

import com.pharma.inventory_service.response.ApiResponse;
import com.pharma.inventory_service.stockmovement.dto.StockMovementRequest;
import com.pharma.inventory_service.stockmovement.dto.StockMovementResponse;
import com.pharma.inventory_service.stockmovement.service.StockMovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @PostMapping("/in")
    public ApiResponse<StockMovementResponse> addStock(@Valid @RequestBody StockMovementRequest request) {
        stockMovementService.addStock(request);
        return ApiResponse.success("Stock added", null);
    }

    @PostMapping("/out")
    public ApiResponse<Void> deductStock(@Valid @RequestBody StockMovementRequest request) {
        stockMovementService.deductStock(request);
        return ApiResponse.success("Stock deducted", null);
    }

    @PostMapping("/reserve")
    public ApiResponse<Void> reserveStock(@Valid @RequestBody StockMovementRequest request) {
        stockMovementService.reserveStock(request);
        return ApiResponse.success("Stock reserved", null);
    }

    @PostMapping("/release")
    public ApiResponse<Void> releaseStock(@Valid @RequestBody StockMovementRequest request) {
        stockMovementService.releaseStock(request);
        return ApiResponse.success("Stock released", null);
    }

    @GetMapping("/history/{batchId}")
    public ApiResponse<List<StockMovementResponse>> history(@PathVariable Long batchId) {
        return ApiResponse.success(
                "Stock movement history",
                stockMovementService.getHistoryByBatch(batchId)
        );
    }
}
