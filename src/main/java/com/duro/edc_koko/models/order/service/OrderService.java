package com.duro.edc_koko.models.order.service;

import com.duro.edc_koko.models.order.domain.Order;
import com.duro.edc_koko.models.order.model.OrderDTO;
import com.duro.edc_koko.models.order.repos.OrderRepository;
import com.duro.edc_koko.models.product.domain.Product;
import com.duro.edc_koko.models.product.repos.ProductRepository;
import com.duro.edc_koko.models.user.domain.User;
import com.duro.edc_koko.models.user.repos.UserRepository;
import com.duro.edc_koko.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(final OrderRepository orderRepository, final UserRepository userRepository,
                        final ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("id"));
        return orders.stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .toList();
    }

    public OrderDTO get(final Integer id) {
        return orderRepository.findById(id)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final OrderDTO orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        return orderRepository.save(order).getId();
    }

    public void update(final Integer id, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    public void delete(final Integer id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setId(order.getId());
        orderDTO.setQuantity(order.getQuantity());
        orderDTO.setUser(order.getUser() == null ? null : order.getUser().getId());
        orderDTO.setProduct(order.getProduct() == null ? null : order.getProduct().getId());
        return orderDTO;
    }

    private Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setQuantity(orderDTO.getQuantity());
        final User user = orderDTO.getUser() == null ? null : userRepository.findById(orderDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        order.setUser(user);
        final Product product = orderDTO.getProduct() == null ? null : productRepository.findById(orderDTO.getProduct())
                .orElseThrow(() -> new NotFoundException("product not found"));
        order.setProduct(product);
        return order;
    }

    public boolean productExists(final Integer id) {
        return orderRepository.existsByProductId(id);
    }

}
