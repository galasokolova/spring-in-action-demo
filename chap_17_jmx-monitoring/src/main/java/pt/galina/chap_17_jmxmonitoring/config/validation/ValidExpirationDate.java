package pt.galina.chap_17_jmxmonitoring.config.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pt.galina.chap_17_jmxmonitoring.config.validation.ExpirationDateValidator;

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

