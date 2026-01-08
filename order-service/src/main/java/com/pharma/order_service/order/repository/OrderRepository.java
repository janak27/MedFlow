package com.pharma.order_service.order.repository;

import com.pharma.order_service.order.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"items"})
    Optional<Order> findByOrderReference(String orderReference);

    boolean existsByOrderReference(String orderReference);
}