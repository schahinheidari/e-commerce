package org.example.shoppingbackend.repository;

import org.example.shoppingbackend.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDao extends JpaRepository<Category, Long> {
    Category findByName(String name);

    boolean existsByName(String name);
}
