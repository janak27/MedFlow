package com.pharma.order_service.service;

import com.pharma.order_service.integration.inventory.InventoryClient;
import com.pharma.order_service.integration.inventory.dto.InventoryReservationResponse;
import com.pharma.order_service.order.dto.CreateOrderItemRequest;
import com.pharma.order_service.order.dto.CreateOrderRequest;
import com.pharma.order_service.order.dto.OrderResponse;
import com.pharma.order_service.order.enumtype.OrderStatus;
import com.pharma.order_service.order.repository.OrderRepository;
import com.pharma.order_service.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @MockBean
    private InventoryClient inventoryClient;

    @Test
    void createOrder_shouldConfirmOrder_whenInventoryIsReserved() {

        InventoryReservationResponse response = new InventoryReservationResponse();
        response.setSuccess(true);
        response.setReason("OK");

        Mockito.when(inventoryClient.reserve(Mockito.any()))
                .thenReturn(response);

        OrderResponse result = orderService.createOrder(createValidRequest());

        assertEquals(OrderStatus.CONFIRMED, result.getStatus());
    }

    private CreateOrderRequest createValidRequest() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerId(String.valueOf(1L));
        request.setOrderReference("ORD-1");

        CreateOrderItemRequest item = new CreateOrderItemRequest();
        item.setProductId(101L);
        item.setQuantity(2);

        request.setItems(new ArrayList<>(List.of(item)));
        return request;
    }
}
