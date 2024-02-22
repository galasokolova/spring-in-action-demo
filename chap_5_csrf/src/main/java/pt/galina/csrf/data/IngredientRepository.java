package pt.galina.csrf.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.csrf.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
