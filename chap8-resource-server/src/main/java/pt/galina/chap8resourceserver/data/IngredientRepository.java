package pt.galina.chap8resourceserver.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap8resourceserver.entity.taco.Ingredient;
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
