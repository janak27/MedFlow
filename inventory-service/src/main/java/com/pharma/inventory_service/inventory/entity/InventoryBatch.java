package com.pharma.inventory_service.inventory.entity;

import com.pharma.inventory_service.product.entity.Product;
import com.pharma.inventory_service.warehouse.entity.Warehouse;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "inventory_batches",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"product_id", "warehouse_id", "batch_number"}
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // WHAT
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // WHERE
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    // BATCH INFO
    @Column(name = "batch_number", nullable = false, length = 100)
    private String batchNumber;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    // PRICING
    @Column(name = "purchase_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "mrp", nullable = false, precision = 10, scale = 2)
    private BigDecimal mrp;

    // STOCK
    @Column(name = "quantity_available", nullable = false)
    private Integer quantityAvailable;

    @Column(name = "quantity_reserved", nullable = false)
    private Integer quantityReserved;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InventoryStatus status;

    /**
     * Safety net:
     * Ensures quantities are never null
     */
    @PrePersist
    public void prePersist() {
        if (quantityAvailable == null) {
            quantityAvailable = 0;
        }
        if (quantityReserved == null) {
            quantityReserved = 0;
        }
        if (status == null) {
            status = InventoryStatus.ACTIVE;
        }
    }

    public void reserve(int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        if (quantity > this.quantityAvailable) {
            throw new IllegalStateException("Insufficient stock in batch");
        }

        this.quantityAvailable -= quantity;
        this.quantityReserved += quantity;
    }

}
