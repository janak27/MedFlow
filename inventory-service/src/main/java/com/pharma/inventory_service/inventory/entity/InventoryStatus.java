
package com.pharma.inventory_service.inventory.entity;

public enum InventoryStatus {

    ACTIVE,        // Sellable stock
    EXPIRED,       // Expired batch
    BLOCKED,       // Damaged / recalled / audit hold
    OUT_OF_STOCK   // Quantity available = 0
}
