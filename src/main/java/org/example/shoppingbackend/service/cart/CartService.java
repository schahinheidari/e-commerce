package org.example.shoppingbackend.service.cart;

import lombok.RequiredArgsConstructor;
import org.example.shoppingbackend.exceptions.ResourceNotFoundException;
import org.example.shoppingbackend.model.entity.Cart;
import org.example.shoppingbackend.repository.CartDao;
import org.example.shoppingbackend.repository.CartItemDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements CartServiceIMPL{
    private final CartDao cartDao;
    private final CartItemDao cartItemDao;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartDao.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartDao.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemDao.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartDao.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
/*        return cart.getCartItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);*/
        return cart.getTotalAmount();
    }

    @Override
    public Long initializeCart() {
        Cart newCart = new Cart();
        Long newCartId = cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);
        return cartDao.save(newCart).getId();
    }
}
