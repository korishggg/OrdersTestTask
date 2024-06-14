package org.example.testtask.service.impl;

import org.example.testtask.entity.*;
import org.example.testtask.exception.PermissionDeniedException;
import org.example.testtask.model.CreateOrderRequest;
import org.example.testtask.model.OrderDto;
import org.example.testtask.model.OrderItemDto;
import org.example.testtask.repositories.OrderRepository;
import org.example.testtask.repositories.ProductRepository;
import org.example.testtask.security.CustomUserDetails;
import org.example.testtask.service.OrderService;
import org.example.testtask.exception.ResourceNotFoundException;
import org.example.testtask.utils.Mappers;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderDto createOrder(CreateOrderRequest request) {
        Map<Long, Integer> productIdOnQuantities = mapToProductIdQuantities(request);

        List<Product> products = productRepository.findAllById(productIdOnQuantities.keySet());
        validateOrder(products, productIdOnQuantities);

        Order toSave = new Order();
        BigDecimal sum = BigDecimal.ZERO;

        for (Product product : products) {
            Integer productQuantity = productIdOnQuantities.get(product.getId());
            BigDecimal sumPerProduct = product.getPrice().multiply(BigDecimal.valueOf(productQuantity));
            sum = sum.add(sumPerProduct);
            toSave.addOrderItem(new OrderItem(productQuantity, product));
        }

        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        toSave.setClient(principal.getUser());
        toSave.setStatus(Status.READY);
        toSave.setOrderPrice(sum);

        Order saved = orderRepository.save(toSave);
        return Mappers.orderToCreateOrderResponse(saved);
    }

    private static Map<Long, Integer> mapToProductIdQuantities(CreateOrderRequest request) {
        return request.items()
                .stream()
                .collect(Collectors.toMap(
                        OrderItemDto::productId,
                        OrderItemDto::quantity,
                        Integer::sum
                ));
    }

    @Override
    public List<OrderDto> getOrders() {
        Long userId = getUserFromContext().getId();
        List<Order> orders = orderRepository.findByClientId(userId);
        return Mappers.ordersToCreateOrderResponses(orders);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with this Id = " + orderId + " is not found"));

        verifyOwner(order);
        return Mappers.orderToCreateOrderResponse(order);
    }

    private void verifyOwner(Order order) {
        User user = getUserFromContext();
        if (!order.getClient().getId().equals(user.getId())) {
            throw new PermissionDeniedException("Current User Doesn`t have enough permissions");
        }
    }

    private void validateOrder(List<Product> products, Map<Long, Integer> productIdOnQuantities) {
//        TODO some checks is some product are available and other verifications ...
    }

    private User getUserFromContext() {
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUser();
    }
}
