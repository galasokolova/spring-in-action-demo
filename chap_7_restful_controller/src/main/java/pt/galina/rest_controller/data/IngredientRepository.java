package pt.galina.rest_controller.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.rest_controller.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
