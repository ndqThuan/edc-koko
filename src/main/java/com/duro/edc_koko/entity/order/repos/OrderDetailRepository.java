package com.duro.edc_koko.entity.order.repos;


import com.duro.edc_koko.entity.order.domain.Order;
import com.duro.edc_koko.entity.order.domain.OrderDetail;
import com.duro.edc_koko.entity.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    OrderDetail findFirstByOrder(Order order);

    OrderDetail findFirstByProduct(Product product);

    List<OrderDetail> findByOrder(Order order);

    List<OrderDetail> findByProduct(Product product);

    long countDistinctByProduct(Product product);


}
