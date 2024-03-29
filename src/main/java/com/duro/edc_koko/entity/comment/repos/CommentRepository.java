package com.duro.edc_koko.entity.comment.repos;

import com.duro.edc_koko.entity.comment.domain.Comment;
import com.duro.edc_koko.entity.product.domain.Product;
import com.duro.edc_koko.entity.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findFirstByUser(User user);

    Comment findFirstByProduct(Product product);

    boolean existsByUserId(Integer id);

    boolean existsByProductId(Integer id);

}
