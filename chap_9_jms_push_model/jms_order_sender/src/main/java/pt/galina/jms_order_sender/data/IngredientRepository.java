package pt.galina.jms_order_sender.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.jms_order_sender.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
