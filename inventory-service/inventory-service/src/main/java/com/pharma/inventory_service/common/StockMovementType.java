package com.pharma.inventory_service.common;

public enum StockMovementType {
    IN,          // Purchase / Stock addition
    OUT,         // Sale / Consumption
    ADJUSTMENT,  // Manual correction
    TRANSFER,    // Warehouse transfer (future)
    EXPIRED      // Expired stock removal
}