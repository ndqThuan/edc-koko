package com.duro.edc_koko.models.image.service;

import com.duro.edc_koko.models.image.domain.Image;
import com.duro.edc_koko.models.image.model.ImageDTO;
import com.duro.edc_koko.models.image.repos.ImageRepository;
import com.duro.edc_koko.models.product.domain.Product;
import com.duro.edc_koko.models.product.repos.ProductRepository;
import com.duro.edc_koko.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    public ImageService(final ImageRepository imageRepository,
                        final ProductRepository productRepository) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
    }

    public List<ImageDTO> findAll() {
        final List<Image> images = imageRepository.findAll(Sort.by("id"));
        return images.stream()
                .map(image -> mapToDTO(image, new ImageDTO()))
                .toList();
    }

    public ImageDTO get(final Integer id) {
        return imageRepository.findById(id)
                .map(image -> mapToDTO(image, new ImageDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ImageDTO imageDTO) {
        final Image image = new Image();
        mapToEntity(imageDTO, image);
        return imageRepository.save(image).getId();
    }

    public void update(final Integer id, final ImageDTO imageDTO) {
        final Image image = imageRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(imageDTO, image);
        imageRepository.save(image);
    }

    public void delete(final Integer id) {
        imageRepository.deleteById(id);
    }

    private ImageDTO mapToDTO(final Image image, final ImageDTO imageDTO) {
        imageDTO.setId(image.getId());
        imageDTO.setUrl(image.getUrl());
        imageDTO.setProduct(image.getProduct() == null ? null : image.getProduct().getId());
        return imageDTO;
    }

    private Image mapToEntity(final ImageDTO imageDTO, final Image image) {
        image.setUrl(imageDTO.getUrl());
        final Product product = imageDTO.getProduct() == null ? null : productRepository.findById(imageDTO.getProduct())
                .orElseThrow(() -> new NotFoundException("product not found"));
        image.setProduct(product);
        return image;
    }

}
