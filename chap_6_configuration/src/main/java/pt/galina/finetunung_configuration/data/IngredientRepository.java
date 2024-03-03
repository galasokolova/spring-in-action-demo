package pt.galina.finetunung_configuration.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.finetunung_configuration.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
