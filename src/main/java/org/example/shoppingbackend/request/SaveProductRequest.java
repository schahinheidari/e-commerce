package org.example.shoppingbackend.request;

import lombok.Data;
import org.example.shoppingbackend.model.entity.Category;

import java.math.BigDecimal;

@Data
public class SaveProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
