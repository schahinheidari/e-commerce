package org.example.shoppingbackend.model.dto;

import lombok.Data;
import org.example.shoppingbackend.model.entity.Category;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;

    private Category category;

    private List<ImageDto> imageDtos;
}
