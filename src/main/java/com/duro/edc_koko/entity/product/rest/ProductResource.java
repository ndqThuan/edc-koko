package com.duro.edc_koko.entity.product.rest;

import com.duro.edc_koko.entity.product.domain.Product;
import com.duro.edc_koko.entity.product.model.ProductDTO;
import com.duro.edc_koko.entity.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("http://localhost:5500")
@RequiredArgsConstructor
public class ProductResource {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts () {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ProductDTO getProduct (@PathVariable(name = "id") final Integer id) {
        return productService.get(id);
    }

    @PostMapping
    public ResponseEntity<Integer> createProduct (@RequestBody @Valid final Product product) {
        final Integer createdId = productService.create(product);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateProduct (@RequestBody @Valid final Product product) {
        Integer id = product.getId();
        productService.update(id, product);
        return ResponseEntity.ok(id);
    }
}
