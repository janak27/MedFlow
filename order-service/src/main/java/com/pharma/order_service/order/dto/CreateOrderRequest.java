package com.pharma.order_service.order.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    @NotBlank(message = "Order reference must not be blank")
    private String orderReference;

    @NotBlank(message = "Customer ID must not be blank")
    private String customerId;

    @NotEmpty(message = "Order must contain at least one item")
    @Valid // Critical: Triggers validation on each item in the list
    private List<CreateOrderItemRequest> items;
}
