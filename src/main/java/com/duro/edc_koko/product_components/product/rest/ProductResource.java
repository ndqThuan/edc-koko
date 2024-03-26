package com.duro.edc_koko.product_components.product.rest;

import com.duro.edc_koko.product_components.product.domain.Product;
import com.duro.edc_koko.product_components.product.model.ProductDTO;
import com.duro.edc_koko.product_components.product.service.ProductService;
import com.duro.edc_koko.util.ReferencedException;
import com.duro.edc_koko.util.ReferencedWarning;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductResource {

    private final ProductService productService;


    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(productService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createProduct(@RequestBody @Valid final Product product) {
        final Integer createdId = productService.create(product);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateProduct(@RequestBody @Valid final Product product) {
        productService.update(product);
        return ResponseEntity.ok(product.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") final Integer id) {
        final ReferencedWarning referencedWarning = productService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
