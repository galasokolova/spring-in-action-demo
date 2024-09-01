package pt.galina.spring_webflux_demo.config.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
public class ExpirationDateValidator implements ConstraintValidator<ValidExpirationDate, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/yy");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        try {
            YearMonth expirationDate = YearMonth.parse(value, FORMATTER);
            YearMonth currentMonth = YearMonth.now();

            boolean valid = !expirationDate.isBefore(currentMonth);
            log.info("Validating expiration date: {} - valid: {}", value, valid);
            return valid;
        } catch (DateTimeParseException e) {
            log.info("Invalid expiration date format: {}", value);
            return false; // Неверный формат даты
        }
    }
}



