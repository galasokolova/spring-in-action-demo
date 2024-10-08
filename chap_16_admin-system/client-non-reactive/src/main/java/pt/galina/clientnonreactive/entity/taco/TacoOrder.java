package pt.galina.clientnonreactive.entity.taco;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;
import pt.galina.clientnonreactive.config.validation.ValidCreditCard;
import pt.galina.clientnonreactive.config.validation.ValidExpirationDate;
import pt.galina.clientnonreactive.entity.user.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "Taco_Order")
public class TacoOrder implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "placed_at")
    private Date placedAt = new Date();

    @Column(name = "delivery_Name")
    @NotBlank(message="Delivery name is required")
    private String deliveryName;

    @Column(name = "delivery_Street")
    @NotBlank(message="Street is required")
    private String deliveryStreet;

    @Column(name = "delivery_City")
    @NotBlank(message="City is required")
    private String deliveryCity;

    @Column(name = "delivery_State")
    @NotBlank(message="State is required")
    private String deliveryState;

    @Column(name = "delivery_Zip")
    @NotBlank(message="Zip code is required")
    private String deliveryZip;

    @Column(name = "cc_number")
    @NotBlank(message = "Credit card number is required")
    @ValidCreditCard
    private String ccNumber;

    @Column(name = "cc_expiration")
    @Pattern(regexp="^(0[1-9]|1[0-2])/(\\d{2})$",
            message="Must be formatted MM/YY")
    @NotBlank(message = "Expiration date is required")
    @ValidExpirationDate
    private String ccExpiration;

    @Column(name = "cc_cvv")
    @NotBlank(message = "CVV is required")
    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tacoOrder")
    @ToString.Exclude
    @JsonManagedReference     // for displaying id of orders in json correctly
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }
}
