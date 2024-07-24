package pt.galina.chap6profileconfig.config;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableConfigurationProperties(OrderProps.class)
class OrderPropsTest {

    @Autowired
    private OrderProps orderProps;

    @Autowired
    private Validator validator;

    @Test
    void whenValidValue_thenNoException() {
        orderProps.setPageSize(10);
        assertEquals(10, orderProps.getPageSize());
    }

    @Test
    void whenInvalidValueTooLow_thenThrowException() {
        orderProps.setPageSize(0);
        Set<ConstraintViolation<OrderProps>> violations = validator.validate(orderProps);
        assertEquals(1, violations.size());
        assertEquals("must be between 1 and 25", violations.iterator().next().getMessage());
    }

    @Test
    void whenInvalidValueTooHigh_thenThrowException() {
        orderProps.setPageSize(26);
        Set<ConstraintViolation<OrderProps>> violations = validator.validate(orderProps);
        assertEquals(1, violations.size());
        assertEquals("must be between 1 and 25", violations.iterator().next().getMessage());
    }
}
