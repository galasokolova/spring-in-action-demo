package pt.galina.chap3jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    @Column(name = "name")
    private String name;

    @Size(min=1, message="You must choose at least 1 ingredient")
    @ManyToMany
    @JoinTable(
            name = "taco_ingredients",
            joinColumns = @JoinColumn(name = "taco_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredients_id")
    )
    private List<Ingredient> ingredients = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "taco_order_id")
    @ToString.Exclude
    private TacoOrder tacoOrder;

    private Date createdAt = new Date();

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
}


