package com.duro.edc_koko.entity.order.service;

import com.duro.edc_koko.entity.order.domain.Order;
import com.duro.edc_koko.entity.order.repos.OrderRepository;
import com.duro.edc_koko.entity.product.repos.ProductRepository;
import com.duro.edc_koko.entity.user.repos.UserRepository;
import com.duro.edc_koko.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    public List<Order> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("id"));
        return orders.stream().toList();
    }

    public Order get(final Integer id) {
        return orderRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Integer create(final Order order) {
        return orderRepository.save(order).getId();
    }

    public Integer update(final Order order) {
        return orderRepository.save(order).getId();
    }

    public void delete(final Integer id) {
        orderRepository.deleteById(id);
    }

}
