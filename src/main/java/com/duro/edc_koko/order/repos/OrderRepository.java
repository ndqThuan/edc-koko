package com.duro.edc_koko.order.repos;

import com.duro.edc_koko.order.domain.Order;
import com.duro.edc_koko.product_components.product.domain.Product;
import com.duro.edc_koko.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findFirstByUser(User user);

    Order findFirstByProduct(Product product);

    boolean existsByProductId(Integer id);

}
