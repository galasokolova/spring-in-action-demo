package pt.galina.oath2_auth_server.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.oath2_auth_server.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
