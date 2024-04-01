package com.duro.edc_koko.entity.comment.service;

import com.duro.edc_koko.entity.comment.domain.Comment;
import com.duro.edc_koko.entity.comment.model.CommentDTO;
import com.duro.edc_koko.entity.comment.repos.CommentRepository;
import com.duro.edc_koko.entity.product.repos.ProductRepository;
import com.duro.edc_koko.entity.user.repos.UserRepository;
import com.duro.edc_koko.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public List<CommentDTO> findAll() {
        final List<Comment> comments = commentRepository.findAll(Sort.by("id"));
        return comments.stream()
                .map(comment -> mapToDTO(comment, new CommentDTO()))
                .toList();
    }

    public CommentDTO get(final Integer id) {
        return commentRepository.findById(id)
                .map(comment -> mapToDTO(comment, new CommentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final Comment comment) {
        return commentRepository.save(comment).getId();
    }

    public Integer update(final Comment comment) {
        return commentRepository.save(comment).getId();
    }

    public void delete(final Integer id) {
        commentRepository.deleteById(id);
    }

    private CommentDTO mapToDTO(final Comment comment, final CommentDTO commentDTO) {
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setUsername(comment.getUser().getUsername());
        commentDTO.setDate(comment.getDate());
        return commentDTO;
    }

}
