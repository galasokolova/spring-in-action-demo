package pt.galina.resource_server.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.galina.resource_server.entity.taco.Ingredient;
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
