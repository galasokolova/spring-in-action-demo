package pt.galina.rabbitmq_push_listener.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.rabbitmq_push_listener.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
