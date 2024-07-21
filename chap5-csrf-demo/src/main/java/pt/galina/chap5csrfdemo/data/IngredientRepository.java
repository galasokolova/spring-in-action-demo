package pt.galina.chap5csrfdemo.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap5csrfdemo.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
