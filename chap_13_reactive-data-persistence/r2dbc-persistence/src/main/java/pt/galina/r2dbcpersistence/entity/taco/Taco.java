package pt.galina.r2dbcpersistence.entity.taco;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private String name;

    @Column("ingredient_ids")
    private List<Long> ingredientIds = new ArrayList<>();  // Идентификаторы ингредиентов, сохраняемые в БД

    @Transient
    private List<Ingredient> ingredients = new ArrayList<>();  // Используется только в памяти

    public void addIngredient(Ingredient ingredient) {
        ingredientIds.add(ingredient.getId());
    }
}

