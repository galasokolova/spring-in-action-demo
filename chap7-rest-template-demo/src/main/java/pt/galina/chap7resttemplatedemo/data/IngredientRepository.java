package pt.galina.chap7resttemplatedemo.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap7resttemplatedemo.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
