package pt.galina.chap3jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "taco_order_id")
    private TacoOrder tacoOrder;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    /**
     * The @ToString.Exclude annotation is used to prevent a potential StackOverflowError
     * caused by recursive calls between Taco and TacoOrder in the generated toString() method.
     */
    @Size(min=1, message="You must choose at least 1 ingredient")
    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "Ingredient_Ref",
            joinColumns = @JoinColumn(name = "taco_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients = new ArrayList<>();

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
}
