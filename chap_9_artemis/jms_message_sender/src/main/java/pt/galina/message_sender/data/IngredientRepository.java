package pt.galina.message_sender.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.message_sender.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
