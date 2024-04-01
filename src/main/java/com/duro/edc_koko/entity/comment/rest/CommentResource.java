package com.duro.edc_koko.entity.comment.rest;

import com.duro.edc_koko.entity.comment.domain.Comment;
import com.duro.edc_koko.entity.comment.model.CommentDTO;
import com.duro.edc_koko.entity.comment.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentResource {

    private final CommentService commentService;

    public CommentResource(final CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        return ResponseEntity.ok(commentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(commentService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createComment(@RequestBody @Valid final Comment comment) {
        final Integer createdId = commentService.create(comment);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateComment(@RequestBody @Valid final Comment comment) {
        final Integer id = commentService.update(comment);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "id") final Integer id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
