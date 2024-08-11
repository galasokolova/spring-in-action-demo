package pt.galina.orderreceiver.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.orderreceiver.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
