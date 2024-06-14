package org.example.testtask.model;

import java.util.List;

public record OrderDto(Long orderId,
                       List<OrderItemDto> items,
                       String status,
                       Double price,
                       Long userId) {
}
