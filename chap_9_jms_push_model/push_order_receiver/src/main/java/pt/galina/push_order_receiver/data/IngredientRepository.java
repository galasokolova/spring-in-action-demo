package pt.galina.push_order_receiver.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.push_order_receiver.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
