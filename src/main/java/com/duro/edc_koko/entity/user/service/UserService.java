package com.duro.edc_koko.entity.user.service;

import com.duro.edc_koko.entity.comment.repos.CommentRepository;
import com.duro.edc_koko.entity.order.repos.OrderRepository;
import com.duro.edc_koko.entity.user.domain.User;
import com.duro.edc_koko.entity.user.repos.UserRepository;
import com.duro.edc_koko.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CommentRepository commentRepository;

    public List<User> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream().toList();
    }

    public User get(final Integer id) {
        return userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public void update(final User user) {
        userRepository.save(user);
    }

    public void delete(final Integer id) {
        userRepository.deleteById(id);
    }

    public boolean emailExists(final String username) {
        return userRepository.existsByUsername(username);
    }

    private String generateSessionToken() {
        return UUID.randomUUID().toString();
    }

}
