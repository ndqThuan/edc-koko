package com.duro.edc_koko.product_components.product.service;

import com.duro.edc_koko.order.domain.Order;
import com.duro.edc_koko.order.repos.OrderRepository;
import com.duro.edc_koko.product_components.comment.domain.Comment;
import com.duro.edc_koko.product_components.comment.repos.CommentRepository;
import com.duro.edc_koko.product_components.image.domain.Image;
import com.duro.edc_koko.product_components.image.repos.ImageRepository;
import com.duro.edc_koko.product_components.product.domain.Product;
import com.duro.edc_koko.product_components.product.model.ProductDTO;
import com.duro.edc_koko.product_components.product.repos.ProductRepository;
import com.duro.edc_koko.util.NotFoundException;
import com.duro.edc_koko.util.ReferencedWarning;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;

    @Value("${my.product-image-not-found}")
    private String PRODUCT_IMAGE_NOT_FOUND_URL;


    public List<ProductDTO> findAll() {
        final List<Product> products = productRepository.findAll(Sort.by("id"));
        return products.stream()
                .map(product -> mapToDTO(product, new ProductDTO()))
                .toList();
    }

    /*public List<Product> findAll() {
        final List<Product> products = productRepository.findAll(Sort.by("id"));
        return products.stream()
                .toList();
    }*/

    public List<ProductDTO> findTop7ProductsInSale() {
        final List<Product> products = productRepository.findTop7ProductsInSale()
                .orElseThrow(NotFoundException::new);
        return products.stream()
                .map(product -> mapToDTO(product, new ProductDTO()))
                .toList();
    }

    public List<ProductDTO> find7NewProducts() {
        final List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).subList(0, 7);
        return products.stream()
                .map(product -> mapToDTO(product, new ProductDTO()))
                .toList();
    }

    public ProductDTO get(final Integer id) {
        return productRepository.findById(id)
                .map(product -> mapToDTO(product, new ProductDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final Product product) {
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
                ? PRODUCT_IMAGE_NOT_FOUND_URL
                : imageRepository.findFirstByProduct(product).getUrl());

        return productDTO;
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Product product = productRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Order productOrder = orderRepository.findFirstByProduct(product);
        if (productOrder != null) {
            referencedWarning.setKey("product.order.product.referenced");
            referencedWarning.addParam(productOrder.getId());
            return referencedWarning;
        }
        final Image productImage = imageRepository.findFirstByProduct(product);
        if (productImage != null) {
            referencedWarning.setKey("product.image.product.referenced");
            referencedWarning.addParam(productImage.getId());
            return referencedWarning;
        }
        final Comment productComment = commentRepository.findFirstByProduct(product);
        if (productComment != null) {
            referencedWarning.setKey("product.comment.product.referenced");
            referencedWarning.addParam(productComment.getId());
            return referencedWarning;
        }
        return null;
    }

}
