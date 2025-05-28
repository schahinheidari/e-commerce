package org.example.shoppingbackend.service.product;

import lombok.RequiredArgsConstructor;
import org.example.shoppingbackend.exceptions.ProductNotFoundException;
import org.example.shoppingbackend.model.entity.Category;
import org.example.shoppingbackend.model.entity.Product;
import org.example.shoppingbackend.repository.CategoryDao;
import org.example.shoppingbackend.repository.ProductDao;
import org.example.shoppingbackend.request.ProductUpdateRequest;
import org.example.shoppingbackend.request.SaveProductRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceIMPL{

    private final ProductDao productDao;
    private final CategoryDao categoryDao;

    @Override
    public Product saveProduct(SaveProductRequest request) {
        // check if the category is found in the DB
        // if yes, set it as the new product category
        // if no, then save it as a new category
        // the set as the new product category.
        Category category = Optional.ofNullable(categoryDao.findByName(request.getCategory().getName()))
                .orElseGet(()-> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryDao.save(newCategory);
                });
        request.setCategory(category);

        return productDao.save(createProduct(request, category));
    }

    private Product createProduct(SaveProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productDao.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productDao.findById(productId)
                .map(existingProduct-> updateExistingProduct(existingProduct, request))
                .map(productDao :: save)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryDao.findByName(request.getCategory().getName());
        existingProduct.setName(category.getName());
        return existingProduct;
    }

    @Override
    public void deleteProductById(Long id) {
        productDao.findById(id)
                .ifPresentOrElse(productDao::delete,
                        ()-> {throw new ProductNotFoundException("Product not found");});
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productDao.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productDao.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productDao.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productDao.findByName(name);
    }

    @Override
    public List<Product> getProductsByNameAndBrand(String brand, String name) {
        return productDao.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productDao.countByBrandAndName(brand, name);
    }
}
