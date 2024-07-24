package pt.galina.chap6finetuningconfig.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap6finetuningconfig.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
