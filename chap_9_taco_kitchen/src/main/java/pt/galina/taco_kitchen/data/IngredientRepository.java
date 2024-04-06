package pt.galina.taco_kitchen.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.taco_kitchen.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
