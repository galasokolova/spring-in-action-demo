package pt.galina.kafkalistener.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.kafkalistener.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
