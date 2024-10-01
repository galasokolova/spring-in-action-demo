package pt.galina.clientnonreactive.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.clientnonreactive.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
