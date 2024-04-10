package pt.galina.rabbitmq.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.rabbitmq.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
