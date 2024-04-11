package com.duro.edc_koko.cloud.redis.rest;

import com.duro.edc_koko.entity.product.model.ProductDTO;
import com.duro.edc_koko.entity.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jedis")
public class JedisResource {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<String> initShoppingCart () throws IOException {
        String userId = "thuanDepZai";
        Map<ProductDTO, Integer> cartMap = new HashMap<>();

        List<ProductDTO> productDTOList = productService.find7NewProducts();

        int i = 1;
        for (ProductDTO dto : productDTOList) {
            cartMap.put(dto, i);
            i++;
        }

        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
