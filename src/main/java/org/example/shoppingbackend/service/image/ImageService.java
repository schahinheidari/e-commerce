package org.example.shoppingbackend.service.image;

import lombok.RequiredArgsConstructor;
import org.example.shoppingbackend.exceptions.ResourceNotFoundException;
import org.example.shoppingbackend.model.dto.ImageDto;
import org.example.shoppingbackend.model.entity.Image;
import org.example.shoppingbackend.model.entity.Product;
import org.example.shoppingbackend.repository.ImageDao;
import org.example.shoppingbackend.service.product.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements ImageServiceIMPL{

    private final ImageDao imageDao;
    private final ProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageDao.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Image with id " + id + " not found"));
    }

    @Override
    public void deleteImageById(Long id) {
        imageDao.findById(id).ifPresentOrElse(imageDao::delete, ()-> {
            throw new ResourceNotFoundException("Image with id " + id + " not found");
        });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);

        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImageBlob(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageDao.save(image);

                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
                imageDao.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);

            } catch (IOException | SQLException e){
                throw new RuntimeException(e.getMessage());

            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImageBlob(new SerialBlob(file.getBytes()));
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
