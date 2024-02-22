package pt.galina.jdbc.data;

import pt.galina.jdbc.entity.Ingredient;
import java.util.Optional;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);
}
