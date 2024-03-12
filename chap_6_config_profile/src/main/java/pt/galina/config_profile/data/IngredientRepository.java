package pt.galina.config_profile.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.config_profile.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
