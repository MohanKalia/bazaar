package org.mohankalia.bazaar.service.image;

import lombok.RequiredArgsConstructor;
import org.mohankalia.bazaar.Exceptions.ProductNotFoundException;
import org.mohankalia.bazaar.Exceptions.ResourceNotFoundException;
import org.mohankalia.bazaar.dto.ImageDTO;
import org.mohankalia.bazaar.model.Image;
import org.mohankalia.bazaar.model.Product;
import org.mohankalia.bazaar.repository.ImageRepository;
import org.mohankalia.bazaar.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found!"));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Image not found!");
                });
    }

    @Override
    public List<ImageDTO> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
        List<ImageDTO> savedImageDTO = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";

                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);

                Image img = imageRepository.save(image);
                img.setDownloadUrl(buildDownloadUrl + img.getId());
                imageRepository.save(img);

                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setImageId(img.getId());
                imageDTO.setImageName(img.getFileName());
                imageDTO.setDownloadURL(img.getDownloadUrl());

                savedImageDTO.add(imageDTO);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return savedImageDTO;

    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
