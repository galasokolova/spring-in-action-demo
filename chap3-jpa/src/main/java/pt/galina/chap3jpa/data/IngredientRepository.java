package pt.galina.chap3jpa.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap3jpa.entity.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
