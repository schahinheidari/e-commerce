package org.example.shoppingbackend.service.order;

import org.example.shoppingbackend.model.dto.OrderDto;
import org.example.shoppingbackend.model.entity.Order;

import java.util.List;

public interface OrderServiceIMPL {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
