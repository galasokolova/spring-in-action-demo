package pt.galina.spring_data_rest.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.spring_data_rest.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
