package pt.galina.ordersender.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.ordersender.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
