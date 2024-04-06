package pt.galina.jms_push_listener.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.jms_push_listener.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
