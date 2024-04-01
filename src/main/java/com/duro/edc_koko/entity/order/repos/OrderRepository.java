package com.duro.edc_koko.entity.order.repos;

import com.duro.edc_koko.entity.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Integer> {

}
