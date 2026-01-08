package com.pharma.order_service.order.service;

import com.pharma.order_service.exception.DuplicateOrderException;
import com.pharma.order_service.integration.inventory.InventoryClient;
import com.pharma.order_service.integration.inventory.dto.InventoryReservationRequest;
import com.pharma.order_service.integration.inventory.dto.InventoryReservationResponse;
import com.pharma.order_service.integration.inventory.mapper.InventoryMapper;
import com.pharma.order_service.order.dto.CreateOrderRequest;
import com.pharma.order_service.order.dto.OrderResponse;
import com.pharma.order_service.order.entity.Order;
import com.pharma.order_service.order.entity.OrderItem;
import com.pharma.order_service.order.enumtype.OrderStatus;
import com.pharma.order_service.order.mapper.OrderMapper;
import com.pharma.order_service.order.repository.OrderRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        // Use the more efficient exists check
        if (orderRepository.existsByOrderReference(request.getOrderReference())) {
            throw new DuplicateOrderException("Order reference '" + request.getOrderReference() + "' already exists.");
        }

        Order order = createOrderEntity(request);

        // Save here to get an ID and persist items before remote call
        orderRepository.save(order);

        boolean inventoryReserved = tryReserveInventory(order, request);

        if (inventoryReserved) {
            order.confirm();
        } else {
            order.fail();
        }

        // The status update is auto-saved at the end of @Transactional method
        return OrderMapper.toResponse(order);
    }

    private Order createOrderEntity(CreateOrderRequest request) {
        Order order = Order.builder()
                .customerId(request.getCustomerId())
                .status(OrderStatus.CREATED)
                .orderReference(request.getOrderReference())
                .items(new ArrayList<>())
                .build();

        request.getItems().forEach(req -> {
            order.addItem(OrderItem.builder()
                    .productId(req.getProductId())
                    .quantity(req.getQuantity())
                    .price(mockPrice())
                    .build());
        });

        order.calculateTotalAmount();
        return order;
    }

    private boolean tryReserveInventory(Order order, CreateOrderRequest request) {
        try {
            InventoryReservationRequest inventoryRequest =
                    InventoryMapper.toReservationRequest(order);

            log.info("Sending to inventory => {}", inventoryRequest);

            InventoryReservationResponse response =
                    inventoryClient.reserve(inventoryRequest);

            return response.isSuccess();
        } catch (FeignException ex) {
            // Extracting the specific error body if available
            String errorBody = ex.contentUTF8();
            log.error("Inventory Service error (Status: {}) for Order: {}. Details: {}",
                    ex.status(), order.getOrderReference(), errorBody);
            return false;
        } catch (Exception ex) {
            log.error("Critical failure calling Inventory Service for Order: {}",
                    order.getOrderReference(), ex);
            return false;
        }
    }

    private void checkDuplicateOrder(String reference) {
        if (orderRepository.existsByOrderReference(reference)) {
            log.warn("Duplicate order attempt detected for reference: {}", reference);
            throw new DuplicateOrderException("Order reference '" + reference + "' already exists.");
        }
    }

    private BigDecimal mockPrice() {
        return new BigDecimal("100.00");
    }
}