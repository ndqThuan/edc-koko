package com.duro.edc_koko.entity.category.model;

import com.duro.edc_koko.entity.category.service.CategoryService;
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
 * Validate that the name value isn't taken yet.
 */
@Target({FIELD, METHOD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = CategoryNameUnique.CategoryNameUniqueValidator.class
)
public @interface CategoryNameUnique {

    String message() default "{Exists.category.name}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class CategoryNameUniqueValidator implements ConstraintValidator<CategoryNameUnique, String> {

        private final CategoryService categoryService;
        private final HttpServletRequest request;

        public CategoryNameUniqueValidator(final CategoryService categoryService,
                                           final HttpServletRequest request) {
            this.categoryService = categoryService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equalsIgnoreCase(categoryService.get(Integer.parseInt(currentId)).getName())) {
                // value hasn't changed
                return true;
            }
            return !categoryService.nameExists(value);
        }

    }

}