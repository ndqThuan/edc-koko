package com.duro.edc_koko.product_components.image.repos;

import com.duro.edc_koko.product_components.image.domain.Image;
import com.duro.edc_koko.product_components.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Image, Integer> {

    Image findFirstByProduct(Product product);

}
