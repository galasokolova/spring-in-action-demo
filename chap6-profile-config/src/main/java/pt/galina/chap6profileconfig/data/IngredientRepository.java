package pt.galina.chap6profileconfig.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap6profileconfig.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
