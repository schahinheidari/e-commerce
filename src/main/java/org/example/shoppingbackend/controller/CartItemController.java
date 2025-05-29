package org.example.shoppingbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingbackend.exceptions.ResourceNotFoundException;
import org.example.shoppingbackend.model.entity.Cart;
import org.example.shoppingbackend.model.entity.User;
import org.example.shoppingbackend.response.ApiResponse;
import org.example.shoppingbackend.service.cart.CartItemServiceIMPL;
import org.example.shoppingbackend.service.cart.CartServiceIMPL;
import org.example.shoppingbackend.service.user.UserService;
import org.example.shoppingbackend.service.user.UserServiceIMPL;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

    private final CartItemServiceIMPL cartItemService;
    private final CartServiceIMPL cartService;
    private final UserServiceIMPL userService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
        try {
            User user = userService.getUserById(1L);
            Cart cart = cartService.initializeCart(user);
            cartItemService.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Add Item Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new ApiResponse("Remove Item Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long itemId,
                                                          @RequestParam Integer quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new ApiResponse("Update Item Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
