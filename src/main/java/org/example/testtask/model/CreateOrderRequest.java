package org.example.testtask.model;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(@NotNull List<OrderItemDto> items) {
}
