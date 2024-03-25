package com.duro.edc_koko.product_components.comment.service;

import com.duro.edc_koko.product_components.comment.domain.Comment;
import com.duro.edc_koko.product_components.comment.model.CommentDTO;
import com.duro.edc_koko.product_components.comment.repos.CommentRepository;
import com.duro.edc_koko.product_components.product.domain.Product;
import com.duro.edc_koko.product_components.product.repos.ProductRepository;
import com.duro.edc_koko.user.domain.User;
import com.duro.edc_koko.user.repos.UserRepository;
import com.duro.edc_koko.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CommentService(final CommentRepository commentRepository,
            final UserRepository userRepository, final ProductRepository productRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<CommentDTO> findAll() {
        final List<Comment> comments = commentRepository.findAll(Sort.by("id"));
        return comments.stream()
                .map(comment -> mapToDTO(comment, new CommentDTO()))
                .toList();
    }

    public CommentDTO get(final Long id) {
        return commentRepository.findById(id)
                .map(comment -> mapToDTO(comment, new CommentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CommentDTO commentDTO) {
        final Comment comment = new Comment();
        mapToEntity(commentDTO, comment);
        return commentRepository.save(comment).getId();
    }

    public void update(final Long id, final CommentDTO commentDTO) {
        final Comment comment = commentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(commentDTO, comment);
        commentRepository.save(comment);
    }

    public void delete(final Long id) {
        commentRepository.deleteById(id);
    }

    private CommentDTO mapToDTO(final Comment comment, final CommentDTO commentDTO) {
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setUser(comment.getUser() == null ? null : comment.getUser().getId());
        commentDTO.setUsername(comment.getUser() == null ? null : comment.getUser().getUsername());
        commentDTO.setProduct(comment.getProduct() == null ? null : comment.getProduct().getId());
        return commentDTO;
    }

    private void mapToEntity(final CommentDTO commentDTO, final Comment comment) {
        comment.setContent(commentDTO.getContent());
        final User user = commentDTO.getUser() == null ? null : userRepository.findById(commentDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        comment.setUser(user);
        final Product product = commentDTO.getProduct() == null ? null : productRepository.findById(commentDTO.getProduct())
                .orElseThrow(() -> new NotFoundException("product not found"));
        comment.setProduct(product);
    }

    public boolean userExists(final Integer id) {
        return commentRepository.existsByUserId(id);
    }

    public boolean productExists(final Integer id) {
        return commentRepository.existsByProductId(id);
    }

}
