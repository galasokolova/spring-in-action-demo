package pt.galina.r2dbcpersistence.entity.taco;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;
import pt.galina.r2dbcpersistence.entity.user.AppUser;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table("taco_order")  // Указываем таблицу
public class TacoOrder {

    @Id
    private Long id;

    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccNumber;
    private String ccExpiration;
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

    // Этот метод будет вызываться при создании заказа
    public void setPlacedAt() {
        this.placedAt = LocalDateTime.now();
    }
}
