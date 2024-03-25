package com.duro.edc_koko.product_components.comment.repos;

import com.duro.edc_koko.product_components.comment.domain.Comment;
import com.duro.edc_koko.product_components.product.domain.Product;
import com.duro.edc_koko.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findFirstByUser(User user);

    Comment findFirstByProduct(Product product);

    boolean existsByUserId(Integer id);

    boolean existsByProductId(Integer id);

}
