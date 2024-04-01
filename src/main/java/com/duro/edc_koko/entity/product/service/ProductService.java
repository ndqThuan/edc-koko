package com.duro.edc_koko.entity.product.service;

import com.duro.edc_koko.entity.image.repos.ImageRepository;
import com.duro.edc_koko.entity.order.repos.OrderDetailRepository;
import com.duro.edc_koko.entity.product.domain.Product;
import com.duro.edc_koko.entity.product.model.ProductDTO;
import com.duro.edc_koko.entity.product.model.ProductTrend;
import com.duro.edc_koko.entity.product.repos.ProductRepository;
import com.duro.edc_koko.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@EnableCaching
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ImageRepository imageRepository;

    @Value("${product-image-not-found}")
    // Create a custom property that take the value of the image not found url
    // Or you can just put a string in this variable
    private String IMAGE_NOT_FOUND_URL;


    public List<ProductDTO> findAll() {
        final List<Product> products = productRepository.findAll(Sort.by("id"));
        return products.stream()
                .map(product -> mapToDTO(product, new ProductDTO()))
                .toList();
    }

//    public List<ProductDTO> findTop7ProductsInSale() {
//        final List<Product> products = productRepository.findTop7ProductsInSale()
//                .orElseThrow(NotFoundException::new);
//        return products.stream()
//                .map(product -> mapToDTO(product, new ProductDTO()))
//                .toList();
//    }

    public List<ProductDTO> find7NewProducts() {
        final List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).subList(0, 7);
        return products.stream()
                .map(product -> mapToDTO(product, new ProductDTO()))
                .toList();
    }


    @Cacheable(value = "product", key = "#id")
    public ProductDTO get(final Integer id) {
        return productRepository.findById(id)
                .map(product -> mapToDTO(product, new ProductDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final Product product) {
        product.setUploadDate(LocalDate.now());
        return productRepository.save(product).getId();
    }

    public void update(final Product product) {
        productRepository.save(product);
    }

    public void delete(final Integer id) {
        productRepository.deleteById(id);
    }

    private ProductDTO mapToDTO(final Product product, final ProductDTO productDTO) {
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setAvailable(product.getAvailable());
        productDTO.setCategory(product.getCategory() == null ? null : product.getCategory().getName());

        productDTO.setImageUrl(imageRepository.findFirstByProduct(product) == null
                ? IMAGE_NOT_FOUND_URL
                : imageRepository.findFirstByProduct(product).getUrl());

        productDTO.setTrend(getTrend(product));

        return productDTO;
    }

    private ProductTrend getTrend(final Product product) {
        if (product.getUploadDate().isAfter(LocalDate.now().minusMonths(1))) {
            return ProductTrend.NEW;
        }

        if (orderDetailRepository.countDistinctByProduct(product) > 10) {
            return ProductTrend.HOT;
        }

        return null;
    }

    public void initTable() {

    }

}
