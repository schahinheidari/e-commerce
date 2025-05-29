package org.example.shoppingbackend.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDto {
    private Long cartId;
    private Set<CartItemDto> itemDtos;
    private BigDecimal totalAmount;
}
