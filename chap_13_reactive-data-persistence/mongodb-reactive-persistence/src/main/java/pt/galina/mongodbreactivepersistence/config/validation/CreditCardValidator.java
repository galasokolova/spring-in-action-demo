package pt.galina.mongodbreactivepersistence.config.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreditCardValidator implements ConstraintValidator<ValidCreditCard, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // Если поле пустое, пусть сработает @NotNull
        }

        boolean valid = isValidCreditCardNumber(value);
        log.info("Validating credit card number: {} - valid: {}", value, valid);
        return valid;
    }

    private boolean isValidCreditCardNumber(String number) {
        int nDigits = number.length();
        int sum = 0;
        boolean isSecond = false;

        for (int i = nDigits - 1; i >= 0; i--) {
            int d = number.charAt(i) - '0';

            if (isSecond) {
                d *= 2;
            }

            sum += d / 10;
            sum += d % 10;

            isSecond = !isSecond;
        }

        return sum % 10 == 0;
    }
}




