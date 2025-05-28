package org.example.shoppingbackend.service.category;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.shoppingbackend.exceptions.AlreadyExistsException;
import org.example.shoppingbackend.exceptions.ResourceNotFoundException;
import org.example.shoppingbackend.model.entity.Category;
import org.example.shoppingbackend.repository.CategoryDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryServiceIMPL{

    private final CategoryDao categoryDao;
    @Override
    public Category getCategoryById(Long id) {
        return categoryDao.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryDao.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    @Override
    public Category saveCategory(Category category) {
        return Optional.of(category).filter(c -> !categoryDao.existsByName(c.getName()))
                .map(categoryDao::save).orElseThrow(()->
                        new AlreadyExistsException(category.getName()+ " already exists"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
            oldCategory.setName(category.getName());
            return categoryDao.save(oldCategory);
        }).orElseThrow(()-> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryDao.findById(id)
                .ifPresentOrElse(categoryDao::delete, ()-> {
                    throw new ResourceNotFoundException("Category not found!");
                        });
    }
}
