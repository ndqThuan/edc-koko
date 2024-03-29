package com.duro.edc_koko.entity.order.repos;

import com.duro.edc_koko.entity.order.domain.Order;
import com.duro.edc_koko.entity.product.domain.Product;
import com.duro.edc_koko.entity.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findFirstByUser(User user);

    Order findFirstByProduct(Product product);

    boolean existsByProductId(Integer id);

}
