package learning.practice.spring_forum_with_react.boundedContext.post.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import learning.practice.spring_forum_with_react.boundedContext.post.service.CategoryService;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CategoryService.class})
@Documented
public @interface CategoryRequest {
    String message() default "에러";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
