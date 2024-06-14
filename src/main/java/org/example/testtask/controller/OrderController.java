package org.example.testtask.controller;

import org.example.testtask.model.CreateOrderRequest;
import org.example.testtask.model.OrderDto;
import org.example.testtask.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Validated @RequestBody CreateOrderRequest request) {
        OrderDto response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderDto> orders = orderService.getOrders();
        return ResponseEntity.status(HttpStatus.OK)
                .body(orders);
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        OrderDto order = orderService.getOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(order);
    }
}