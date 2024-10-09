package pt.galina.dockercontainerdemo.entity.taco;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.galina.dockercontainerdemo.config.validation.ValidCreditCard;
import pt.galina.dockercontainerdemo.config.validation.ValidExpirationDate;
import pt.galina.dockercontainerdemo.entity.taco.Taco;
import pt.galina.dockercontainerdemo.entity.user.User;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@Document(collection = "tacoOrder")
public class TacoOrder implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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

    @NotBlank(message = "Credit card number is required")
    @ValidCreditCard
    private String ccNumber;

    @NotBlank(message = "Expiration date is required")
    @ValidExpirationDate
    private String ccExpiration;

    @NotBlank(message = "CVV is required")
    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;


    private User user;

    private List<Taco> tacos = new ArrayList<>();
    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }

    public void placedAt() {
        this.placedAt = LocalDateTime.now();
    }
}



