package com.pharma.order_service.integration.inventory;


import com.pharma.order_service.integration.inventory.dto.InventoryReservationRequest;
import com.pharma.order_service.integration.inventory.dto.InventoryReservationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "inventory-service",
        url = "${inventory.service.url}",
        fallback = InventoryClientFallback.class // Optional: Requires Resilience4j
)
public interface InventoryClient {

    @PostMapping("/api/inventory/reserve")
    InventoryReservationResponse reserve(@RequestBody InventoryReservationRequest request);
}



// Fallback class to handle service downtime gracefully
@Component
class InventoryClientFallback implements InventoryClient {
    @Override
    public InventoryReservationResponse reserve(InventoryReservationRequest request) {
        // Return a "Failure" response instead of crashing
        return new InventoryReservationResponse(false, "Inventory Service is currently unavailable");
    }
}