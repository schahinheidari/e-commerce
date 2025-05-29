package org.example.shoppingbackend.service.order;

import lombok.RequiredArgsConstructor;
import org.example.shoppingbackend.enums.OrderStatus;
import org.example.shoppingbackend.exceptions.ResourceNotFoundException;
import org.example.shoppingbackend.model.dto.OrderDto;
import org.example.shoppingbackend.model.entity.Cart;
import org.example.shoppingbackend.model.entity.Order;
import org.example.shoppingbackend.model.entity.OrderItem;
import org.example.shoppingbackend.model.entity.Product;
import org.example.shoppingbackend.repository.OrderDao;
import org.example.shoppingbackend.repository.ProductDao;
import org.example.shoppingbackend.service.cart.CartServiceIMPL;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceIMPL{

    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final CartServiceIMPL cartService;
    private final ModelMapper modelMapper;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);

        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);

        order.setOrderItems(new HashSet<>(orderItemList));
        order.setOrderTotalAmount(calculateTotalAmount(orderItemList));
        Order orderSaved = orderDao.save(order);
        cartService.clearCart(cart.getId());

        return orderSaved;
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return  order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getCartItems().stream().map(cartItem-> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productDao.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
        }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList
                .stream()
                .map(item-> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderDao.findById(orderId)
                .map(this::convertToDto).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        List<Order> orders = orderDao.findByUserId(userId);
        return orders.stream().map(this::convertToDto).toList();
    }

    private OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
