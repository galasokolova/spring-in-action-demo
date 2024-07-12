package pt.galina.chap4mongodb.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap4mongodb.entity.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
