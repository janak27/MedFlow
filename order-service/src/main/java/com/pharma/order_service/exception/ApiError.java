package com.pharma.order_service.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ApiError {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
