package pt.galina.chap6loggingconfig.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap6loggingconfig.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
