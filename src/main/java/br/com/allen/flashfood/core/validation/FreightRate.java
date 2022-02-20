package br.com.allen.flashfood.core.validation;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.PositiveOrZero;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@PositiveOrZero
public @interface FreightRate {
    @OverridesAttribute(constraint = PositiveOrZero.class, name = "message")
    String message() default "{FreightRate.Invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
