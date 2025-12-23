package com.pharma.inventory_service.stockmovement.entity;

import com.pharma.inventory_service.inventory.entity.InventoryBatch;
import com.pharma.inventory_service.stockmovement.entity.StockMovementType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which inventory batch changed
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_batch_id", nullable = false)
    private InventoryBatch inventoryBatch;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StockMovementType type;

    @Column(nullable = false)
    private Integer quantity;

    // orderId / purchaseId / returnId (optional but powerful)
    private String referenceId;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
