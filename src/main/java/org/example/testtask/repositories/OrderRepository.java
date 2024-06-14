package org.example.testtask.repositories;

import org.example.testtask.entity.Order;
import org.example.testtask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClientId(Long clientId);

}