package org.example.shoppingbackend.repository;

import org.example.shoppingbackend.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDao extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
