package pt.galina.chap7restfulcontroller.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "taco.orders")
@Data
@Validated
public class OrderProps {

    @Min(value = 1, message = "must be between 1 and 25")
    @Max(value = 25, message = "must be between 1 and 25")
    private int pageSize;
    public OrderProps(int pageSize) {
        this.pageSize = pageSize;
    }
}
