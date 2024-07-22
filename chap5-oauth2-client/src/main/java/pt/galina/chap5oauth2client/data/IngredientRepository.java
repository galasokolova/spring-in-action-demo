package pt.galina.chap5oauth2client.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap5oauth2client.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
