package pt.galina.method_level_security.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.method_level_security.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
