package pt.galina.jpa.ch_3_jpa.data;

import pt.galina.jpa.ch_3_jpa.entity.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
