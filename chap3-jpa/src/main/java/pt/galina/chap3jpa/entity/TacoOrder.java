package pt.galina.chap3jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.CreditCardNumber;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "placed_at")
    @CreationTimestamp
    private Date placedAt;

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
    @CreditCardNumber(message="Not a valid credit card number")
    private String ccNumber;

    @Column(name = "cc_expiration")
    @Pattern(regexp="^(0[1-9]|1[0-2])/(\\d{2})$",
            message="Must be formatted MM/YY")
    private String ccExpiration;

    @Column(name = "cc_cvv")
    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    /**
     * The @ToString.Exclude annotation is used to prevent a potential StackOverflowError
     * caused by recursive calls between Taco and TacoOrder in the generated toString() method.
     */
    @OneToMany(mappedBy = "tacoOrder", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        taco.setTacoOrder(this);
        this.tacos.add(taco);
    }
}
