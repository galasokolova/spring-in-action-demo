package pt.galina.dockercontainerdemo.config.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pt.galina.dockercontainerdemo.config.validation.CreditCardValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CreditCardValidator.class)
public @interface ValidCreditCard {
    String message() default "Invalid credit card number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

