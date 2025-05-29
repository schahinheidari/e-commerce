package org.example.shoppingbackend.repository;

import org.example.shoppingbackend.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
