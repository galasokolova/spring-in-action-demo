package pt.galina.r2dbcpersistence.entity.taco;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table("taco")
public class Taco {

    @Id
    private Long id;

    @NonNull
    @Column("name")
    @Size(min=5, message="Name must be at least 5 characters long")
    private String name;

    @Column("ingredient_ids")
    @Size(min=1, message="You must choose at least 1 ingredient")
    private List<Long> ingredientIds = new ArrayList<>();  // saving to DB

    @Transient
    private List<Ingredient> ingredients = new ArrayList<>();  // using only in memory

    public void addIngredient(Ingredient ingredient) {
        ingredientIds.add(ingredient.getId());
    }
}

