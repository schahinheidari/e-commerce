package org.example.shoppingbackend.service.category;

import org.example.shoppingbackend.model.entity.Category;

import java.util.List;

public interface CategoryServiceIMPL {

    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category saveCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategory(Long id);

}
