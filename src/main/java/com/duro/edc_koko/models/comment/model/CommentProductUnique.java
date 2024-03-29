package com.duro.edc_koko.models.comment.model;

import com.duro.edc_koko.models.comment.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.web.servlet.HandlerMapping;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import static java.lang.annotation.ElementType.*;


/**
 * Validate that the id value isn't taken yet.
 */
@Target({FIELD, METHOD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = CommentProductUnique.CommentProductUniqueValidator.class
)
public @interface CommentProductUnique {

    String message() default "{Exists.comment.product}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class CommentProductUniqueValidator implements ConstraintValidator<CommentProductUnique, Integer> {

        private final CommentService commentService;
        private final HttpServletRequest request;

        public CommentProductUniqueValidator(final CommentService commentService,
                                             final HttpServletRequest request) {
            this.commentService = commentService;
            this.request = request;
        }

        @Override
        public boolean isValid(final Integer value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equals(commentService.get(Long.parseLong(currentId)).getProduct())) {
                // value hasn't changed
                return true;
            }
            return !commentService.productExists(value);
        }

    }

}
