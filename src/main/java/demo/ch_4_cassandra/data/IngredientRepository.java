package demo.ch_3_jpa.data;

import demo.ch_3_jpa.entity.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
