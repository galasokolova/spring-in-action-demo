package pt.galina.jms.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.jms.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
