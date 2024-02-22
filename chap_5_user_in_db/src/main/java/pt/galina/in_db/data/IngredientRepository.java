package pt.galina.in_db.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.in_db.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
