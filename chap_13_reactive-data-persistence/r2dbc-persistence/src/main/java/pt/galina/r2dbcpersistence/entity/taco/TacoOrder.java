package pt.galina.r2dbcpersistence.entity.taco;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;
import pt.galina.r2dbcpersistence.entity.user.AppUser;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table("taco_order")
public class TacoOrder {

    @Id
    private Long id;

    @NotBlank(message = "Delivery name is required")
    private String deliveryName;

    @NotBlank(message = "Street is required")
    private String deliveryStreet;

    @NotBlank(message = "City is required")
    private String deliveryCity;

    @NotBlank(message = "State is required")
    private String deliveryState;

    @NotBlank(message = "Zip code is required")
    private String deliveryZip;

    @NotBlank(message = "Credit card number is required")
    @Pattern(regexp = "^[0-9]{16}$", message = "Invalid credit card number")
    private String ccNumber;

    @NotBlank(message = "Expiration date is required")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/([0-9]{2})$", message = "Must be formatted MM/YY")
    private String ccExpiration;

    @NotBlank(message = "CVV is required")
    @Pattern(regexp = "^[0-9]{3}$", message = "Invalid CVV")
    private String ccCVV;

    private Set<Long> tacoIds = new LinkedHashSet<>();

    private Long userId;
    private LocalDateTime placedAt;

    @Transient
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
        if (taco.getId() != null) {
            this.tacoIds.add(taco.getId());
        }
    }


    public void setUser(AppUser user) {
        this.userId = user.getId();
    }

    public void setPlacedAt() {
        this.placedAt = LocalDateTime.now();
    }

    public void addTacoId(Long id) {
        this.tacoIds.add(id);
    }


}
