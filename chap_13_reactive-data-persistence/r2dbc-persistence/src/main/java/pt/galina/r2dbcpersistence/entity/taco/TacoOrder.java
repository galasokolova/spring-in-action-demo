package pt.galina.r2dbcpersistence.entity.taco;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.PrePersist;
import org.springframework.data.annotation.Id;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import pt.galina.r2dbcpersistence.entity.user.AppUser; // Импортируем User
import java.time.LocalDateTime;

@Data
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
    private transient List<Taco> tacos = new ArrayList<>();
    public void addTaco(Taco taco) {
        this.tacos.add(taco);
        if (taco.getId() != null) {
            this.tacoIds.add(taco.getId());
        }
    }

    public void setUser(AppUser user) {
        this.userId = user.getId();
    }

    @PrePersist
    void placedAt() {
        this.placedAt = LocalDateTime.now();
    }
}
