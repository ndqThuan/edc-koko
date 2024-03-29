package com.duro.edc_koko.models.user.service;

import com.duro.edc_koko.cloud.redis.service.JedisService;
import com.duro.edc_koko.models.comment.domain.Comment;
import com.duro.edc_koko.models.comment.repos.CommentRepository;
import com.duro.edc_koko.models.order.domain.Order;
import com.duro.edc_koko.models.order.repos.OrderRepository;
import com.duro.edc_koko.models.user.domain.User;
import com.duro.edc_koko.models.user.repos.UserRepository;
import com.duro.edc_koko.util.CookieUtil;
import com.duro.edc_koko.util.NotFoundException;
import com.duro.edc_koko.util.ReferencedWarning;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final JedisService jedisService;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CookieUtil cookieUtil;

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

    public boolean emailExists(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    private String generateSessionToken() {
        return UUID.randomUUID().toString();
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Order userOrder = orderRepository.findFirstByUser(user);
        if (userOrder != null) {
            referencedWarning.setKey("user.order.user.referenced");
            referencedWarning.addParam(userOrder.getId());
            return referencedWarning;
        }
        final Comment userComment = commentRepository.findFirstByUser(user);
        if (userComment != null) {
            referencedWarning.setKey("user.comment.user.referenced");
            referencedWarning.addParam(userComment.getId());
            return referencedWarning;
        }
        return null;
    }

}
