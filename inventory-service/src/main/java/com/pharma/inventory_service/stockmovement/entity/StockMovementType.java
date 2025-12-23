package com.pharma.inventory_service.stockmovement.entity;



public enum StockMovementType {
    IN,        // Purchase / Return
    OUT,       // Sale / Damage / Expiry
    RESERVE,   // Order placed
    RELEASE    // Order cancelled
}
