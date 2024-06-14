package org.example.testtask.utils;

import org.example.testtask.entity.Order;
import org.example.testtask.entity.OrderItem;
import org.example.testtask.entity.Role;
import org.example.testtask.entity.User;
import org.example.testtask.model.OrderDto;
import org.example.testtask.model.CreateUserRequest;
import org.example.testtask.model.OrderItemDto;

import java.util.List;

public class Mappers {

    public static User createUserRequesToUser(CreateUserRequest request, String password, Role role) {
        return new User(request.firstName(), request.lastName(), password, request.email(), role);
    }

    public static List<OrderDto> ordersToCreateOrderResponses(List<Order> order) {
        return order.stream()
                .map(Mappers::orderToCreateOrderResponse)
                .toList();
    }

    public static OrderDto orderToCreateOrderResponse(Order order) {
        return new OrderDto(order.getId(),
                orderToOrderItemDto(order),
                order.getStatus().name(),
                order.getOrderPrice().doubleValue(),
                order.getClient().getId()
        );
    }

    private static List<OrderItemDto> orderToOrderItemDto(Order order) {
        return order.getOrderItems()
                .stream()
                .map(Mappers::orderItemToOrderItemDto)
                .toList();
    }

    public static OrderItemDto orderItemToOrderItemDto(OrderItem orderItem) {
        return new OrderItemDto(orderItem.getProduct().getId(), orderItem.getQuantity());
    }

}
