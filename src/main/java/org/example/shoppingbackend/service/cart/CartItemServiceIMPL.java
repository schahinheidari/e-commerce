package org.example.shoppingbackend.service.cart;

import org.example.shoppingbackend.model.entity.CartItem;

public interface CartItemServiceIMPL {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
