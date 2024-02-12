package pt.galina.jdbc.ch_3_jdbc.data;

import pt.galina.jdbc.ch_3_jdbc.entity.Ingredient;
import java.util.Optional;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);
}
