package pt.galina.jms_sender.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.jms_sender.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
