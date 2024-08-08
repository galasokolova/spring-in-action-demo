package pt.galina.jmsordersender.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.jmsordersender.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
