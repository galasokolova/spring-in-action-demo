package pt.galina.logging.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.logging.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
