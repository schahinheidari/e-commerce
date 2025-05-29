package org.example.shoppingbackend.repository;

import org.example.shoppingbackend.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemDao extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);
}
