package pt.galina.push_message_listener.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.push_message_listener.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
