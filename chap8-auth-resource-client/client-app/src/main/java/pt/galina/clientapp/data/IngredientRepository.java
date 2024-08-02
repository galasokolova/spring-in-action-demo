package pt.galina.clientapp.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.clientapp.entity.taco.Ingredient;
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
