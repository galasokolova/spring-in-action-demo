package pt.galina.chap8admin_preauthorize_demo.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap8admin_preauthorize_demo.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
