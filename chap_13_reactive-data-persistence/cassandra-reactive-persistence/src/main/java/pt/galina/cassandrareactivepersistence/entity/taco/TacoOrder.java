package pt.galina.cassandrareactivepersistence.entity.taco;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import pt.galina.cassandrareactivepersistence.config.validation.ValidCreditCard;
import pt.galina.cassandrareactivepersistence.config.validation.ValidExpirationDate;
import pt.galina.cassandrareactivepersistence.entity.user.UserUDT;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Table("orders")
public class TacoOrder implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @PrimaryKey
    private UUID id = Uuids.timeBased();

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
    @jakarta.validation.constraints.Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    @Column("tacos")
    private List<TacoUDT> tacos = new ArrayList<>();

    // Метод для добавления пользователя к заказу
    // Новое поле для хранения информации о пользователе
    @Setter
    @Indexed
    private UserUDT user;

    public void addTaco(TacoUDT taco) {
        this.tacos.add(taco);
    }

    public void placedAt() {
        this.placedAt = LocalDateTime.now();
    }

}
