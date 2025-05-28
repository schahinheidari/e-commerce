package org.example.shoppingbackend.service.image;

import org.example.shoppingbackend.model.dto.ImageDto;
import org.example.shoppingbackend.model.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageServiceIMPL {

    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
