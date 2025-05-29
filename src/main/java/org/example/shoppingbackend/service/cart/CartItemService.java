package org.example.shoppingbackend.service.cart;

import lombok.RequiredArgsConstructor;
import org.example.shoppingbackend.exceptions.ResourceNotFoundException;
import org.example.shoppingbackend.model.entity.Cart;
import org.example.shoppingbackend.model.entity.CartItem;
import org.example.shoppingbackend.model.entity.Product;
import org.example.shoppingbackend.repository.CartDao;
import org.example.shoppingbackend.repository.CartItemDao;
import org.example.shoppingbackend.service.product.ProductServiceIMPL;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements CartItemServiceIMPL{
    private final CartItemDao cartItemDao;
    private final CartDao cartDao;
    private final ProductServiceIMPL productService;
    private final CartServiceIMPL cartService;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        //1. Get the Cart
        //2. Get the Product
        //3. Check if the product already in the cart
        //4. If yes, then increase the quantity with the requested quantity
        //5. If No, the initiate a new CartItem entry.
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId()
                        .equals(productId)).findFirst().orElse(new CartItem());
        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addCartItem(cartItem);
        cartItemDao.save(cartItem);
        cartDao.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeCartItem(itemToRemove);
        cartDao.save(cart);

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                  item.setQuantity(quantity);
                  item.setUnitPrice(item.getProduct().getPrice());
                  item.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getCartItems()
                .stream()
                .map(CartItem :: getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);
        cartDao.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId()
                        .equals(productId)).findFirst().orElseThrow(
                        ()-> new ResourceNotFoundException("Item not found"));
    }
}
