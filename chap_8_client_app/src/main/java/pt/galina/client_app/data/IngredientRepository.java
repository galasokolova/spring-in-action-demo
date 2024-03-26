package pt.galina.client_app.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.client_app.entity.taco.Ingredient;
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
