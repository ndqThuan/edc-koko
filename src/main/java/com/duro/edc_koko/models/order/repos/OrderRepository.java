package com.duro.edc_koko.models.order.repos;

import com.duro.edc_koko.models.order.domain.Order;
import com.duro.edc_koko.models.product.domain.Product;
import com.duro.edc_koko.models.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findFirstByUser(User user);

    Order findFirstByProduct(Product product);

    boolean existsByProductId(Integer id);

}
