package com.duro.edc_koko.entity.product.service;

import com.duro.edc_koko.entity.category.repos.CategoryRepository;
import com.duro.edc_koko.entity.image.service.ImageService;
import com.duro.edc_koko.entity.order.repos.OrderDetailRepository;
import com.duro.edc_koko.entity.product.domain.Product;
import com.duro.edc_koko.entity.product.model.ProductDTO;
import com.duro.edc_koko.entity.product.model.ProductFilter;
import com.duro.edc_koko.entity.product.model.ProductTrend;
import com.duro.edc_koko.entity.product.repos.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@EnableCaching
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ImageService imageService;
    private final CategoryRepository categoryRepository;
    @Value("${azure.image-not-found-url}")
    // Create a custom property that take the value of the image not found url
    // Or you can just put a string in this variable
    private String IMAGE_NOT_FOUND_URL;

    public List<ProductDTO> findAll () {
        final List<Product> products = productRepository.findAll(Sort.by("id"));
        return products.stream()
                       .map(product -> mapToDTO(product, new ProductDTO()))
                       .toList();
    }


    public List<ProductDTO> find7NewProducts () {
        final List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).subList(0, 7);
        return products.stream()
                       .map(product -> mapToDTO(product, new ProductDTO()))
                       .toList();
    }


    @Cacheable(value = "product", key = "#id")
    public ProductDTO get (final Integer id) {
        Optional<Product> thisProduct = productRepository.findById(id);
        ProductDTO viewProduct = new ProductDTO();

        if (thisProduct.isPresent()) {
            mapToDTO(thisProduct.get(), viewProduct);
            viewProduct.setDescription(thisProduct.get().getDescription());
            viewProduct.setImageUrl(imageService.findAllByProduct(thisProduct.get()));
        } else {
            throw new NullPointerException();
        }

        return viewProduct;
    }

    public int countAllProduct () {
        return productRepository.countAllProducts();
    }

    public int countByCategory (int categoryId) {
        return productRepository.countDistinctByCategory(categoryId);
    }

    public List<ProductDTO> findByCategoryName (String categoryName) {
        if (categoryName.equalsIgnoreCase("all")) {
            return findAll();
        }

        String requestCategory = categoryName.replace("-", " ");
        List<Product> products = productRepository.findByCategoryName(requestCategory);

        return products.stream()
                       .map(product -> mapToDTO(product, new ProductDTO()))
                       .toList();
    }

    public List<ProductDTO> findFilteredProducts (ProductFilter filter) {
        Specification<Product> spec = Specification.where(null);

        if (filter.hasCategories()) {
            spec = spec.and(ProductRepository.withCategory(filter.getCategories()));
        }

        if (filter.hasMinPrice()) {
            spec = spec.and(ProductRepository.withPriceGreaterThan(filter.getMin_price()));
        }

        if (filter.hasMaxPrice()) {
            spec = spec.and(ProductRepository.withPriceLessThan(filter.getMax_price()));
        }

        if (filter.hasOrderBy()) {
            spec = spec.and(ProductRepository.orderBy(filter.getOrderBy()));
        }

        List<Product> products = productRepository.findAll(spec);

        return products.stream()
                       .map(product -> mapToDTO(product, new ProductDTO()))
                       .toList();
    }

    public Integer create (final Product product) {
        product.setUploadDate(LocalDate.now());
        return productRepository.save(product).getId();
    }

    @CachePut(value = "product", key = "#id")
    public void update (final Integer id, final Product product) {
        productRepository.save(product);
    }

    @CacheEvict(value = "product", key = "#id")
    public void delete (final Integer id) {
        productRepository.deleteById(id);
    }

    private ProductDTO mapToDTO (final Product product, final ProductDTO productDTO) {
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setAvailable(product.getAvailable());
        productDTO.setCategory(product.getCategory() == null
                                       ? null
                                       : product.getCategory().getName());

        productDTO.addImageUrl(imageService.findFirstByProduct(product) == null
                                       ? IMAGE_NOT_FOUND_URL
                                       : imageService.findFirstByProduct(product));

        productDTO.setTrend(getTrend(product));

        return productDTO;
    }

    private ProductTrend getTrend (final Product product) {
        if (product.getUploadDate().isAfter(LocalDate.now().minusWeeks(1))) {
            return ProductTrend.NEW;
        }

        if (orderDetailRepository.countDistinctByProduct(product) > 10) {
            return ProductTrend.HOT;
        }

        return null;
    }

}
