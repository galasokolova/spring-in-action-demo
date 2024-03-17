package pt.galina.authorization_server.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.authorization_server.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
