package pt.galina.chap_15_actuatordemo.config.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExpirationDateValidator.class)
public @interface ValidExpirationDate {
    String message() default "Invalid expiration date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

