package org.example.shoppingbackend.service.cart;

import org.example.shoppingbackend.model.entity.Cart;

import java.math.BigDecimal;

public interface CartServiceIMPL {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeCart();
}
