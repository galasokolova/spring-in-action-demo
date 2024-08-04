package pt.galina.chap8clientapp.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap8clientapp.entity.taco.Ingredient;
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
