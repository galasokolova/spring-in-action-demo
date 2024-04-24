package pt.galina.kafka_listener.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.kafka_listener.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
