package pt.galina.resourceserver.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.resourceserver.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
