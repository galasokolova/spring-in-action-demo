package pt.galina.configuration.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.configuration.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
