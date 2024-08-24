package pt.galina.r2dbcpersistence.entity.taco;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Taco {

    @Id
    private Long id;
    private @NonNull String name;

    @Getter
    private Set<Long> ingredientIds = new HashSet<>();

    @Getter
    private transient List<Ingredient> ingredients = new ArrayList<>(); // Initialize with empty list

    public void addIngredient(Ingredient ingredient) {
        ingredientIds.add(ingredient.getId());  // Use Long ID here
    }

}



