package org.example.testtask.service;

import org.example.testtask.model.CreateOrderRequest;
import org.example.testtask.model.OrderDto;
import org.example.testtask.security.CustomUserDetails;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(CreateOrderRequest request);

    List<OrderDto> getOrders();

    OrderDto getOrder(Long orderId);
}
