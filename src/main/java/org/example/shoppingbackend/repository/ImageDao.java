package org.example.shoppingbackend.repository;

import org.example.shoppingbackend.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageDao extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);
}
