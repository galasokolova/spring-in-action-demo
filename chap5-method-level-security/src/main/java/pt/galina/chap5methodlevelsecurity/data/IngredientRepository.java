package pt.galina.chap5methodlevelsecurity.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap5methodlevelsecurity.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
