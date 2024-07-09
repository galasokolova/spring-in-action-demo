package pt.galina.spring_webflux_demo.entity.taco;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.galina.spring_webflux_demo.entity.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@Document(collection = "tacoOrder")
public class TacoOrder {
    @Id
    private String id;

    private LocalDateTime placedAt;

    @NotBlank(message="Delivery name is required")
    private String deliveryName;

    @NotBlank(message="Street is required")
    private String deliveryStreet;

    @NotBlank(message="City is required")
    private String deliveryCity;

    @NotBlank(message="State is required")
    private String deliveryState;

    @NotBlank(message="Zip code is required")
    private String deliveryZip;

    @CreditCardNumber(message="Not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])/(\\d{2})$",
            message="Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    private User user;

    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        if (this.tacos == null) {
            this.tacos = new ArrayList<>();
        }
        this.tacos.add(taco);
        log.info("Added taco to order: {}", taco);
    }

    public void placedAt() {
        this.placedAt = LocalDateTime.now();
    }
}
