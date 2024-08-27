package pt.galina.r2dbcpersistence.entity.taco;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table("taco") // Аннотация указывает, что класс отображается на таблицу taco в базе данных PostgreSQL
public class Taco {

    @Id
    private Long id;

    @NonNull
    @Column("name")  // Указание столбца в базе данных
    private String name;

    @Getter
    @Column("ingredient_ids")  // Сохраняем ingredientIds как массив в PostgreSQL
    private Set<Long> ingredientIds = new HashSet<>();

    @Getter
    private transient List<Ingredient> ingredients = new ArrayList<>();

    public void addIngredient(Ingredient ingredient) {
        ingredientIds.add(ingredient.getId());
    }

}
