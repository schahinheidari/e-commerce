package org.example.shoppingbackend.repository;

import org.example.shoppingbackend.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDao extends JpaRepository<OrderItem, Long> {
}
