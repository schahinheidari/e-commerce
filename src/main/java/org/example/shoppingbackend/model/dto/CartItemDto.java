package org.example.shoppingbackend.model.dto;

import lombok.Data;
import org.example.shoppingbackend.model.entity.Product;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDto product;
}
