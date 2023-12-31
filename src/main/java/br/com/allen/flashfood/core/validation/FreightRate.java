package br.com.allen.flashfood.core.validation;

import static java.lang.annotation.ElementType.*;

import jakarta.validation.Constraint;
import jakarta.validation.OverridesAttribute;
import jakarta.validation.Payload;
import jakarta.validation.constraints.PositiveOrZero;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
