package org.example.shoppingbackend.service.product;

import org.example.shoppingbackend.model.entity.Product;
import org.example.shoppingbackend.request.ProductUpdateRequest;
import org.example.shoppingbackend.request.SaveProductRequest;

import java.util.List;

public interface ProductServiceIMPL {
    Product saveProduct(SaveProductRequest product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product updateProduct(ProductUpdateRequest request, Long productId);
    void deleteProductById(Long id);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByNameAndBrand(String name, String brand);
    Long countProductsByBrandAndName(String brand, String name);
}
