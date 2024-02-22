package pt.galina.oauth2_client.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.oauth2_client.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
