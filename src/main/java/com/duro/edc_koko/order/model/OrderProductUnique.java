package com.duro.edc_koko.order.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.duro.edc_koko.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the id value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = OrderProductUnique.OrderProductUniqueValidator.class
)
public @interface OrderProductUnique {

    String message() default "{Exists.order.product}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class OrderProductUniqueValidator implements ConstraintValidator<OrderProductUnique, Integer> {

        private final OrderService orderService;
        private final HttpServletRequest request;

        public OrderProductUniqueValidator(final OrderService orderService,
                final HttpServletRequest request) {
            this.orderService = orderService;
            this.request = request;
        }

        @Override
        public boolean isValid(final Integer value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equals(orderService.get(Integer.parseInt(currentId)).getProduct())) {
                // value hasn't changed
                return true;
            }
            return !orderService.productExists(value);
        }

    }

}
