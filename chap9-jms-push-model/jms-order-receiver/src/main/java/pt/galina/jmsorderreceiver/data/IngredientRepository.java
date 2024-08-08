package pt.galina.jmsorderreceiver.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.jmsorderreceiver.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
