package pt.galina.chap7restfulcontroller.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap7restfulcontroller.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
