package com.duro.edc_koko.entity.image.repos;

import com.duro.edc_koko.entity.image.domain.Image;
import com.duro.edc_koko.entity.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ImageRepository extends JpaRepository<Image, Integer> {

    Image findFirstByProduct (Product product);

    List<Image> findByProduct (Product product);


}
