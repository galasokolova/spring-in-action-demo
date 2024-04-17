package pt.galina.jms_receiver.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.jms_receiver.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
