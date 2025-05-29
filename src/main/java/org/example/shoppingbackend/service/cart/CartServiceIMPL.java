package org.example.shoppingbackend.service.cart;

import org.example.shoppingbackend.model.entity.Cart;
import org.example.shoppingbackend.model.entity.User;

import java.math.BigDecimal;

public interface CartServiceIMPL {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializeCart(User user);

    Cart getCartByUserId(Long userId);
}
